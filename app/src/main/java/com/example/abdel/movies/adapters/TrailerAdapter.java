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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesModel> trailerArrayList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mTrailerListener;

    public TrailerAdapter(Context context, ArrayList<MoviesModel> dataArrayList) {
        this.context = context;
        this.trailerArrayList = dataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    /*  */

    public void setOnItemClickListener(OnItemClickListener listener) {
        mTrailerListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoviesModel currentElement = trailerArrayList.get(position);
        String trailerUrl = currentElement.getTrailerUrl();
      /*long trailerId=  getItemId(position);
        holder.tv_trailer_num.setText((int) trailerId);*/
    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trailer_num;

        ViewHolder(View itemView) {
            super(itemView);
            tv_trailer_num = (TextView) itemView.findViewById(R.id.trailer_number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTrailerListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mTrailerListener.onItemClick(position);

                        }

                    }
                }
            });
        }

    }
}

