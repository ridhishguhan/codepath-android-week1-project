package com.yahoo.codepath.flicks.presenter;

import com.yahoo.codepath.flicks.R;
import com.yahoo.codepath.flicks.adapter.MovieListAdapter;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivityViewHolder {
    private final Activity mActivity;
    private MovieListAdapter mMovieListAdapter;

    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.movie_list)
    protected ListView mMovieList;

    public MovieListActivityViewHolder(Activity activity) {
        mActivity = activity;
    }

    public void onCreate(Callbacks callbacks) {
        mActivity.setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this, mActivity);

        mMovieListAdapter = new MovieListAdapter(movie -> callbacks.onMovieItemClicked(movie));
        mMovieList.setAdapter(mMovieListAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(() -> callbacks.onRefresh());
    }

    public void setData(List<MovieListAdapter.IMovieListItem> movies) {
        mMovieListAdapter.setMovieListItems(movies);
    }

    public void showError() {
        Toast.makeText(mActivity,
                       "Oops something went wrong..",
                       Toast.LENGTH_SHORT).show();
    }

    public interface Callbacks {
        void onRefresh();
        void onMovieItemClicked(MovieListAdapter.IMovieListItem movieListItem);
    }
}
