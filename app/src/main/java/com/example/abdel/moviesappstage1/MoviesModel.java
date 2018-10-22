package com.example.abdel.moviesappstage1;

public class MoviesModel {
    private String mImageUrl;
    private String mMovieName;
    private String mMovieReleaseDate;
    private String mMovieOverview;
    private String mMovieRate;

    public MoviesModel(String mImageUrl, String mMovieName, String mMovieReleaseDate, String mMovieOverview, String mMovieRate) {
        this.mImageUrl = mImageUrl;
        this.mMovieName = mMovieName;
        this.mMovieReleaseDate = mMovieReleaseDate;
        this.mMovieOverview = mMovieOverview;
        this.mMovieRate = mMovieRate;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public void setmMovieName(String mMovieName) {
        this.mMovieName = mMovieName;
    }

    public String getmMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public void setmMovieReleaseDate(String mMovieReleaseDate) {
        this.mMovieReleaseDate = mMovieReleaseDate;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public void setmMovieOverview(String mMovieOverview) {
        this.mMovieOverview = mMovieOverview;
    }

    public String getmMovieRate() {
        return mMovieRate;
    }

    public void setmMovieRate(String mMovieRate) {
        this.mMovieRate = mMovieRate;
    }
}
