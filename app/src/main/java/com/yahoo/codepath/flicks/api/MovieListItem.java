package com.yahoo.codepath.flicks.api;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieListItem implements Parcelable {
    @SerializedName("id")
    private long mId;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @SerializedName("vote_average")
    private float mAverageRating;

    protected MovieListItem(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mOverview = in.readString();
        mBackdropPath = in.readString();
        mPosterPath = in.readString();
        mAverageRating = in.readFloat();
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public float getAverageRating() {
        return mAverageRating;
    }

    public long getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeString(mBackdropPath);
        parcel.writeString(mPosterPath);
        parcel.writeFloat(mAverageRating);
    }

    public static final Creator<MovieListItem> CREATOR = new Creator<MovieListItem>() {
        @Override
        public MovieListItem createFromParcel(Parcel in) {
            return new MovieListItem(in);
        }

        @Override
        public MovieListItem[] newArray(int size) {
            return new MovieListItem[size];
        }
    };
}
