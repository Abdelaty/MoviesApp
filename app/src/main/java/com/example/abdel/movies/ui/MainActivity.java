package com.example.abdel.movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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
import com.example.abdel.movies.BuildConfig;
import com.example.abdel.movies.R;
import com.example.abdel.movies.adapters.FavouriteAdapter;
import com.example.abdel.movies.adapters.MoviesAdapter;
import com.example.abdel.movies.database.MovieDatabase;
import com.example.abdel.movies.models.DatabaseModel;
import com.example.abdel.movies.models.MoviesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickListener {
    static long currentVisiblePosition = 0; // this variable should be static in class
    private static Bundle mBundleRecyclerViewState;
    String apiKey = BuildConfig.API_KEY;
    String baseUrl = "http://image.tmdb.org/t/p/w185";
    String popularUrl = " http://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
    String topRatedUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + apiKey;
    MenuItem popularItem;
    MenuItem topRatedItem;
    GridLayoutManager gridLayoutManager;
    int x = 0;
    String LIST_STATE_KEY = "hi";
    private MovieDatabase movieDatabase;
    private RecyclerView moviesRecyclerView;
    private ArrayList<MoviesModel> moviesArrayList;
    private MoviesAdapter moviesAdapter;
    private RequestQueue mRequestQueue;
    private List<DatabaseModel> favouriteArrayList = new List<DatabaseModel>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<DatabaseModel> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(DatabaseModel databaseModel) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends DatabaseModel> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends DatabaseModel> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public DatabaseModel get(int index) {
            return null;
        }

        @Override
        public DatabaseModel set(int index, DatabaseModel element) {
            return null;
        }

        @Override
        public void add(int index, DatabaseModel element) {

        }

        @Override
        public DatabaseModel remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<DatabaseModel> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<DatabaseModel> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<DatabaseModel> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    private FavouriteAdapter favAdapter;
    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        popularItem = findViewById(R.id.popular);
        topRatedItem = findViewById(R.id.rated);
        movieDatabase = MovieDatabase.getAppInstance(getApplicationContext());
        moviesRecyclerView = findViewById(R.id.rv_widget);
        moviesArrayList = new ArrayList<>();
        moviesRecyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(getApplicationContext(), moviesArrayList);
        moviesRecyclerView.setAdapter(moviesAdapter);
        mRequestQueue = Volley.newRequestQueue(this);
        if (savedInstanceState != null) {
            x = savedInstanceState.getInt("movieKey");

            switch (x) {
                case 0: {
                    moviesArrayList.clear();

                    parseJSON(popularUrl);
                    moviesArrayList.clear();
                    moviesAdapter.notifyDataSetChanged();
                    Toast.makeText(this, R.string.pop_movie, Toast.LENGTH_SHORT).show();

                }
                case 1: {
                    moviesArrayList.clear();
                    parseJSON(topRatedUrl);
                    moviesArrayList.clear();
                    moviesAdapter.notifyDataSetChanged();
                    Toast.makeText(this, R.string.top_movie, Toast.LENGTH_SHORT).show();

                }
                case 2: {
                    Toast.makeText(this, R.string.fav_movie, Toast.LENGTH_SHORT).show();
                    moviesArrayList.clear();
                    favouriteArrayList = movieDatabase.daoAccess().getMovies();
                    favAdapter = new FavouriteAdapter(this, favouriteArrayList);
                    moviesRecyclerView.setAdapter(favAdapter);
                    favAdapter.notifyDataSetChanged();
                }
            }
        } else {
            parseJSON(popularUrl);
        }
        gridLayoutManager = new GridLayoutManager(this, 2);

        moviesRecyclerView.setLayoutManager(gridLayoutManager);
        currentVisiblePosition = ((GridLayoutManager) moviesRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

    }

    private void parseJSON(String url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        if (result.has("title") || result.has("poster_path") || result.has("vote_average") || result.has("id") || result.has("release_date") || result.has("id")) {
                            String movieName = result.getString("title");
                            String imagePath = result.getString("poster_path");
                            String movieRate = result.getString("vote_average");
                            String movieId = result.getString("id");
                            String movieOverview = result.getString("overview");
                            String movieReleaseDate = result.getString("release_date");
                            String imageUrl = baseUrl + imagePath;
                            moviesArrayList.add(new MoviesModel(imageUrl, movieName, movieReleaseDate, movieOverview, movieRate, movieId));
                            moviesAdapter = new MoviesAdapter(MainActivity.this, moviesArrayList);
                            moviesRecyclerView.setAdapter(moviesAdapter);
                            moviesAdapter.setOnItemClickListener(MainActivity.this);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("JSON EXCEPTION Event", "No data received ");
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
            moviesArrayList.clear();
            Toast.makeText(this, R.string.pop_movie, Toast.LENGTH_SHORT).show();
            parseJSON(popularUrl);
            moviesAdapter.notifyDataSetChanged();

            x = 0;
            Log.v("x", String.valueOf(x));
            return true;
        } else if (itemThatWasClickedId == R.id.rated) {
            moviesArrayList.clear();

            Toast.makeText(this, R.string.top_movie, Toast.LENGTH_SHORT).show();
            parseJSON(topRatedUrl);
            moviesAdapter.notifyDataSetChanged();


            x = 1;
            return true;
        } else if (itemThatWasClickedId == R.id.fav) {
            Toast.makeText(this, R.string.fav_movie, Toast.LENGTH_SHORT).show();

            moviesArrayList.clear();
            favouriteArrayList = movieDatabase.daoAccess().getMovies();
            favAdapter = new FavouriteAdapter(this, favouriteArrayList);
            moviesRecyclerView.setAdapter(favAdapter);
            favAdapter.notifyDataSetChanged();

            x = 2;

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailsActivity.class);
        MoviesModel clickedItem = moviesArrayList.get(position);
        detailIntent.putExtra("imagePath", clickedItem.getImageUrl());
        detailIntent.putExtra("movieName", clickedItem.getMovieName());
        detailIntent.putExtra("movieRate", clickedItem.getMovieRate());
        detailIntent.putExtra("movieReleaseDate", clickedItem.getMovieReleaseDate());
        detailIntent.putExtra("movieOverview", clickedItem.getMovieOverview());
        detailIntent.putExtra("movieId", clickedItem.getMovieId());


        startActivity(detailIntent);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("movieKey", x);
        mListState = gridLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
        //   mListState = gridLayoutManager.onSaveInstanceState();
        //    state.putParcelable(LIST_STATE_KEY, mListState);
        Log.v("onSaveInstanceState", String.valueOf(x));
        // Save list state
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        //   if (savedInstanceState != null)
        //       mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
    }

    /* @Override
     protected void onResume() {
         super.onResume();

         if (mListState != null) {
             gridLayoutManager.onRestoreInstanceState(mListState);
         }
     }*/
  /*  @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = moviesRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(LIST_STATE_KEY, listState);
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        moviesRecyclerView.getLayoutManager().scrollToPosition((int) currentVisiblePosition);
        currentVisiblePosition = 0;
        if (mListState != null) {
            gridLayoutManager.onRestoreInstanceState(mListState);
        }
    }

}



