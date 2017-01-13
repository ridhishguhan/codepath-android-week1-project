package com.yahoo.codepath.flicks.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    private List<MovieListItem> mResults;

    public List<MovieListItem> getResults() {
        return mResults;
    }
}
