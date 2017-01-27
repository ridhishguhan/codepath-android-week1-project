package com.yahoo.codepath.flicks;

import com.yahoo.codepath.flicks.activity.MovieListActivityMVP;
import com.yahoo.codepath.flicks.adapter.MovieListAdapter;
import com.yahoo.codepath.flicks.api.Movie;
import com.yahoo.codepath.flicks.api.MovieApiHelper;
import com.yahoo.codepath.flicks.presenter.MovieListActivityPresenter;
import com.yahoo.codepath.flicks.presenter.MovieListActivityViewHolder;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import android.content.Intent;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieListActivityTest extends BaseUnitTest {
    @Mock
    private MovieListActivityMVP mActivityMVP;

    @Mock
    private MovieApiHelper mMovieApiHelper;

    @Mock
    private MovieListActivityViewHolder mViewHolder;

    @Test
    public void testThatMovieDetailsActivityCalled() {
        MovieListActivityPresenter presenter = new MovieListActivityPresenter(mActivityMVP, mViewHolder, mMovieApiHelper);
        MovieListAdapter.IMovieListItem item = mock(MovieListAdapter.IMovieListItem.class);
        Movie movie = mock(Movie.class);
        when(movie.getTitle()).thenReturn("Rings");
        when(item.getApiMovieItem()).thenReturn(movie);

        presenter.onMovieItemClicked(item);

        verify(mActivityMVP, times(1)).startActivity(argThat(new ArgumentMatcher<Intent>() {
            @Override
            public boolean matches(Intent argument) {
                Movie movie = argument.getParcelableExtra("ex_movie_data");
                return movie.getTitle().equals("Rings");
            }
        }));
    }
}
