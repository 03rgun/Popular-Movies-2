package com.example.oktay.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.oktay.popularmovies2.data.FavoritesContract.*;


public class FavoritesDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1; //increment this when you update the database

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
                FavoritesAdd.COLUMN_MOVIE_NAME+ " TEXT NOT NULL" +
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
}
