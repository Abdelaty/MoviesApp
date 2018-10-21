package com.example.abdel.moviesappstage1;

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


public class MainActivity extends AppCompatActivity {
    String baseUrl = "http://image.tmdb.org/t/p/w185";
    String popularUrl = "http://api.themoviedb.org/3/movie/popular?api_key=28d6cfccb10e5e4d42b71bdfd19ec50b";
    String topRatedUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=28d6cfccb10e5e4d42b71bdfd19ec50b";
    private RecyclerView moviesRecyclerView;
    private ArrayList<MoviesModel> moviesArrayList;
    private MoviesAdapter moviesAdapter;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesRecyclerView = findViewById(R.id.rv_widget);
        moviesArrayList = new ArrayList<>();
        moviesRecyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(getApplicationContext(), moviesArrayList);
        moviesRecyclerView.setAdapter(moviesAdapter);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(popularUrl);


        //Linear to gridView
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void parseJSON(String url) {

        String apiKey = "";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //be Ware
                try {
                    //   JSONObject jsonObject = response
                    //be ware
                    JSONArray jsonArray = response.getJSONArray("results");
                    //

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        String movieName = result.getString("title");
                        String imagePath = result.getString("poster_path");
                        String imageUrl = baseUrl + imagePath;
                        Log.v("Image Event", "image Url is: " + imageUrl);
                        moviesArrayList.add(new MoviesModel(imageUrl, movieName));

                        moviesAdapter = new MoviesAdapter(MainActivity.this, moviesArrayList);
                        moviesRecyclerView.setAdapter(moviesAdapter);
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

            return true;
        }
        if (itemThatWasClickedId == R.id.Rated) {
            Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
            parseJSON(topRatedUrl);
            moviesArrayList.clear();
            moviesAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
