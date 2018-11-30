package com.example.abdel.movies.models;

public class MoviesModel {
    private String imageUrl;
    private String movieReleaseDate;
    private String movieOverview;
    private String movieRate;
    private String movieReview;
    private String movieAuthor;
    private String actorImage;
    private String actorName;
    private String actorCharacter;
    private String trailerUrl;

    private String movieId;

    private String movieName;

    public MoviesModel(String actorName, String actorCharacter, String actorImage) {

        this.actorName = actorName;
        this.actorCharacter = actorCharacter;
        this.actorImage = actorImage;
    }

    public MoviesModel(String imageUrl, String movieName, String movieReleaseDate, String movieOverview, String movieRate, String movieId) {
        this.imageUrl = imageUrl;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.movieOverview = movieOverview;
        this.movieRate = movieRate;
        this.movieId = movieId;
    }

    public MoviesModel(String movieReview, String movieId, String movieAuthor, int id) {
        this.movieReview = movieReview;
        this.movieId = movieId;
        this.movieAuthor = movieAuthor;
    }

    public MoviesModel(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(String movieReview) {
        this.movieReview = movieReview;
    }

    public String getMovieAuthor() {
        return movieAuthor;
    }

    public void setMovieAuthor(String movieAuthor) {
        this.movieAuthor = movieAuthor;
    }

    public String getActorImage() {
        return actorImage;
    }

    public void setActorImage(String actorImage) {
        this.actorImage = actorImage;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorCharacter() {
        return actorCharacter;
    }

    public void setActorCharacter(String actorCharacter) {
        this.actorCharacter = actorCharacter;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }


}
