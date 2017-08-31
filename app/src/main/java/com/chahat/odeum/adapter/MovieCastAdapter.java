package com.chahat.odeum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.object.MovieCastObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chahat on 29/8/17.
 */

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CastViewHolder> {

    List<MovieCastObject> movieCastList;
    Context context;

    public MovieCastAdapter(Context context){
        this.context = context;
        movieCastList = new ArrayList<>();
    }

    public void setMovieCastList(List<MovieCastObject> movieCastList) {
        this.movieCastList = movieCastList;
        notifyDataSetChanged();
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_cast,parent,false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {

        MovieCastObject movieCastObject = movieCastList.get(position);
        Picasso.with(context).load(ApiClient.IMAGE_URL+movieCastObject.getProfile()).into(holder.imageViewProfile);
        holder.textViewName.setText(movieCastObject.getName());
        holder.textViewAs.setText(movieCastObject.getCharacter());
    }

    public List<MovieCastObject> getMovieCastList() {
        return movieCastList;
    }

    @Override
    public int getItemCount() {
        if (movieCastList!=null) return movieCastList.size();
        else return 0;
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageViewProfile;
        private TextView textViewName,textViewAs;

        public CastViewHolder(View itemView) {
            super(itemView);

            imageViewProfile = (CircleImageView) itemView.findViewById(R.id.imageViewProfile);
            textViewAs = (TextView) itemView.findViewById(R.id.textViewAs);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }
    }
}
