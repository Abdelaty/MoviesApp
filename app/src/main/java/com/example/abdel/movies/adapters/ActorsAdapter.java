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
import com.example.abdel.movies.models.MoviesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesModel> artistArrayList;
    private LayoutInflater layoutInflater;

    public ActorsAdapter(Context context, ArrayList<MoviesModel> artistDataArrayList) {
        this.context = context;
        this.artistArrayList = artistDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ActorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_actor_item, parent, false);
        return new ActorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorsAdapter.ViewHolder holder, int position) {
        MoviesModel currentElement = artistArrayList.get(position);
        String ActorImageUrl = currentElement.getActorImage();

        Picasso.with(context).load(ActorImageUrl).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.iv_actorImage);
        String ActorName = currentElement.getActorName();
        String ActorChar = currentElement.getActorCharacter();
        holder.iv_actorName.setText(ActorName);
        holder.iv_actorChar.setText(ActorChar);
    }

    @Override
    public int getItemCount() {
        return artistArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_actorImage;
        TextView iv_actorName, iv_actorChar;

        ViewHolder(View itemView) {
            super(itemView);
            iv_actorImage = (ImageView) itemView.findViewById(R.id.actor_image);
            iv_actorName = (TextView) itemView.findViewById(R.id.actor_name);
            iv_actorChar = (TextView) itemView.findViewById(R.id.actor_char);
        }
    }

}
