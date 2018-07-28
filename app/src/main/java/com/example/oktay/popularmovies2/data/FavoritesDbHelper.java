package com.example.oktay.popularmovies2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.oktay.popularmovies2.MainActivity;
import com.example.oktay.popularmovies2.data.FavoritesContract.*;
import com.example.oktay.popularmovies2.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class FavoritesDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 3; //increment this when you update the database

    //constructor
    public FavoritesDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " +
                FavoritesAdd.TABLE_NAME + " (" +
                FavoritesAdd._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoritesAdd.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                FavoritesAdd.COLUMN_MOVIE_NAME + " TEXT NOT NULL," +
                FavoritesAdd.COLUMN_MOVIE_POSTER + " TEXT NOT NULL," +
                FavoritesAdd.COLUMN_MOVIE_RATE + " TEXT NOT NULL," +
                FavoritesAdd.COLUMN_MOVIE_RELEASE + " TEXT NOT NULL," +
                FavoritesAdd.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL" +
                "); ";
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);

    }

    // for now it  simply drop the table and create a new one. This means if you change the
    // DATABASE_VERSION the table will be dropped instead of alter the table.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoritesAdd.TABLE_NAME);
        onCreate(db);
    }




    public List<Movie> getFavorites(){
        String[] col = {
                FavoritesContract.FavoritesAdd._ID,
                FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME,
                FavoritesContract.FavoritesAdd.COLUMN_MOVIE_POSTER,
                FavoritesContract.FavoritesAdd.COLUMN_MOVIE_OVERVIEW,
                FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RELEASE,
                FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RATE,
                //FavoritesContract.FavoritesAdd.COLUMN_MOVIE_ID
        };
        List<Movie> favorites = new ArrayList<>();

        SQLiteDatabase mDb = this.getReadableDatabase();
        Cursor cursor = mDb.query(
                FavoritesContract.FavoritesAdd.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_ID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_POSTER)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_OVERVIEW)));
                movie.setRate(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RATE)));
                movie.setRelease(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RELEASE)));

                favorites.add(movie);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        mDb.close();
        return favorites;
    }
}
