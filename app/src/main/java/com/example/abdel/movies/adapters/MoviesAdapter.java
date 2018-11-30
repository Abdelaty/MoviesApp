package com.example.abdel.movies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdel.movies.R;
import com.example.abdel.movies.models.DatabaseModel;
import com.example.abdel.movies.models.MoviesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesModel> moviesArrayList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;
    private ArrayList<DatabaseModel> favArrayList;

    public MoviesAdapter(Context context, ArrayList<MoviesModel> dataArrayList) {
        this.context = context;
        this.moviesArrayList = dataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }
  /*  MoviesAdapter(Context context, ArrayList<DatabaseModel> moviesArrayList,int i) {
        this.context = context;
        this.favArrayList = moviesArrayList;
        layoutInflater = LayoutInflater.from(context);
    }
    /*  */

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
        MoviesModel currentElement = moviesArrayList.get(position);
        String imageUrl = currentElement.getImageUrl();
        String title = currentElement.getMovieName();
        String movieId = currentElement.getMovieId();

        String rate = currentElement.getMovieRate();
        String overview = currentElement.getMovieOverview();
        String releaseDate = currentElement.getMovieReleaseDate();
        holder.tv_movieName.setText(title);
        Picasso.with(context).load(imageUrl).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.iv_moviePoster);
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movieName;

        ImageView iv_moviePoster;

        ViewHolder(View itemView) {
            super(itemView);
            tv_movieName = (TextView) itemView.findViewById(R.id.movie_name);
            iv_moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
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

