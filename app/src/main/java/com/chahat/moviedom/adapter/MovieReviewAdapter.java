package com.chahat.moviedom.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chahat.moviedom.R;
import com.chahat.moviedom.object.MovieReviewObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 29/8/17.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewViewHolder> {

    private List<MovieReviewObject> reviewList;
    private Context context;

    public MovieReviewAdapter(Context context){
        this.context = context;
        reviewList = new ArrayList<>();
    }

    public void setReviewList(List<MovieReviewObject> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_review,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {

        MovieReviewObject movieReviewObject = reviewList.get(position);
        holder.textViewAuthor.setText(movieReviewObject.getAuthor());
        holder.textViewContent.setText(movieReviewObject.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList!=null) return reviewList.size();
        else return 0;
    }

    public List<MovieReviewObject> getReviewList() {
        return reviewList;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textViewAuthor) TextView textViewAuthor;
        @BindView(R.id.textViewContent) TextView textViewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(reviewList.get(getAdapterPosition()).getUrl()));
            context.startActivity(intent);
        }
    }
}
