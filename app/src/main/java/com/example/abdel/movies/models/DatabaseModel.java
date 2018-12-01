package com.example.abdel.movies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class DatabaseModel {
    @NonNull
    @PrimaryKey
    private String movieDbId;
    private String imageUrl;
    private String movieName;

    @NonNull
    public String getMovieDbId() {
        return movieDbId;
    }

    public void setMovieDbId(@NonNull String movieDbId) {
        this.movieDbId = movieDbId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }




 /*   @ColumnInfo(name = "movieName")
    private String movieName;
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
*/
}
