package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.MoviesAdapter;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
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
    private FloatingActionButton fab;
    private TextView lbl_title;
    private TextView lbl_tagline;
    private MovieViewModel movieViewModel;
    private ImageView lbl_poster;
    private ImageView lbl_backdrop;
    private TextView lbl_popularity;
    private TextView lbl_voteaverage;
    private TextView lbl_votecount;
    private TextView lbl_runtime;
    private TextView lbl_overview;
    private TextView lbl_date;
    private TextView lbl_genre;
    private RecyclerView company_rv;
    private LinearLayoutManager lm;
    private ChipGroup cg_genre, cg_language;
    private ImageView people,star,calendar,clock,thumb;
    private ProgressBar pb;
    private CardView cv_gray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        pb = view.findViewById(R.id.progress_movie_detail);
        clock = view.findViewById(R.id.clock_movie_details_fragment);
        calendar = view.findViewById(R.id.calendar_movie_details_fragment);
        star = view.findViewById(R.id.star_movie_details_fragment);
        people = view.findViewById(R.id.people_movie_details_fragment);
        thumb = view.findViewById(R.id.thumb_movie_details_fragment);
        cv_gray = view.findViewById(R.id.white_background);
        fab = view.findViewById(R.id.fab_movie_details_fragment);

        pb.setVisibility(View.VISIBLE);
        clock.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        star.setVisibility(View.INVISIBLE);
        people.setVisibility(View.INVISIBLE);
        thumb.setVisibility(View.INVISIBLE);
        cv_gray.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);

        lbl_title = view.findViewById(R.id.lbl_title_movie_details_fragment);
        lbl_tagline = view.findViewById(R.id.lbl_tagline_movie_details_fragment);
        lbl_date = view.findViewById(R.id.lbl_date_movie_details_fragment);
        lbl_overview = view.findViewById(R.id.lbl_overview_movie_details_fragment);
        lbl_poster = view.findViewById(R.id.img_movie_details_fragment);
        lbl_backdrop = view.findViewById(R.id.backdrop_movie_details_fragment);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_movies_details_fragment);
        lbl_voteaverage = view.findViewById(R.id.lbl_vote_movie_details_fragment);
        lbl_runtime = view.findViewById(R.id.lbl_runtime_movie_details_fragment);
        lbl_votecount = view.findViewById(R.id.lbl_votecount_movie_details_fragment);
        lbl_genre = view.findViewById(R.id.lbl_genre_movie_details_fragment);

        lbl_genre.setVisibility(View.INVISIBLE);
        company_rv = view.findViewById(R.id.rv_movie_details_fragment);
        cg_language = view.findViewById(R.id.chipgroup_movie_languages);
        String movieId = String.valueOf(getArguments().getInt("id_bundle"));
        movieViewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        movieViewModel.getMovieById(movieId);

        movieViewModel.getResultMovieById().observe(getActivity(), showMovie);

        return view;
    }
    private Observer<Movies> showMovie = new Observer<Movies>() {

        @Override
        public void onChanged(Movies movies) {
//            bundle.putString("title_bundle",nowPlaying.getResults().get(position).getTitle());
//            bundle.putString("backdrop_bundle",nowPlaying.getResults().get(position).getBackdrop_path());
//            bundle.putInt("id_bundle",nowPlaying.getResults().get(position).getId());
//            bundle.putString("poster_bundle",nowPlaying.getResults().get(position).getPoster_path());
//            bundle.putDouble("popularity_bundle",nowPlaying.getResults().get(position).getPopularity());
//            bundle.putString("date_bundle",nowPlaying.getResults().get(position).getRelease_date());
//            bundle.putDouble("avgvote_bundle",nowPlaying.getResults().get(position).getVote_average());
//            bundle.putDouble("vote_bundle",nowPlaying.getResults().get(position).getVote_count());
//            bundle.putString("overview_bundle",nowPlaying.getResults().get(position).getOverview());
            pb.setVisibility(View.INVISIBLE);
            clock.setVisibility(View.VISIBLE);
            calendar.setVisibility(View.VISIBLE);
            star.setVisibility(View.VISIBLE);
            people.setVisibility(View.VISIBLE);
            thumb.setVisibility(View.VISIBLE);
            cv_gray.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            lbl_genre.setVisibility(View.VISIBLE);
            //visible semua
            String from = getArguments().getString("from_bundle");
            String tagline = movies.getTagline();
            String title = getArguments().getString("title_bundle");
            String poster = getArguments().getString("poster_bundle");
            String backdrop = getArguments().getString("backdrop_bundle");
            String overview = getArguments().getString("overview_bundle");
            double popularity = getArguments().getDouble("popularity_bundle");
            String date = getArguments().getString("date_bundle");
            double avgvote = getArguments().getDouble("avgvote_bundle");
            int vote = getArguments().getInt("vote_bundle");
            int runtime = movies.getRuntime();
            String language = getArguments().getString("language_bundle");
            Chip chipLanguage = new Chip(cg_language.getContext());
            chipLanguage.setText(language);
            cg_language.addView(chipLanguage);
            String genre = "";
            for (int i = 0; i < movies.getGenres().size(); i ++){
                if (i == (movies.getGenres().size()-1)){
                    genre += movies.getGenres().get(i).getName();
                }else{
                    genre += movies.getGenres().get(i).getName() + ", ";
                }
            }
            lbl_genre.setText(genre);
            lbl_title.setText(title);
            lbl_tagline.setText(tagline);
            lbl_popularity.setText(String.valueOf(popularity));
            lbl_overview.setText(overview);
            lbl_voteaverage.setText(String.valueOf(avgvote));
            lbl_date.setText(date);
            lbl_votecount.setText(String.valueOf(vote));
            lbl_runtime.setText(String.valueOf(runtime));
            Glide.with(getActivity()).load(Const.IMG_URL + poster).into(lbl_poster);
            Glide.with(getActivity()).load(Const.IMG_URL + backdrop).into(lbl_backdrop);

            lm = new LinearLayoutManager(getActivity());
            company_rv.setLayoutManager(lm);
            MoviesAdapter adapter = new MoviesAdapter(getActivity());
            adapter.setListMovies(movies.getProduction_companies());
            company_rv.setAdapter(adapter);

            ItemClickSupport.addTo(company_rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), ""+movies.getProduction_companies().get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(from.equalsIgnoreCase("Now Playing")) {
                        Navigation.findNavController(view).navigate(R.id.action_movieDetailsFragment_to_nowPlayingFragment);
                    }else{
                        Navigation.findNavController(view).navigate(R.id.action_movieDetailsFragment_to_upComingFragment);
                    }
                }
            });
        }
    };
}