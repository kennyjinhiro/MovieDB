package com.example.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository repository;

    private MovieRepository(){}

    public static MovieRepository getInstance(){
        if(repository==null){
            repository = new MovieRepository();
        }
        return repository;
    }

    public MutableLiveData<Movies> getMovieData(String movieId){
        final MutableLiveData<Movies> result = new MutableLiveData<>();

        ApiService.endpoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<NowPlaying> getNowPlayingData(){
        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();

        ApiService.endpoint().getNowPlaying(Const.API_KEY).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });

        return result;
    }

    public MutableLiveData<String> getGenreData(List<Integer> nowplaying_genrelist){
        final MutableLiveData<String> result = new MutableLiveData<>();

        ApiService.endpoint().getGenre(Const.API_KEY).enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                //Manggil json body
//                result.setValue(response.body());

                //Decompose json
                int print_count = 0;
                int size_nowplaying = nowplaying_genrelist.size();
                String resultString = "";
                List<Genre.Genres> list_genre =  response.body().getGenres();
                for (int i = 0; i < size_nowplaying; i++){
                    for (int j = 0; j < list_genre.size(); j++){
                        if (nowplaying_genrelist.get(i) == list_genre.get(j).getId()){
                            if(i == size_nowplaying - 1){
                                resultString += list_genre.get(j).getName();
                            }
                            else {
                                resultString += list_genre.get(j).getName() + ", ";
                                print_count++;
                            }
                        }
//                        if ((print_count == size_nowplaying) && (nowplaying_genrelist.get(i) == list_genre.get(j).getId())){
//                            resultString += list_genre.get(i).getName();
//                            break;
//                        }
                    }
                }
                result.setValue(resultString);
                resultString = "";
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {

            }
        });
        return result;
    }
}
