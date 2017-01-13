package com.yahoo.codepath.flicks;

import com.yahoo.codepath.flicks.api.MovieApiHelper;
import com.yahoo.codepath.flicks.api.MovieListItem;
import com.yahoo.codepath.flicks.api.MoviesResponse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

public class MovieListActivity extends AppCompatActivity {

    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.movie_list)
    protected ListView mMovieList;
    private Call<MoviesResponse> mMoviesRequest;
    private MovieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        mMovieListAdapter = new MovieListAdapter(movieListItemData -> {
            Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.EX_MOVIE_DATA, movieListItemData.getApiMovieItem());
            startActivity(intent);
        });

        mMovieList.setAdapter(mMovieListAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(() -> makeMovieListRequest());
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeMovieListRequest();
    }

    private void makeMovieListRequest() {
        mMoviesRequest = MovieApiHelper.getInstance().getNowPlayingService().getMovies();
        mMoviesRequest.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                updateListWithMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(MovieListActivity.this,
                                   "Oops something went wrong..",
                                   Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void updateListWithMovies(List<MovieListItem> results) {
        boolean isPortraitOrientation = getResources().getConfiguration().orientation == 1;
        runOnUiThread(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
            mMovieListAdapter.setMovieListItems(new ArrayList<>(Observable.from(results).map(result -> {
                boolean showBackdropImage =
                        (!isPortraitOrientation || result.getAverageRating() > 5f);
                String imagePath =
                        showBackdropImage ? result.getBackdropPath() : result.getPosterPath();
                return new MovieListAdapter.IMovieListItem() {
                    @Override
                    public MovieListItem getApiMovieItem() {
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

    @Override
    protected void onPause() {
        super.onPause();
        cancelMovieRequest();
    }

    private void cancelMovieRequest() {
        mMoviesRequest.cancel();
    }
}
