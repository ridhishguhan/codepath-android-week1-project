package com.yahoo.codepath.flicks.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("/3/movie/now_playing")
    Call<MoviesResponse> getMovies();
}
