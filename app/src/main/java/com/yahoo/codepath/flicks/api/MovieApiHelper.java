package com.yahoo.codepath.flicks.api;

import android.util.Log;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiHelper {
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780";

    private static MovieApiHelper INSTANCE;

    private Retrofit mRetrofit;
    private MovieService mMovieService;

    public static synchronized MovieApiHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieApiHelper();
        }

        return INSTANCE;
    }

    private MovieApiHelper() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url()
                                 .newBuilder()
                                 .addQueryParameter("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed")
                                 .build();
            request = request.newBuilder().url(url).build();
            Log.d("OKHttp", request.toString());
            return chain.proceed(request);
        });

        mRetrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                                          .addConverterFactory(GsonConverterFactory.create())
                                          .client(httpClient.build())
                                          .build();
        mMovieService = mRetrofit.create(MovieService.class);
    }

    public MovieService getNowPlayingService() {
        return mMovieService;
    }
}
