package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.modules.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // URL for API
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // Tag for Log debugging
    public static final String TAG = "MainActivity";

    // This is where the stream of movies will be held
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        // Create an Adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // Set the adapter on the RecyclerView
        rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager on the RecyclerView
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Create async client
        AsyncHttpClient client = new AsyncHttpClient();
        
        // Attempt async connection
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {

            // Successful Connection
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG, "Successful");

                // Read jsonObject data
                JSONObject jsonObject = json.jsonObject;
                try {
                    // Read jsonObject's "results" array data
                    // This is an array with objects of "results" inside
                    JSONArray resultsJsonArray = jsonObject.getJSONArray("results");
                    Log.i(TAG, resultsJsonArray.toString());
                    // get all jsonObject data that is inside of resultsJsonArray
                    movies.addAll(Movie.fromJsonArray(resultsJsonArray));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movie size: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.i(TAG, "FAILURE");
            }
        });
    }
}