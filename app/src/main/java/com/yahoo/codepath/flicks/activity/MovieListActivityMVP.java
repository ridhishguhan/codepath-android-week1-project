package com.yahoo.codepath.flicks.activity;

import com.yahoo.codepath.flicks.presenter.MovieListActivityPresenter;
import com.yahoo.codepath.flicks.presenter.MovieListActivityViewHolder;
import com.yahoo.codepath.flicks.api.MovieApiHelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieListActivityMVP extends AppCompatActivity {

    private MovieListActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MovieListActivityPresenter(this,
                                                    new MovieListActivityViewHolder(this),
                                                    MovieApiHelper.getInstance());
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }
}
