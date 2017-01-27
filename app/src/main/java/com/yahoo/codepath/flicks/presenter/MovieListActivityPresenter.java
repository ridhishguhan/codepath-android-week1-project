package com.yahoo.codepath.flicks.presenter;

import com.yahoo.codepath.flicks.activity.MovieDetailsActivity;
import com.yahoo.codepath.flicks.activity.MovieListActivityMVP;
import com.yahoo.codepath.flicks.adapter.MovieListAdapter;
import com.yahoo.codepath.flicks.api.Movie;
import com.yahoo.codepath.flicks.api.MovieApiHelper;
import com.yahoo.codepath.flicks.api.MoviesResponse;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

public class MovieListActivityPresenter implements MovieListActivityViewHolder.Callbacks {
    private final MovieListActivityMVP mActivity;
    private final MovieListActivityViewHolder mViewHolder;
    private final MovieApiHelper mMovieApiHelper;

    private Call<MoviesResponse> mMoviesRequest;

    public MovieListActivityPresenter(MovieListActivityMVP activity,
                                      MovieListActivityViewHolder viewHolder,
                                      MovieApiHelper movieApiHelper) {
        mActivity = activity;
        mViewHolder = viewHolder;
        mMovieApiHelper = movieApiHelper;
    }

    public void onCreate(Bundle savedInstanceState) {
        mViewHolder.onCreate(this);
    }

    public void onResume() {
        makeMovieListRequest();
    }

    public void onPause() {
        cancelMovieRequest();
    }

    private void makeMovieListRequest() {
        mMoviesRequest = mMovieApiHelper.getNowPlayingService().getMovies();
        mMoviesRequest.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                updateListWithMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                mActivity.runOnUiThread(() -> {
                    mViewHolder.showError();
                });
            }
        });
    }

    private void updateListWithMovies(List<Movie> results) {
        boolean isPortraitOrientation = mActivity.getResources().getConfiguration().orientation == 1;
        mActivity.runOnUiThread(() -> {
            mViewHolder.setData(new ArrayList<>(Observable.from(results).map(result -> {
                boolean showBackdropImage =
                        (!isPortraitOrientation || result.getAverageRating() > 5f);
                String imagePath =
                        showBackdropImage ? result.getBackdropPath() : result.getPosterPath();
                return new MovieListAdapter.IMovieListItem() {
                    @Override
                    public Movie getApiMovieItem() {
                        return result;
                    }

                    @Override
                    public String getImagePath() {
                        return MovieApiHelper.IMAGE_BASE_URL + imagePath;
                    }

                    @Override
                    public boolean shouldShowTitleAndDescription() {
                        return result.getAverageRating() <= 5f;
                    }
                };
            }).toList().toBlocking().single()));
        });
    }

    private void cancelMovieRequest() {
        mMoviesRequest.cancel();
    }

    @Override
    public void onRefresh() {
        makeMovieListRequest();
    }

    @Override
    public void onMovieItemClicked(MovieListAdapter.IMovieListItem movieListItem) {
        Intent intent = new Intent(mActivity, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EX_MOVIE_DATA, movieListItem.getApiMovieItem());
        mActivity.startActivity(intent);
    }
}
