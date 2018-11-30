package com.example.abdel.movies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.abdel.movies.models.DatabaseModel;

@Database(entities = {DatabaseModel.class}, version = 7, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "movies_db";
    private static final Object LOCK = new Object();
    private static MovieDatabase INSTANCE;

    public static MovieDatabase getAppInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                Log.d("data", "Creating new database instance");
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME)
                                // allow queries on the main thread.
                                // Don't do this on a real app! See PersistenceBasicSample for an example.
                                .allowMainThreadQueries().fallbackToDestructiveMigration()
                                .build();
            }
        }
        Log.d("database instance  ", "Getting database  instance ");
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract DaoAccess daoAccess();
}