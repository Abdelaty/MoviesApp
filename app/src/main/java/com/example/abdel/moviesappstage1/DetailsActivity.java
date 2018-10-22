package com.example.abdel.moviesappstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.abdel.moviesappstage1.MainActivity.EXTRA_OVERVIEW;
import static com.example.abdel.moviesappstage1.MainActivity.EXTRA_RATE;
import static com.example.abdel.moviesappstage1.MainActivity.EXTRA_RELEASE;
import static com.example.abdel.moviesappstage1.MainActivity.EXTRA_TITLE;
import static com.example.abdel.moviesappstage1.MainActivity.EXTRA_URL;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String movieRate = intent.getStringExtra(EXTRA_RATE);
        String movieReleaseDate = intent.getStringExtra(EXTRA_RELEASE);
        String movieOverview = intent.getStringExtra(EXTRA_OVERVIEW);
        String movieName = intent.getStringExtra(EXTRA_TITLE);


        ImageView moviePoster = findViewById(R.id.iv_movie_poster_detail);
        TextView tvMovieName = findViewById(R.id.tv_movie_name);
        TextView tvMovieRate = findViewById(R.id.tv_movie_Rate);
        tvMovieRate.setVisibility(View.VISIBLE);

        TextView tvMovieOverview = findViewById(R.id.tv_movie_overview);
        tvMovieOverview.setVisibility(View.VISIBLE);

        TextView tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieReleaseDate.setVisibility(View.VISIBLE);


        Picasso.with(this).load(imageUrl).into(moviePoster);
        tvMovieName.setText(movieName);
        tvMovieRate.setText(movieRate);
        tvMovieReleaseDate.setText(movieReleaseDate);
        tvMovieOverview.setText(movieOverview);
       setTitle(movieName);

    }
}

