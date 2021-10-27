package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.CardViewViewHolder> {

    private Context context;
    private List<Movies.ProductionCompanies> listMovies;
    private List<Movies.ProductionCompanies> getListMovies(){return listMovies;}

    public void setListMovies(List<Movies.ProductionCompanies> listMovies){
        this.listMovies = listMovies;
    }
    public MoviesAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_companies,parent,false);
        return new MoviesAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final Movies.ProductionCompanies results = getListMovies().get(position);
        String error_link = "https://rimatour.com/wp-content/uploads/2017/09/No-image-found.jpg";
        if (results != null) {
            Glide.with(context).load(Const.IMG_URL + results.getLogo_path()).into(holder.img_company);
        } else{
            Glide.with(context).load(error_link).into(holder.img_company);
        }
    }

    @Override
    public int getItemCount() {
        return getListMovies().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_company;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_company = itemView.findViewById(R.id.img_companies);
        }
    }
}
