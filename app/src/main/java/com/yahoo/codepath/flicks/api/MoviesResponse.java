package com.yahoo.codepath.flicks.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> mResults;

    public List<Movie> getResults() {
        return mResults;
    }
}
