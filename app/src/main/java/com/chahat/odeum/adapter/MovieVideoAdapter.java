package com.chahat.odeum.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.object.MovieVideoObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chahat on 29/8/17.
 */

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.MovieVideoViewHolder> {

    List<MovieVideoObject> videoList;
    Context context;

    public MovieVideoAdapter(Context context){
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
        MovieVideoObject movieVideoObject = videoList.get(position);
        Picasso.with(context).load(ApiClient.YOU_TUBE_IMAGE+movieVideoObject.getKey()+"/0.jpg").into(holder.imageViewVideo);
        holder.textViewName.setText(movieVideoObject.getName());
    }

    @Override
    public int getItemCount() {
        if (videoList!=null) return videoList.size();
        else return 0;
    }

    public void setVideoList(List<MovieVideoObject> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    public List<MovieVideoObject> getVideoList() {
        return videoList;
    }

    public class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageViewVideo;
        private TextView textViewName;


        public MovieVideoViewHolder(View itemView) {
            super(itemView);

            imageViewVideo = (ImageView) itemView.findViewById(R.id.imageViewVideo);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
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
