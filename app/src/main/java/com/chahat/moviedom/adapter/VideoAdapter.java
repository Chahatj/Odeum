package com.chahat.moviedom.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.object.VideoObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 29/8/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MovieVideoViewHolder> {

    List<VideoObject> videoList;
    Context context;

    public VideoAdapter(Context context){
        this.context = context;
        videoList = new ArrayList<>();
    }

    @Override
    public MovieVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_video,parent,false);
        return new MovieVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieVideoViewHolder holder, int position) {
        VideoObject videoObject = videoList.get(position);
        Picasso.with(context).load(ApiClient.YOU_TUBE_IMAGE+videoObject.getKey()+"/0.jpg").into(holder.imageViewVideo);
        holder.textViewName.setText(videoObject.getName());
    }

    @Override
    public int getItemCount() {
        if (videoList!=null) return videoList.size();
        else return 0;
    }

    public void setVideoList(List<VideoObject> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    public List<VideoObject> getVideoList() {
        return videoList;
    }

    public class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageViewVideo) ImageView imageViewVideo;
        @BindView(R.id.textViewName) TextView textViewName;


        public MovieVideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(ApiClient.YOU_TUBE_VIDEO+videoList.get(getAdapterPosition()).getKey()));
            context.startActivity(intent);
        }
    }
}
