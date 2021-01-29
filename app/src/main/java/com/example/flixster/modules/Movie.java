package com.example.flixster.modules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String posterPath;
    String backdropPath;
    String title;
    String overview;



    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        // Get each JsonObject inside of movieJsonArray
        for (int i = 0; i < movieJsonArray.length(); i++) {
            // Add each JsonObject into movies list
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getBackdropPath() {
        // Use configuration api to find image sizes
        // Should be comparing the sizes but I am just hardcoded this part for now
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        // Use configuration api to find image sizes
        // Should be comparing the sizes but I am just hardcoded this part for now
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
