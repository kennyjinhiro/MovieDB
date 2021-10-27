package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.adapter.UpcomingAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpComingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpComingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpComingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rv_upcoming;
    private MovieViewModel view_model;
    private ProgressBar pb;
    NestedScrollView nsv;
    boolean is_scrolling = false;
    int page = 1;
    int current_items, total_items, scroll_out_items;
    LinearLayoutManager lm;
    private boolean loading = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

//        nsv = view.findViewById(R.id.scrollview_up_coming_fragment);
        pb = view.findViewById(R.id.progress_bar_up_coming_fragment);

        rv_upcoming = view.findViewById(R.id.rv_up_coming_fragment);
        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getUpcoming(page);

        pb.setVisibility(View.VISIBLE);
        lm = new LinearLayoutManager(getActivity());
        rv_upcoming.setLayoutManager(lm);

        //start
        rv_upcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    is_scrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                current_items = lm.getChildCount();
//                total_items = lm.getItemCount();
//                scroll_out_items = lm.findFirstVisibleItemPosition();
//
//                if(current_items + scroll_out_items == total_items){
//                    if (is_scrolling == true) {
//                        Log.v("...", "Last Item!!");
//                        page++;
//                        pb.setVisibility(View.VISIBLE);
//                        view_model.getUpcoming(page);
//                        view_model.getResultUpcoming().observe(getActivity(), showUpcoming);
//                    }
//                }
//                if(dy > 0) {
                    current_items = lm.getChildCount();
                    total_items = lm.getItemCount();
                    scroll_out_items = lm.findFirstVisibleItemPosition();

//                    if (loading) {
                    if ((current_items + scroll_out_items == total_items)&&is_scrolling) {
                        is_scrolling = false;
                        Log.v("...", "Last Item!!");
                        //Wondering if page-- doesnt work;
                        page=page+1;
                        pb.setVisibility(View.VISIBLE);
                        view_model.getUpcoming(page);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                view_model.getResultUpcoming().observe(getActivity(), showUpcoming);
                            }
                        }, 500);


                    }else if ((page > 1) && (dy < 0)){
                        if (lm.findFirstCompletelyVisibleItemPosition() == 0) {
                            Log.v("...", "First Item!!");
                            //Wondering if page-- doesnt work;
                            page=page-1;
                            pb.setVisibility(View.VISIBLE);
                            view_model.getUpcoming(page);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    view_model.getResultUpcoming().observe(getActivity(), showUpcoming);
                                }
                            }, 500);
                        }
                    }
//                    }
//                }
            }
        });
        //end


//        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
//                    page++;
//                    pb.setVisibility(View.VISIBLE);
//                    view_model.getUpcoming(page);
//                }
////                    LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(rv_now_playing.getLayoutManager());
////                    int totalItemCount = layoutManager.getItemCount();
////                    int lastVisible = layoutManager.findLastVisibleItemPosition();
////
////                    boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
////                    if (totalItemCount > 0 && endHasBeenReached) {
////                        page++;
////                        pb.setVisibility(View.VISIBLE);
////                        view_model.getNowPlaying(page);
////                    }
//            }
//        });

        view_model.getResultUpcoming().observe(getActivity(), showUpcoming);
        return view;
    }

    private Observer<Upcoming> showUpcoming = new Observer<Upcoming>() {

        @Override
        public void onChanged(Upcoming upcoming) {
            pb.setVisibility(View.INVISIBLE);
            UpcomingAdapter adapter = new UpcomingAdapter(getActivity());

            rv_upcoming.setAdapter(adapter);
            adapter.setListUpcoming(upcoming.getResults());
//            nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                    LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(rv_upcoming.getLayoutManager());
////                    int totalItemCount = layoutManager.getItemCount();
////                    int lastVisible = layoutManager.findLastVisibleItemPosition();
////
////                    boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
////                    if (totalItemCount > 0 && endHasBeenReached) {
////                        page++;
////                        pb.setVisibility(View.VISIBLE);
////                        view_model.getUpcoming(page);
////                    }
//                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
//                        page++;
//                        pb.setVisibility(View.VISIBLE);
//                        view_model.getUpcoming(page);
//                    }
//                }
//            });
            ItemClickSupport.addTo(rv_upcoming).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title_bundle",upcoming.getResults().get(position).getTitle());
                    bundle.putString("backdrop_bundle",upcoming.getResults().get(position).getBackdrop_path());
                    bundle.putInt("id_bundle",upcoming.getResults().get(position).getId());
                    bundle.putString("poster_bundle",upcoming.getResults().get(position).getPoster_path());
                    bundle.putDouble("popularity_bundle",upcoming.getResults().get(position).getPopularity());
                    bundle.putString("date_bundle",upcoming.getResults().get(position).getRelease_date());
                    bundle.putDouble("avgvote_bundle",upcoming.getResults().get(position).getVote_average());
                    bundle.putInt("vote_bundle",upcoming.getResults().get(position).getVote_count());
                    bundle.putString("overview_bundle",upcoming.getResults().get(position).getOverview());
                    bundle.putIntegerArrayList("genre_bundle",(ArrayList<Integer>) upcoming.getResults().get(position).getGenre_ids());
                    bundle.putString("language_bundle",upcoming.getResults().get(position).getOriginal_language());
                    bundle.putString("from_bundle","Up Coming");
                    Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailsFragment,bundle);
                }
            });
        }
    };

    public boolean isLastVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rv_upcoming.getLayoutManager();
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = rv_upcoming.getAdapter().getItemCount();
        return (pos >= numItems - 1);
    }
}