package com.chahat.moviedom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.object.ImagesObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleImageAdapter extends RecyclerView.Adapter<PeopleImageAdapter.ViewHolder> {

    private Context context;
    private List<ImagesObject> imagesList;
    private SharedItemClickListner sharedItemClickListner;

    public PeopleImageAdapter(Context context,SharedItemClickListner itemClickListner){
        this.context = context;
        imagesList = new ArrayList<>();
        this.sharedItemClickListner = itemClickListner;
    }

    public void setImagesList(List<ImagesObject> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }

    public List<ImagesObject> getImagesList() {
        return imagesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.images,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImagesObject imagesObject = imagesList.get(position);
        Picasso.with(context).load(ApiClient.IMAGE_URL+imagesObject.getFilePath()).into(holder.peopleImage);
    }

    @Override
    public int getItemCount() {
        if (imagesList!=null) return imagesList.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.peopleImage) ImageView peopleImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ImagesObject imagesObject = imagesList.get(getAdapterPosition());
            sharedItemClickListner.onItemClick(0,peopleImage,imagesObject.getFilePath());
        }
    }
}
