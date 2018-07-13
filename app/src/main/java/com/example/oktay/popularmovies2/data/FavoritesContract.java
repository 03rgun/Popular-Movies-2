package com.example.oktay.popularmovies2.data;

import android.provider.BaseColumns;

public class FavoritesContract {
    public static final class FavoritesAdd implements BaseColumns{

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_NAME = "movieName";
    }
}
