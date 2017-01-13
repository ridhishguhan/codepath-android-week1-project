package com.yahoo.codepath.flicks;

import com.squareup.picasso.Picasso;
import com.yahoo.codepath.flicks.api.MovieApiHelper;
import com.yahoo.codepath.flicks.api.MovieListItem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String EX_MOVIE_DATA = "ex_movie_data";

    @BindView(R.id.backdrop)
    protected ImageView mPoster;

    @BindView(R.id.title)
    protected TextView mTitle;

    @BindView(R.id.rating)
    protected RatingBar mRatingBar;

    @BindView(R.id.overview)
    protected TextView mOverview;

    @BindView(R.id.release_date)
    protected TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        MovieListItem movie = getIntent().getParcelableExtra(EX_MOVIE_DATA);
        Picasso.with(this).load(MovieApiHelper.IMAGE_BASE_URL + movie.getBackdropPath()).into(mPoster);
        mTitle.setText(movie.getTitle());
        mOverview.setText(movie.getOverview());
        mRatingBar.setRating(movie.getAverageRating() / 2);
    }
}
