package com.example.abdel.moviesappstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imagePath");
        String movieRate = intent.getStringExtra("movieRate");
        String movieReleaseDate = intent.getStringExtra("movieReleaseDate");
        String movieOverview = intent.getStringExtra("movieOverview");
        String movieName = intent.getStringExtra("movieName");

        ImageView moviePoster = findViewById(R.id.iv_movie_poster_detail);
        TextView tvMovieName = findViewById(R.id.tv_movie_name);
        TextView tvMovieRate = findViewById(R.id.tv_movie_Rate);
        tvMovieRate.setVisibility(View.VISIBLE);
        TextView tvMovieOverview = findViewById(R.id.tv_movie_overview);
        tvMovieOverview.setVisibility(View.VISIBLE);

        TextView tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieReleaseDate.setVisibility(View.VISIBLE);


        Picasso.with(this).load(imageUrl).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(moviePoster);
        tvMovieName.setText(movieName);
        tvMovieRate.setText(movieRate);
        tvMovieReleaseDate.setText(movieReleaseDate);
        tvMovieOverview.setText(movieOverview);
        setTitle(movieName);

    }
}

