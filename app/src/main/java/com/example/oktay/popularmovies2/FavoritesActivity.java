package com.example.oktay.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.oktay.popularmovies2.data.FavoritesContract;

public class FavoritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FavoritesActivity.class.getSimpleName();
    private static final int FAVORITE_LOADER_ID = 0;
    private FavoritesCursorAdapter mAdapter;
    RecyclerView mRecyclerView;
    Context context = this;

    public static final String[] FAVORITE_DETAIL = {
            FavoritesContract.FavoritesAdd.COLUMN_MOVIE_ID,
//            FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RATE,
//            FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RELEASE,
//            FavoritesContract.FavoritesAdd.COLUMN_MOVIE_POSTER,
//            FavoritesContract.FavoritesAdd.COLUMN_MOVIE_OVERVIEW,
//            FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME,
    };

    public static final int INDEX_MOVIE_ID = 0;
//    public static final int INDEX_MOVIE_NAME = 1;
//    public static final int INDEX_MOVIE_OVERVIEW = 2;
//    public static final int INDEX_MOVIE_POSTER = 3;
//    public static final int INDEX_MOVIE_RELEASE = 4;
//    public static final int INDEX_WEATHER_WIND_SPEED = 5;
//    public static final int INDEX_WEATHER_DEGREES = 6;
//    public static final int INDEX_WEATHER_CONDITION_ID = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_favorites);

        int mNoOfColumns = MainActivity.calculateNoOfColumns(getApplicationContext());

        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavoritesCursorAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.addOnItemTouchListener(
//                new FavoriteItemClickListener(context, mRecyclerView, new FavoriteItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Class destinationClass = DetailActivity.class;
//
//                        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
//                        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, position);
//                        intentToStartDetailActivity.putExtra("title", mAdapter.name);
//                        intentToStartDetailActivity.putExtra("poster", mAdapter.poster);
//                        intentToStartDetailActivity.putExtra("rate", mAdapter.rate);
//                        intentToStartDetailActivity.putExtra("release", mAdapter.release);
//                        intentToStartDetailActivity.putExtra("overview", mAdapter.overview);
//                        intentToStartDetailActivity.putExtra("id", mAdapter.movieId);
//
//                        startActivity(intentToStartDetailActivity);
//
//                    }
//                }));


        getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mFavData = null;

            @Override
            protected void onStartLoading() {
                if (mFavData != null){
                    deliverResult(mFavData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getContentResolver().query(FavoritesContract.FavoritesAdd.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                } catch (Exception e){
                    Log.e(TAG, "failed to load fav data asyncly");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mFavData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
