package com.example.abdel.movies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.abdel.movies.models.DatabaseModel;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    public void insert(DatabaseModel... databaseModel);

    @Update
    public void update(DatabaseModel... databaseModel);

    @Delete
    public void delete(DatabaseModel databaseModel);

    @Query("SELECT * FROM databaseModel")
    public List<DatabaseModel> getMovies();

    @Query("DELETE FROM databaseModel WHERE movieDbId = :number")
    public int delMovieWithId(String number);

    @Query("SELECT * FROM databaseModel WHERE movieDbId = :number")
    public boolean getMovieWithId(String number);
}