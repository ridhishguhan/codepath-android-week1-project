package com.yahoo.codepath.flicks;

import com.yahoo.codepath.flicks.adapter.MovieListAdapter;
import com.yahoo.codepath.flicks.api.Movie;
import com.yahoo.codepath.flicks.views.MovieListItemView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import android.view.LayoutInflater;
import android.view.View;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MovieListItemTest extends BaseUnitTest {

    @Mock
    Movie mMovie;

    @Mock
    MovieListAdapter.IMovieListItem mMovieItemData;

    MovieListItemView mMovieListItemView;

    @Before
    public void setup() {
        when(mMovie.getTitle()).thenReturn("Batman vs Superman");
        when(mMovie.getOverview()).thenReturn("It's a really bad movie");
        when(mMovieItemData.getApiMovieItem()).thenReturn(mMovie);
    }

    @Test
    public void testTitleAndDescriptionHiddenWhenAsked() {
        mMovieListItemView = (MovieListItemView) LayoutInflater.from(RuntimeEnvironment.application)
                                                               .inflate(R.layout.movie_list_item,
                                                                        null);

        when(mMovieItemData.shouldShowTitleAndDescription()).thenReturn(false);
        mMovieListItemView.setData(mMovieItemData);
        assertEquals(mMovieListItemView.findViewById(R.id.details).getVisibility(), View.GONE);
    }
}
