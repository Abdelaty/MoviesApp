package com.example.abdel.movies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdel.movies.R;
import com.example.abdel.movies.models.MoviesModel;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesModel> reviewsArrayList;

    private LayoutInflater layoutInflaterReviews;

    public ReviewsAdapter(Context context, ArrayList<MoviesModel> reviewsDataArrayList) {
        this.context = context;
        this.reviewsArrayList = reviewsDataArrayList;
        layoutInflaterReviews = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflaterReviews.inflate(R.layout.single_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        MoviesModel currentElement = reviewsArrayList.get(position);
        String review = currentElement.getMovieReview();
        String author = currentElement.getMovieAuthor();
        holder.tv_movieReview.setText(review);
        holder.tv_reviewAuthor.setText(author);

    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movieReview;
        TextView tv_reviewAuthor;

        ViewHolder(View itemView) {
            super(itemView);
            tv_movieReview = (TextView) itemView.findViewById(R.id.tv_movie_review);
            tv_reviewAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);

        }
    }

}

