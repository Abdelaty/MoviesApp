package com.example.abdel.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdel.movies.BuildConfig;
import com.example.abdel.movies.R;
import com.example.abdel.movies.adapters.ActorsAdapter;
import com.example.abdel.movies.adapters.ReviewsAdapter;
import com.example.abdel.movies.adapters.TrailerAdapter;
import com.example.abdel.movies.database.MovieDatabase;
import com.example.abdel.movies.models.DatabaseModel;
import com.example.abdel.movies.models.MoviesModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.OnItemClickListener {
    private static final String DATABASE_NAME = "movies_db";
    String apiKey = BuildConfig.API_KEY;
    String movieId = "335983";
    String baseUrl = "http://image.tmdb.org/t/p/w185";
    String baseTrailerUrl = "https://www.youtube.com/watch?v=";
    TextView reviewTV;
    ImageButton imageButton;
    String movieReview;
    String movieAuthor;
    String actorName;
    String actorChar;
    String actorImageUrl;
    String actorFullImage;
    String trailerFullImage;
    Context context = this;
    String movieName, imageUrl;
    private TrailerAdapter.OnItemClickListener mListener;
    private RecyclerView reviewRecyclerView;
    private ArrayList<MoviesModel> reviewArrayList;
    private ReviewsAdapter reviewAdapter;
    private RequestQueue mRequestQueue1, mRequestQueue2, mRequestQueue3;
    private RecyclerView artistRecyclerView;
    private ArrayList<MoviesModel> artistArrayList;
    private ActorsAdapter artistAdapter;
    private RecyclerView trailerRecyclerView;
    private ArrayList<MoviesModel> trailerArrayList;
    private ArrayList<DatabaseModel> moviesArrayList = new ArrayList<DatabaseModel>();
    boolean isFavourite = false;
    private TrailerAdapter trailerAdapter;
    private MovieDatabase movieDatabase;
    private Executor executor;
    private DatabaseModel movie;

    public void setOnItemClickListener(TrailerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        imageButton = findViewById(R.id.fav_button);




        movieDatabase = MovieDatabase.getAppInstance(getApplicationContext());
        movieId = intent.getStringExtra("movieId");
        String REVIEW_URL = " https://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=" + apiKey;
        String CAST_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/casts?api_key=" + apiKey;
        String TRAILER_URL = " https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + apiKey;

        reviewTV = findViewById(R.id.tv_movie_review_label);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onFavouriteClick();
            }
        });
        getSetData(intent);
        mRequestQueue1 = Volley.newRequestQueue(this);
        mRequestQueue2 = Volley.newRequestQueue(this);
        reviewRecyclerView = findViewById(R.id.reviews_rv);
        reviewArrayList = new ArrayList<>();
        reviewRecyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewsAdapter(getApplicationContext(), reviewArrayList);
        reviewRecyclerView.setAdapter(reviewAdapter);
        parseReviewJSON(REVIEW_URL);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));

        artistRecyclerView = findViewById(R.id.actors_rv);
        artistArrayList = new ArrayList<>();
        artistRecyclerView.setHasFixedSize(true);
        artistAdapter = new ActorsAdapter(getApplicationContext(), artistArrayList);
        artistRecyclerView.setAdapter(artistAdapter);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
        parseCastJSON(CAST_URL);

        mRequestQueue3 = Volley.newRequestQueue(this);
        trailerRecyclerView = findViewById(R.id.trailers_rv);
        trailerArrayList = new ArrayList<>();
        trailerRecyclerView.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerArrayList);
        trailerRecyclerView.setAdapter(trailerAdapter);
        parseTrailerJSON(TRAILER_URL);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
    }

    private void parseReviewJSON(String url) {
//NEW

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response2) {

                try {

                    JSONArray jsonArray2 = response2.getJSONArray("results");

                    // Log.v("JSONRviw Event", "Array  is: " + jsonArray2);
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject result1 = jsonArray2.getJSONObject(i);
                        //edit review
                        if (result1.has("content") || result1.has("id") || result1.has("author")) {

                            movieReview = result1.getString("content");
                            movieAuthor = result1.getString("author");
                            //  String movieId = result1.getString("movieId");
                            Log.v("Movie Event", "movie Review: " + movieReview);
                            Log.v("Movie Event", " movieAuthor: " + movieAuthor);
                            //check here again
                            reviewArrayList.add(new MoviesModel(movieReview, movieId, movieAuthor, 0));

                            reviewAdapter = new ReviewsAdapter(DetailsActivity.this, reviewArrayList);
                            reviewRecyclerView.setAdapter(reviewAdapter);
                        } else {
                            reviewTV.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("JSON EXCEPTION Event", "No data reccieved ");
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue1.add(request2);

    }

    private void parseCastJSON(String url) {
        //NEW
        JsonObjectRequest requestCast = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response1) {

                try {

                    JSONArray jsonArray = response1.getJSONArray("cast");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        //edit review
                        if (result.has("name") || result.has("character") || result.has("profile_path")) {

                            actorName = result.getString("name");
                            actorChar = result.getString("character");
                            actorImageUrl = result.getString("profile_path");
                            actorFullImage = baseUrl + actorImageUrl;
                            Log.v("Image Event", "image Url is: " + actorImageUrl);
                            artistArrayList.add(new MoviesModel(actorName, actorChar, actorFullImage));

                            artistAdapter = new ActorsAdapter(DetailsActivity.this, artistArrayList);
                            artistRecyclerView.setAdapter(artistAdapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("JSON EXCEPTION Event", "No data reccieved ");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue2.add(requestCast);
    }

    private void parseTrailerJSON(String url) {
        //NEW
        JsonObjectRequest requestTrailer = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response3) {

                try {

                    JSONArray jsonArray = response3.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        //edit review
                        if (result.has("key")) {
                            String trailerPath = result.getString("key");
                            trailerFullImage = baseTrailerUrl + trailerPath;
                            Log.v("Image Event", "image Url is: " + trailerFullImage);
                            trailerArrayList.add(new MoviesModel(trailerFullImage));

                            trailerAdapter = new TrailerAdapter(DetailsActivity.this, trailerArrayList);
                            trailerRecyclerView.setAdapter(trailerAdapter);
                            trailerAdapter.setOnItemClickListener(DetailsActivity.this);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("JSON EXCEPTION Event", "No data reccieved ");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue3.add(requestTrailer);
    }

    public void getSetData(Intent intent) {
        ImageView moviePoster = findViewById(R.id.iv_movie_poster_detail);
        TextView tvMovieName = findViewById(R.id.tv_movie_name);
        TextView tvMovieRate = findViewById(R.id.tv_movie_Rate);
        TextView tvMovieOverview = findViewById(R.id.tv_movie_overview);
        TextView tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        imageUrl = intent.getStringExtra("imagePath");
        String movieRate = intent.getStringExtra("movieRate");
        String movieReleaseDate = intent.getStringExtra("movieReleaseDate");
        String movieOverview = intent.getStringExtra("movieOverview");
        movieName = intent.getStringExtra("movieName");

        Picasso.with(this).load(imageUrl).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(moviePoster);
        tvMovieName.setText(movieName);
        tvMovieRate.setText(movieRate);
        tvMovieReleaseDate.setText(movieReleaseDate);
        tvMovieOverview.setText(movieOverview);
        setTitle(movieName);

    }

    @Override
    public void onItemClick(int position) {
        MoviesModel clickedItem = trailerArrayList.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedItem.getTrailerUrl()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    public void onFavouriteClick() {
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isFavourite) {
                    imageButton.setBackgroundResource(R.drawable.ic_favorite);
                    isFavourite = false;

                } else {
                    // imageButton.setBackgroundResource(R.drawable.ic_favorite_border);
                    isFavourite = true;

                }

            }
        });        DatabaseModel movie = new DatabaseModel();
        if (!movieDatabase.daoAccess().getMovieWithId(movieId)) {

            movie.setMovieDbId(movieId);
            movie.setMovieName(movieName);
            movie.setImageUrl(imageUrl);
            Log.v("getMovieDbId", movie.getMovieDbId());
            Log.v("getMovieName", movie.getMovieName());
            Log.v("getImageUrl", movie.getImageUrl());
            isFavourite = false;
            movieDatabase.daoAccess().insert(movie);
            Toast.makeText(context, R.string.toast_added, Toast.LENGTH_SHORT).show();

        } else {
            movieDatabase.daoAccess().delMovieWithId(movieId);
            movieDatabase.daoAccess().delete(movie);
            Toast.makeText(context, R.string.toast_removed, Toast.LENGTH_SHORT).show();
            isFavourite = true;

        }
    }


}

