package com.yahoo.codepath.flicks;


import com.yahoo.codepath.flicks.activity.MovieListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MovieListActivityTest {
    @Rule
    public ActivityTestRule<MovieListActivity> mActivityRule = new ActivityTestRule<>(
            MovieListActivity.class);

    @Test
    public void testThatActivityLaunchesAndPullToRefreshWorks() throws InterruptedException {
        onView(withId(R.id.swipe_refresh_layout)).perform(ViewActions.swipeDown());

        // very bad practice but just for demonstration
        Thread.sleep(4000);

        onView(withId(R.id.movie_list)).perform(ViewActions.swipeUp());
    }
}
