package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.adapter.PaginationScrollListener;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.activities.NowPlayingActivity;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.example.moviedb.helper.ItemClickSupport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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

    private RecyclerView rv_now_playing;
    private MovieViewModel view_model;
    private FloatingActionButton fab;
    private ProgressBar pb;
    private LinearLayoutManager lm;
    NestedScrollView nsv;
    int page = 1;
    int current_items, total_items, scroll_out_items,first_item;
    boolean is_scrolling = false;
    boolean loading = true;
//    private static final int start_page = 1;
//    private boolean isLoading = false;
//    private boolean isLastPage = false;
//    private int total_pages = 1000;
//    private int current_page = start_page;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);



        //Note
        rv_now_playing = view.findViewById(R.id.rv_now_playing_fragment);
//        nsv = view.findViewById(R.id.nested_now_playing_fragment);
        pb = view.findViewById(R.id.progress_bar_now_playing_fragment);

//        pb = view.findViewById(R.id.progress_now_playing);
//        pb.setVisibility(View.VISIBLE);
        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getNowPlaying(page);
        lm = new LinearLayoutManager(getActivity());
        rv_now_playing.setLayoutManager(lm);
        pb.setVisibility(View.VISIBLE);

//        int first_view = lm.findFirstVisibleItemPosition();

        //work
//        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
//                    Log.v("...", "Last Item!!");
//                    page++;
//                    pb.setVisibility(View.VISIBLE);
//                    view_model.getNowPlaying(page);
//                    view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
//                }
//            }
//        });


        //start
        rv_now_playing.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                if(dy > 0) {
                    current_items = lm.getChildCount();
                    total_items = lm.getItemCount();
                    scroll_out_items = lm.findFirstVisibleItemPosition();
                    first_item = lm.findFirstCompletelyVisibleItemPosition();
//                    if (loading) {
                    if ((current_items + scroll_out_items >= total_items) && is_scrolling) {
                        is_scrolling = false;
                        Log.v("...", "Last Item!!");
                        //Wondering if page++ doesnt work;
                        page = page + 1;
                        pb.setVisibility(View.VISIBLE);
                        view_model.getNowPlaying(page);
                        view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Do something after 5s = 5000ms
//                                view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
//                            }
//                        }, 500);
                    }

                    else if ((page > 1) && (dy < -0.5)) {
                        if (lm.findFirstCompletelyVisibleItemPosition() == 0) {
                            Log.v("...", "First Item!!");
                            pb.setVisibility(View.VISIBLE);
                            //Wondering if page-- doesnt work;
                            page = page - 1;
                            view_model.getNowPlaying(page);
                            view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Do something after 5s = 5000ms
//
//                                }
//                            }, 500);

                        }

//                    } else if ((first_view > scroll_out_items)&&(dy<0)){
//                        Log.v("...", "Last Item!!");
//                        page--;
//                        pb.setVisibility(View.VISIBLE);
//                        view_model.getNowPlaying(page);
//                        view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
//                    }
//                    else if ((first_item == 1) && is_scrolling ){
//                        is_scrolling = false;
//                        Log.v("...", "Last Item!!");
//                        page--;
//                        pb.setVisibility(View.VISIBLE);
//                        view_model.getNowPlaying(page);
//                        view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
//                    }
//                    }
//                }
                    }
            }
        });

        //end
//        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                if (isLastVisible() == true){
////                    page++;
////                    pb.setVisibility(View.VISIBLE);
////                    view_model.getNowPlaying(page);
////                }
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
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
//                    page++;
//                    pb.setVisibility(View.VISIBLE);
//                    view_model.getNowPlaying(page);
//                }
//            }
//        });
//        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
//            page++;
//            pb.setVisibility(View.VISIBLE);
//            view_model.getNowPlaying(page);
//        }

        view_model.getResultNowPlaying().observe(getActivity(), showNowPlaying);
        return view;
    }


    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            //handler
            pb.setVisibility(View.INVISIBLE);

//            lm = new GridLayoutManager(getActivity(), 2);

//            rv_now_playing.setItemAnimator(new DefaultItemAnimator());
//            Parcelable rv_save_state = rv_now_playing.getLayoutManager().onSaveInstanceState();
            NowPlayingAdapter adapter = new NowPlayingAdapter(getActivity());
            adapter.setListNowPlaying(nowPlaying.getResults());
            rv_now_playing.setAdapter(adapter);
//            rv_now_playing.getLayoutManager().onRestoreInstanceState(rv_save_state);
//            adapter.notifyItemInserted(nowPlaying.getResults().size());

//            rv_now_playing.addOnScrollListener(new PaginationScrollListener(lm) {
//                @Override
//                protected void loadMoreItems() {
//                    isLoading = true;
//                    current_page += 1;
//                    loadNextPage();
//                }
//
//                @Override
//                public int getTotalPageCount() {
//                    return total_pages;
//                }
//
//                @Override
//                public boolean isLastPage() {
//                    return isLastPage;
//                }
//
//                @Override
//                public boolean isLoading() {
//                    return isLoading;
//                }
//            });

//            ItemClickSupport.addTo(rv_now_playing).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
//                    return false;
//                    //Ditahan
//                }
//            });

            ItemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title_bundle",nowPlaying.getResults().get(position).getTitle());
                    bundle.putString("backdrop_bundle",nowPlaying.getResults().get(position).getBackdrop_path());
                    bundle.putInt("id_bundle",nowPlaying.getResults().get(position).getId());
                    bundle.putString("poster_bundle",nowPlaying.getResults().get(position).getPoster_path());
                    bundle.putDouble("popularity_bundle",nowPlaying.getResults().get(position).getPopularity());
                    bundle.putString("date_bundle",nowPlaying.getResults().get(position).getRelease_date());
                    bundle.putDouble("avgvote_bundle",nowPlaying.getResults().get(position).getVote_average());
                    bundle.putInt("vote_bundle",nowPlaying.getResults().get(position).getVote_count());
                    bundle.putString("overview_bundle",nowPlaying.getResults().get(position).getOverview());
                    bundle.putIntegerArrayList("genre_bundle",(ArrayList<Integer>) nowPlaying.getResults().get(position).getGenre_ids());
                    bundle.putString("language_bundle",nowPlaying.getResults().get(position).getOriginal_language());
                    bundle.putString("from_bundle","Now Playing");
                    Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment,bundle);
                }
            });
        }
    };
    public boolean isLastVisible() {
        LinearLayoutManager layoutManager =((LinearLayoutManager) rv_now_playing.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = rv_now_playing.getAdapter().getItemCount();
        return (pos >= numItems - 1);
    }

}