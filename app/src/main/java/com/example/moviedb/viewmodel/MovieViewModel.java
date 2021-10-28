package com.example.moviedb.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository repository;


    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance();
    }

    //Begin of viewmodel get movie by id
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId){
        resultGetMovieById = repository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultMovieById(){
        return resultGetMovieById;
    }
    //End of viewmodel get movie by id


    //Begin of viewmodel get nowplaying by id
    private MutableLiveData<List<NowPlaying.Results>> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(int page){
        resultGetNowPlaying = repository.getNowPlayingData(page);
    }
    public void clearNowPlaying(){
        repository.clearDataNowPlaying();
    }
    public LiveData<List<NowPlaying.Results>> getResultNowPlaying(){
        return resultGetNowPlaying;
    }
    //End of viewmodel get nowplaying by id

    //Begin of viewmodel get nowplaying by id
    private MutableLiveData<List<Upcoming.Results>> resultGetUpcoming = new MutableLiveData<>();
    public void getUpcoming(int page){
        resultGetUpcoming = repository.getUpcomingData(page);
    }
    public void clearUpComing(){
        repository.clearDataUpComing();
    }
    public LiveData<List<Upcoming.Results>> getResultUpcoming(){
        return resultGetUpcoming;
    }
    //End of viewmodel get nowplaying by id


    //Begin of viewmodel get genre by id
    private MutableLiveData<String> resultGetGenre = new MutableLiveData<>();
    public void getGenre(List<Integer> nowplaying_genreids){
        //Proses di viewmodel (tidak jadi)
        //List_genre ini punya NowPlaying, sekarang cocokan sama Genre
        resultGetGenre = repository.getGenreData(nowplaying_genreids);
//        List<Genre.Genres> resultGetGenre = repository.getGenreData().getValue().getGenres();
//        System.out.println(resultGetGenre.get(0).getName());
    }
    public LiveData<String> getResultGenre(){
        return resultGetGenre;
//        MutableLiveData<String> resultGenreLiveData = new MutableLiveData<>();
//        String resultGenre = "";
//        int list_genre_size = list_genre_ids.size();
//        List<Genre.Genres> genre_list = resultGetGenre.getValue().getGenres();
//        for (int i = 0; i < list_genre_size; i++){
//            for (int j = 0; j < genre_list.size(); j++){
//                if (list_genre_ids.get(i) == genre_list.get(j).getId()){
//                    resultGenre += genre_list.get(j).getName();
//                }
//            }
//        }
//        resultGenreLiveData.setValue(resultGenre);
//        return resultGenreLiveData;
    }
    //End of viewmodel get genre by id
}
