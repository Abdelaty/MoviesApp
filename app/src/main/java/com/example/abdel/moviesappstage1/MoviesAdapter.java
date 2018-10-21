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

    public MoviesAdapter(Context context, ArrayList<MoviesModel> dataArrayList) {
        this.context = context;
        this.moviesArrayList = dataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_movie_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        moviesArrayList.get(position);
        MoviesModel currentElement = moviesArrayList.get(position);
        String imageUrl = currentElement.getmImageUrl();
        String title = currentElement.getmMovieName();
        holder.tv_movieName.setText(title);
        Picasso.with(context).load(imageUrl).into(holder.iv_moviePoster);
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movieName;
        ImageView iv_moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_movieName = (TextView) itemView.findViewById(R.id.movie_name);
            iv_moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }
}

