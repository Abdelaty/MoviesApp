package com.example.abdel.moviesappstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesModel> moviesArrayList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;

    public MoviesAdapter(Context context, ArrayList<MoviesModel> dataArrayList) {
        this.context = context;
        this.moviesArrayList = dataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        moviesArrayList.get(position);
        MoviesModel currentElement = moviesArrayList.get(position);
        String imageUrl = currentElement.getmImageUrl();
        String title = currentElement.getmMovieName();
        String rate = currentElement.getmMovieRate();
        String overview = currentElement.getmMovieOverview();
        String releaseDate = currentElement.getmMovieReleaseDate();
        holder.tv_movieName.setText(title);
        holder.tv_movieRate.setText(rate);

        holder.tv_movieRelease.setText(releaseDate);

        holder.tv_movieOverview.setText(overview);

        Picasso.with(context).load(imageUrl).into(holder.iv_moviePoster);
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movieName;
        TextView tv_movieRate;

        TextView tv_movieRelease;
        TextView tv_movieOverview;

        ImageView iv_moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_movieName = (TextView) itemView.findViewById(R.id.movie_name);
            iv_moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
            tv_movieRelease = (TextView) itemView.findViewById(R.id.movie_release_Date);
            tv_movieOverview = (TextView) itemView.findViewById(R.id.movie_overview);
            tv_movieRate = (TextView) itemView.findViewById(R.id.movie_rate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);

                        }

                    }
                }
            });
        }

    }
}

