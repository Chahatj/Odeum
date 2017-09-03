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
import com.chahat.odeum.object.EpisodeObject;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowSeasonAdapter extends RecyclerView.Adapter<TvShowSeasonAdapter.ViewHolder> {

    List<EpisodeObject> episodeList;
    private String seasonName = "N/A";
    private  Context context;

    public TvShowSeasonAdapter(Context context){
        this.context = context;
        episodeList = new ArrayList<>();
    }

    public void setEpisodeList(List<EpisodeObject> episodeList) {
        this.episodeList = episodeList;
        notifyDataSetChanged();
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public void addEpisodeList(List<EpisodeObject> list){
        this.episodeList.addAll(list);
        notifyDataSetChanged();
    }

    public List<EpisodeObject> getEpisodeList() {
        return episodeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.tv_show_season,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        EpisodeObject episodeObject = episodeList.get(position);
        if (episodeObject.getStillPath()!=null) {
            Picasso.with(context).load(ApiClient.IMAGE_URL + episodeObject.getStillPath()).into(holder.imageView);
        }
        holder.textViewName.setText(episodeObject.getName());
        holder.textViewOverview.setText(episodeObject.getOverview());

        if (episodeObject.getAirDate()!=null){
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(episodeObject.getAirDate());
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(d);
                holder.textViewDate.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+calendar.get(Calendar.DAY_OF_MONTH)+", "+calendar.get(Calendar.YEAR));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (episodeList!=null) return episodeList.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textViewName) TextView textViewName;
        @BindView(R.id.textViewDate) TextView textViewDate;
        @BindView(R.id.textViewOverview) TextView textViewOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
