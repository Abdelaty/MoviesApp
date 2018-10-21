package com.example.abdel.moviesappstage1;

public class MoviesModel {
    private String mImageUrl;
    private String mMovieName;

    public MoviesModel(String mImageUrl, String mMovieName) {
        this.mImageUrl = mImageUrl;
        this.mMovieName = mMovieName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmMovieName() {
        return mMovieName;
    }
}
