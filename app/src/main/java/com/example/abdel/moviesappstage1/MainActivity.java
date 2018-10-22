package com.example.abdel.moviesappstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickListener {
    public static final String EXTRA_URL = "imagePath";
    public static final String EXTRA_TITLE = "movieName";
    public static final String EXTRA_RATE = "movieRate";
    public static final String EXTRA_OVERVIEW = "movieOverview";
    public static final String EXTRA_RELEASE = "movieReleaseDate";
    //String apiKey=BuildConfig;
    String baseUrl = "http://image.tmdb.org/t/p/w185";
    //String apiKey = BuildConfig.API_KEY;
    MenuItem popularItem;
    MenuItem topRatedItem;

    //Your API should be here

    String popularUrl = "http://api.themoviedb.org/3/movie/popular?api_key=";
    String topRatedUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    private RecyclerView moviesRecyclerView;
    private ArrayList<MoviesModel> moviesArrayList;
    private MoviesAdapter moviesAdapter;
    private MoviesAdapter.OnItemClickListener mListener;
    private RequestQueue mRequestQueue;

    public void setOnItemClickListener(MoviesAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        popularItem = (MenuItem) findViewById(R.id.popular);
        topRatedItem = (MenuItem) findViewById(R.id.rated);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesRecyclerView = findViewById(R.id.rv_widget);
        moviesArrayList = new ArrayList<>();
        moviesRecyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(getApplicationContext(), moviesArrayList);
        moviesRecyclerView.setAdapter(moviesAdapter);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(popularUrl);

        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void parseJSON(String url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        if (result.has("title") || result.has("poster_path") || result.has("vote_average") || result.has("overview") || response.has("release_date")) {
                            String movieName = result.getString("title");
                            String imagePath = result.getString("poster_path");
                            String movieRate = result.getString("vote_average");
                            String movieOverview = result.getString("overview");
                            String movieReleaseDate = result.getString("release_date");
                            String imageUrl = baseUrl + imagePath;
                            Log.v("Image Event", "image Url is: " + imageUrl);
                            moviesArrayList.add(new MoviesModel(imageUrl, movieName, movieReleaseDate, movieOverview, movieRate));

                            moviesAdapter = new MoviesAdapter(MainActivity.this, moviesArrayList);
                            moviesRecyclerView.setAdapter(moviesAdapter);
                            moviesAdapter.setOnItemClickListener(MainActivity.this);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("Err", "erroeeeeeeeeeeee");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.popular) {
            Toast.makeText(this, "Popular Movie", Toast.LENGTH_SHORT).show();
            parseJSON(popularUrl);
            moviesArrayList.clear();
            moviesAdapter.notifyDataSetChanged();
            ///   popularItem.setVisible(false);
         /*   if (!topRatedItem.isVisible()) {
                topRatedItem.setVisible(true);
            } else if (popularItem.isVisible()) {
                popularItem.setVisible(false);
            }*/
            return true;
        }
        if (itemThatWasClickedId == R.id.rated) {
            Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
            parseJSON(topRatedUrl);
            moviesArrayList.clear();
            moviesAdapter.notifyDataSetChanged();
           /* if (!popularItem.isVisible()) {
                popularItem.setVisible(true);
            } else if (topRatedItem.isVisible()) {
                topRatedItem.setVisible(false);
            }*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailsActivity.class);
        MoviesModel clickedItem = moviesArrayList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getmMovieName());

        detailIntent.putExtra(EXTRA_RATE, clickedItem.getmMovieRate());
        detailIntent.putExtra(EXTRA_RELEASE, clickedItem.getmMovieReleaseDate());
        detailIntent.putExtra(EXTRA_OVERVIEW, clickedItem.getmMovieOverview());

        startActivity(detailIntent);
    }

}
