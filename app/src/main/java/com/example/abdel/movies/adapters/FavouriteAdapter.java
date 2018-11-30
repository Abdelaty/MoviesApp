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
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private MoviesAdapter.OnItemClickListener mListener;
    private List<DatabaseModel> favArrayList;

    public FavouriteAdapter(Context context, List<DatabaseModel> favArrayList) {
        this.context = context;
        this.favArrayList = favArrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    public void setOnItemClickListener(MoviesAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_movie_item, parent, false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        DatabaseModel currentElement = favArrayList.get(position);
        String imageUrl = currentElement.getImageUrl();
        String title = currentElement.getMovieName();
        String movieId = currentElement.getMovieDbId();
        holder.tv_movieName.setText(title);
        Picasso.with(context).load(imageUrl).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.iv_moviePoster);
    }

    @Override
    public int getItemCount() {
        return favArrayList.size();
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