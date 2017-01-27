package com.yahoo.codepath.flicks.adapter;

import com.yahoo.codepath.flicks.views.MovieListItemView;
import com.yahoo.codepath.flicks.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

public class MovieListAdapter2 extends BaseAdapter {
    private final MovieListAdapter.MovieClickListener mMovieClickListener;
    private List<MovieListAdapter.IMovieListItem> mMovieListItems = Collections.emptyList();

    public MovieListAdapter2(MovieListAdapter.MovieClickListener movieClickListener) {
        mMovieClickListener = movieClickListener;
    }

    public void setMovieListItems(List<MovieListAdapter.IMovieListItem> movieListItems) {
        mMovieListItems = movieListItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMovieListItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mMovieListItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MovieListItemView movieListItemView = (MovieListItemView) view;
        if (movieListItemView == null) {
            movieListItemView = (MovieListItemView) LayoutInflater.from(viewGroup.getContext())
                                 .inflate(R.layout.movie_list_item, viewGroup, false);
        }

        MovieListAdapter.IMovieListItem movieItem = (MovieListAdapter.IMovieListItem) getItem(position);
        movieListItemView.setData(movieItem);

        movieListItemView.setOnClickListener(v -> mMovieClickListener.onMovieClicked(movieItem));
        return movieListItemView;
    }
}
