package com.chahat.moviedom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chahat.moviedom.Interface.LoadPagesInterface;
import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.object.PeopleObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chahat on 31/8/17.
 */

public class PopularPeopleAdapter extends RecyclerView.Adapter<PopularPeopleAdapter.ViewHolder> {

    private Context context;
    private List<PeopleObject> peopleList;
    private int currentPage;
    private int totalPages;
    private LoadPagesInterface mLoadPageListner;
    private SharedItemClickListner mItemClickListner;

    public PopularPeopleAdapter(Context context,LoadPagesInterface loadPagesListner,SharedItemClickListner clickListner){
        this.context  = context;
        peopleList = new ArrayList<>();
        currentPage = 1;
        this.mLoadPageListner = loadPagesListner;
        this.mItemClickListner = clickListner;
    }

    @Override
    public PopularPeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.popular_people,parent,false);
        return new ViewHolder(view);
    }

    public void addPeopleList(List<PeopleObject> list){
        this.peopleList.addAll(list);
        notifyDataSetChanged();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPeopleList(List<PeopleObject> peopleList) {
        this.peopleList = peopleList;
        notifyDataSetChanged();
    }

    public List<PeopleObject> getPeopleList() {
        return peopleList;
    }

    @Override
    public void onBindViewHolder(PopularPeopleAdapter.ViewHolder holder, int position) {

        PeopleObject peopleObject = peopleList.get(position);
        Picasso.with(context).load(ApiClient.IMAGE_URL+peopleObject.getProfilePath()).into(holder.imageViewProfile);
        holder.textViewName.setText(peopleObject.getName());

        if (position == peopleList.size()-1){
            if (currentPage<totalPages){
                currentPage = currentPage+1;
                mLoadPageListner.loadPage(currentPage);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (peopleList!=null) return peopleList.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imageViewProfile) CircleImageView imageViewProfile;
        @BindView(R.id.textViewName) TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PeopleObject peopleObject = peopleList.get(getAdapterPosition());
            mItemClickListner.onItemClick(peopleObject.getId(),imageViewProfile,peopleObject.getProfilePath());
        }
    }
}
