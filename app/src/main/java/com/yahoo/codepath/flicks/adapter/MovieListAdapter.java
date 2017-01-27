package com.yahoo.codepath.flicks.adapter;

import com.squareup.picasso.Picasso;
import com.yahoo.codepath.flicks.R;
import com.yahoo.codepath.flicks.api.Movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieListAdapter extends BaseAdapter {
    private final MovieClickListener mMovieClickListener;
    private List<IMovieListItem> mMovieListItems = Collections.emptyList();

    public MovieListAdapter(MovieClickListener movieClickListener) {
        mMovieClickListener = movieClickListener;
    }

    public void setMovieListItems(List<IMovieListItem> movieListItems) {
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
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                                 .inflate(R.layout.movie_list_item, viewGroup, false);
            view.setTag(new ViewHolder(view));
        }

        IMovieListItem movieItem = (IMovieListItem) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mTitle.setText(movieItem.getApiMovieItem().getTitle());
        holder.mDescription.setText(movieItem.getApiMovieItem().getOverview());
        Picasso.with(viewGroup.getContext())
               .load(movieItem.getImagePath())
               .transform(new RoundedCornersTransformation(10, 10))
               .into(holder.mImageView);

        holder.mDetails.setVisibility(movieItem.shouldShowTitleAndDescription() ?
                                      View.VISIBLE :
                                      View.GONE);

        view.setOnClickListener(v -> mMovieClickListener.onMovieClicked(movieItem));
        return view;
    }

    static final class ViewHolder {
        @BindView(R.id.details)
        View mDetails;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.description)
        TextView mDescription;

        @BindView(R.id.poster)
        ImageView mImageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface IMovieListItem {
        Movie getApiMovieItem();
        String getImagePath();
        boolean shouldShowTitleAndDescription();
    }

    public interface MovieClickListener {
        void onMovieClicked(IMovieListItem movieListItemData);
    }
}
