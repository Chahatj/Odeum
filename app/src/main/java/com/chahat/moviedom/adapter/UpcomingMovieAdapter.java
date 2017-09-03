package com.chahat.moviedom.adapter;

/**
 * Created by chahat on 24/8/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.moviedom.Interface.LoadPagesInterface;
import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.object.MovieObject;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 24/8/17.
 */

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.NowPlayingViewHolder> {

    private List<MovieObject> movieList;
    private Context context;
    private int totalPages;
    private int currentPage;
    private LoadPagesInterface mLoadListner;
    private SharedItemClickListner onItemClick;

    public UpcomingMovieAdapter(Context context,LoadPagesInterface loadListner,SharedItemClickListner onItemClick){
        this.context = context;
        mLoadListner = loadListner;
        movieList = new ArrayList<>();
        currentPage = 1;
        this.onItemClick = onItemClick;
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

    public List<MovieObject> getMovieList() {
        return movieList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public UpcomingMovieAdapter.NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.upcoming,parent,false);
        return new NowPlayingViewHolder(view);
    }

    public interface OnItemClick{
        void onItemClick(int id, ImageView imageView,String imageUrl);
    }

    @Override
    public void onBindViewHolder(UpcomingMovieAdapter.NowPlayingViewHolder holder, int position) {

        MovieObject movieObject = movieList.get(position);

        Picasso.with(context).load(ApiClient.IMAGE_URL+movieObject.getPosterPath()).into(holder.movieImage);
        holder.tv_rating.setText(String.valueOf(movieObject.getVoteAverage()));
        holder.tv_title.setText(movieObject.getTitle());

        String[] date = movieObject.getReleaseDate().split("-");
        holder.tv_year.setText(String.valueOf(date[0]));

        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(movieObject.getReleaseDate());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            holder.tv_date.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (position == movieList.size() - 1){
            if (currentPage<totalPages){
                currentPage = currentPage + 1;
                mLoadListner.loadPage(currentPage);
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

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movie_image) ImageView movieImage;
        @BindView(R.id.tv_year) TextView tv_year;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_rating)  TextView tv_rating;
        @BindView(R.id.tv_date) TextView tv_date;

        public NowPlayingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ImageView movieImage = (ImageView) view.findViewById(R.id.movie_image);
            MovieObject movieObject = movieList.get(getAdapterPosition());
            onItemClick.onItemClick(movieObject.getId(),movieImage,movieObject.getPosterPath());
        }
    }
}
