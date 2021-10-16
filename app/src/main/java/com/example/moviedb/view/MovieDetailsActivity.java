package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private MovieViewModel vm;
    private TextView lbl_title,lbl_code,lbl_about,lbl_date,lbl_rating,lbl_language,lbl_average,lbl_genre;
    private ImageView movie_detail_image;
    private String movie_id,movie_title,movie_image_link,movie_about,movie_date,movie_rating,movie_language,movie_average,movie_genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

//        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        NowPlaying.Results movie_data = bundle.getParcelable("nowplaying_data");
        List<Integer> nowplaying_genre_ids = bundle.getIntegerArrayList("nowplaying_genreids");

        movie_id = String.valueOf(movie_data.getId());
        movie_title = String.valueOf(movie_data.getTitle());
        movie_image_link = String.valueOf(movie_data.getPoster_path());
        movie_about = String.valueOf(movie_data.getOverview());
        movie_date = String.valueOf(movie_data.getRelease_date());
        movie_rating = String.valueOf(movie_data.getPopularity());
        movie_language = String.valueOf(movie_data.getOriginal_language());
        movie_average = String.valueOf(movie_data.getVote_average());

        movie_detail_image = findViewById(R.id.movie_details_image);
        lbl_title = findViewById(R.id.lbl_movie_details_title);
        lbl_code = findViewById(R.id.lbl_movie_details_code);
        lbl_about = findViewById(R.id.lbl_movie_details_about);
        lbl_date = findViewById(R.id.lbl_movie_details_date);
        lbl_rating = findViewById(R.id.lbl_movie_details_rating);
        lbl_language = findViewById(R.id.lbl_movie_details_language);
        lbl_average = findViewById(R.id.lbl_movie_details_average);
        lbl_genre = findViewById(R.id.lbl_movie_details_genre);
        //Set
        Glide.with(MovieDetailsActivity.this).load(Const.IMG_URL + movie_image_link).into(movie_detail_image);
        lbl_title.setText(movie_title);
        lbl_code.setText("Movie ID: " + movie_id);
        lbl_about.setText(movie_about);
        lbl_date.setText(movie_date);
        lbl_rating.setText(movie_rating);
        lbl_language.setText(movie_language);
        lbl_average.setText(movie_average);
        //Debug
//        System.out.println(movie_data.getGenre_ids().size());

        vm = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        //Biar proses tidak di activity, coba passing parameter movie_id
        vm.getGenre(nowplaying_genre_ids);
        vm.getResultGenre().observe(MovieDetailsActivity.this, showMovieGenre);

//        vm = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
//        vm.getMovieById(movie_id);
//        vm.getResultMovieById().observe(MovieDetailsActivity.this, showMovieGenre);
    }


    private Observer<String> showMovieGenre = new Observer<String>() {
        @Override
        public void onChanged(String string) {
            lbl_genre.setText(string);
        }
    };

//    private Observer<Movies> showMovieGenre = new Observer<Movies>() {
//        @Override
//        public void onChanged(Movies movies) {
//            String genre = "";
//            for (int i = 0; i < movies.getGenres().size(); i ++){
//                if (i == (movies.getGenres().size()-1)){
//                    genre += movies.getGenres().get(i).getName();
//                }else{
//                    genre += movies.getGenres().get(i).getName() + ", ";
//                }
//            }
//            lbl_genre.setText(genre);
//        }
//    };

    @Override
    public void onBackPressed() {
        finish();
    }
}