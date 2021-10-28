package com.example.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.retrofit.ApiService;

import java.util.ArrayList;
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

    //ArrayList to keep previous nowplaying
//    public MutableLiveData<NowPlaying> getNowPlayingData(int page_now_playing){
//        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();
//        ApiService.endpoint().getNowPlaying(Const.API_KEY,page_now_playing).enqueue(new Callback<NowPlaying>() {
//            @Override
//            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
//                result.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<NowPlaying> call, Throwable t) {
//
//            }
//        });
//
//        return result;
//    }






    private ArrayList<NowPlaying.Results> list_now_playing = new ArrayList<>();

    //IN API RESULTS
    // if we return results we get object array of one movie
    public MutableLiveData<List<NowPlaying.Results>> getNowPlayingData(int page_now_playing){
        final MutableLiveData<List<NowPlaying.Results>> result = new MutableLiveData<>();
        ApiService.endpoint().getNowPlaying(Const.API_KEY,page_now_playing).enqueue(new Callback<NowPlaying>() {


            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                for (NowPlaying.Results i : response.body().getResults()){
                    list_now_playing.add(i);
                }
                result.setValue((List<NowPlaying.Results>) list_now_playing);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }

        });
        return result;
    }

    private ArrayList<Upcoming.Results> list_upcoming = new ArrayList<>();
    public MutableLiveData<List<Upcoming.Results>> getUpcomingData(int page_up_coming){
        final MutableLiveData<List<Upcoming.Results>> result = new MutableLiveData<>();

        ApiService.endpoint().getUpcoming(Const.API_KEY,page_up_coming).enqueue(new Callback<Upcoming>() {
            @Override
            public void onResponse(Call<Upcoming> call, Response<Upcoming> response) {
                for(Upcoming.Results i : response.body().getResults()){
                    list_upcoming.add(i);
                }
                result.setValue(list_upcoming);
            }

            @Override
            public void onFailure(Call<Upcoming> call, Throwable t) {

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
                            resultString += list_genre.get(j).getName();
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
