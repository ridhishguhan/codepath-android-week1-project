package com.yahoo.codepath.flicks.views;

import com.squareup.picasso.Picasso;
import com.yahoo.codepath.flicks.R;
import com.yahoo.codepath.flicks.adapter.MovieListAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieListItemView extends LinearLayout {
    @BindView(R.id.poster)
    protected ImageView mImageView;

    @BindView(R.id.details)
    protected View mDetails;

    @BindView(R.id.title)
    protected TextView mTitle;

    @BindView(R.id.description)
    protected TextView mDescription;

    public MovieListItemView(Context context) {
        super(context);
    }

    public MovieListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //ButterKnife.bind(this);
        mImageView = (ImageView) findViewById(R.id.poster);
        mDescription = (TextView) findViewById(R.id.description);
        mTitle = (TextView) findViewById(R.id.title);
        mDetails = findViewById(R.id.details);
    }

    public void setData(MovieListAdapter.IMovieListItem item) {
        mTitle.setText(item.getApiMovieItem().getTitle());
        mDescription.setText(item.getApiMovieItem().getOverview());
        Picasso.with(getContext())
               .load(item.getImagePath())
               .transform(new RoundedCornersTransformation(10, 10))
               .into(mImageView);

        mDetails.setVisibility(item.shouldShowTitleAndDescription() ?
                                      View.VISIBLE :
                                      View.GONE);

    }
}
