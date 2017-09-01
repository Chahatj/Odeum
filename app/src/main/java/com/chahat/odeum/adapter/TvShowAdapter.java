package com.chahat.odeum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.Interface.LoadPagesInterface;
import com.chahat.odeum.Interface.SharedItemClickListner;
import com.chahat.odeum.R;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.object.MovieObject;
import com.chahat.odeum.object.TvShowObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 1/9/17.
 */

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private List<TvShowObject> tvShowList;
    private Context context;
    private int totalPages;
    private int currentPage;
    private LoadPagesInterface mLoadListner;
    private SharedItemClickListner onItemClickListner;

    public TvShowAdapter(Context context,LoadPagesInterface loadListner,SharedItemClickListner onItemClickListner){
        this.context = context;
        mLoadListner = loadListner;
        tvShowList = new ArrayList<>();
        currentPage = 1;
        this.onItemClickListner = onItemClickListner;
    }

    public void setTvShowList(List<TvShowObject> tvShowList) {
        this.tvShowList = tvShowList;
        notifyDataSetChanged();
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void addTvShowList(List<TvShowObject> tvShowList){
        this.tvShowList.addAll(tvShowList);
        notifyDataSetChanged();
    }

    public List<TvShowObject> getTvShowList() {
        return tvShowList;
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
    public TvShowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.now_playing,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvShowAdapter.ViewHolder holder, int position) {

        TvShowObject tvShowObject = tvShowList.get(position);

        Picasso.with(context).load(ApiClient.IMAGE_URL+tvShowObject.getPosterPath()).into(holder.movieImage);
        holder.tv_rating.setText(String.valueOf(tvShowObject.getVoteAverage()));
        holder.tv_title.setText(tvShowObject.getName());

        if (tvShowObject.getFirstAirDate()!=null){
            String[] date = tvShowObject.getFirstAirDate().split("-");
            holder.tv_year.setText(String.valueOf(date[0]));
        }


        if (position == tvShowList.size() - 1){
            Log.d("TvShowAdapter","in"+tvShowList.size());
            if (currentPage<totalPages){
                currentPage = currentPage + 1;
                Log.d("TvShowAdapter",currentPage+"");
                mLoadListner.loadPage(currentPage);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (tvShowList!=null){
            return tvShowList.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movie_image) ImageView movieImage;
        @BindView(R.id.tv_year) TextView tv_year;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_rating) TextView tv_rating;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ImageView movieImage = (ImageView) view.findViewById(R.id.movie_image);
            TvShowObject tvShowObject = tvShowList.get(getAdapterPosition());
            onItemClickListner.onItemClick(tvShowObject.getId(),movieImage,tvShowObject.getPosterPath());
        }
    }
}
