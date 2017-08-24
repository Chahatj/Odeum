package com.chahat.odeum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.object.MovieObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by chahat on 24/8/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.NowPlayingViewHolder> {

    private List<MovieObject> movieList;
    private Context context;
    private int totalPages;
    private int currentPage=1;
    private LoadListner mLoadListner;

    public MovieAdapter(Context context,LoadListner loadListner){
        this.context = context;
        mLoadListner = loadListner;
        movieList = new ArrayList<>();
    }

    public void setMovieList(List<MovieObject> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void addMovieList(List<MovieObject> movieList){
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public MovieAdapter.NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.now_playing,parent,false);
        return new NowPlayingViewHolder(view);
    }

    public interface LoadListner{
        void loadMorePages(int page);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.NowPlayingViewHolder holder, int position) {

        MovieObject movieObject = movieList.get(position);

        Picasso.with(context).load(ApiClient.IMAGE_URL+movieObject.getPosterPath()).into(holder.movieImage);
        holder.tv_rating.setText(String.valueOf(movieObject.getVoteAverage()));
        holder.tv_title.setText(movieObject.getTitle());

        String[] date = movieObject.getReleaseDate().split("-");
        holder.tv_year.setText(String.valueOf(date[0]));

        if (position == movieList.size() - 1){
            if (currentPage<=totalPages){
                currentPage = currentPage + 1;
                mLoadListner.loadMorePages(currentPage);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (movieList.size()!=0){
            return movieList.size();
        }else {
            return 0;
        }
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder{

        private ImageView movieImage;
        private TextView tv_year,tv_title,tv_rating;

        public NowPlayingViewHolder(View itemView) {
            super(itemView);

            movieImage = (ImageView) itemView.findViewById(R.id.movie_image);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_rating = (TextView) itemView.findViewById(R.id.tv_rating);
        }
    }
}
