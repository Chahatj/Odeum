package com.chahat.moviedom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.object.CastObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chahat on 29/8/17.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>  {

    private List<CastObject> castList;
    private Context context;
    private SharedItemClickListner sharedItemClickListner;

    public CastAdapter(Context context,SharedItemClickListner itemClickListner){
        this.context = context;
        castList = new ArrayList<>();
        this.sharedItemClickListner = itemClickListner;
    }

    public void setCastList(List<CastObject> castList) {
        this.castList = castList;
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

        CastObject castObject = castList.get(position);
        Picasso.with(context).load(ApiClient.IMAGE_URL+castObject.getProfilePath()).into(holder.imageViewProfile);
        holder.textViewName.setText(castObject.getName());
        holder.textViewAs.setText(castObject.getCharacter());
    }

    public List<CastObject> getCastList() {
        return castList;
    }

    @Override
    public int getItemCount() {
        if (castList!=null) return castList.size();
        else return 0;
    }

    public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imageViewProfile) CircleImageView imageViewProfile;
        @BindView(R.id.textViewName) TextView textViewName;
        @BindView(R.id.textViewAs) TextView textViewAs;

        public CastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CastObject castObject = castList.get(getAdapterPosition());
            sharedItemClickListner.onItemClick(castObject.getId(),imageViewProfile,castObject.getProfilePath());
        }
    }
}
