package com.chahat.odeum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.Interface.SharedItemClickListner;
import com.chahat.odeum.R;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.object.MovieObject;
import com.squareup.picasso.Picasso;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 29/8/17.
 */

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.NowPlayingViewHolder> {

    private List<MovieObject> movieList;
    private Context context;
    private SharedItemClickListner onItemClickListner;

    public SimilarMovieAdapter(Context context,SharedItemClickListner onItemClickListner){
        this.context = context;
        movieList = new ArrayList<>();
        this.onItemClickListner = onItemClickListner;
    }

    public void setMovieList(List<MovieObject> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }


    public interface OnItemClickListner{
        void onItemClick(int id,ImageView imageView,String imageURL);
    }

    public List<MovieObject> getMovieList() {
        return movieList;
    }

    @Override
    public SimilarMovieAdapter.NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.similar_movie,parent,false);
        return new NowPlayingViewHolder(view);
    }

    public interface LoadListner{
        void loadMorePages(int page);
    }

    @Override
    public void onBindViewHolder(SimilarMovieAdapter.NowPlayingViewHolder holder, int position) {

        MovieObject movieObject = movieList.get(position);

        Picasso.with(context).load(ApiClient.IMAGE_URL+movieObject.getPosterPath()).into(holder.movieImage);
        holder.tv_title.setText(movieObject.getTitle());

        String[] date = movieObject.getReleaseDate().split("-");
        holder.tv_year.setText(String.valueOf(date[0]));

    }

    @Override
    public int getItemCount() {
        if (movieList.size()!=0){
            return movieList.size();
        }else {
            return 0;
        }
    }


    public class NowPlayingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imageViewMovie) ImageView movieImage;
        @BindView(R.id.textViewYear) TextView tv_year;
        @BindView(R.id.textViewName) TextView tv_title;

        public NowPlayingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MovieObject movieObject  = movieList.get(getAdapterPosition());
            onItemClickListner.onItemClick(movieObject.getId(),movieImage,movieObject.getPosterPath());
        }
    }
}
