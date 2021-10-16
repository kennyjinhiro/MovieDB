package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private Button btn_hit;
    private TextInputLayout til_movie_id;
    private TextView txt_show;
    private ImageView movie_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);
        til_movie_id = findViewById(R.id.til_movie_id_main);
        btn_hit = findViewById(R.id.btn_submit_main);
        txt_show = findViewById(R.id.txt_show);
        movie_image = findViewById(R.id.img_poster_main);
        btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId = til_movie_id.getEditText().getText().toString().trim();
                if (movieId.isEmpty()){
                    til_movie_id.setError("Please fill movie id field!");
                }
                viewModel.getMovieById(movieId);
                viewModel.getResultMovieById().observe(MainActivity.this, showResultMovie);
            }
        });
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if(movies == null){
                txt_show.setText("Movie ID is not available");
            }
            else {
                String title = movies.getTitle();
                String img_path = movies.getPoster_path().toString();
                String full_path = Const.IMG_URL + img_path;
                Glide.with(MainActivity.this).load(full_path).into(movie_image);
                txt_show.setText(title);
            }
        }
    };
}