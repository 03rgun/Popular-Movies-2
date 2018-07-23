package com.example.oktay.popularmovies2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.oktay.popularmovies2.data.FavoritesContract;
import com.example.oktay.popularmovies2.data.FavoritesDbHelper;

public class FavoritesActivity extends AppCompatActivity {
    private FavoritesAdapter mFavoritesAdapter;
    private SQLiteDatabase mDb;
    RecyclerView favoritesRecyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_favorites);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //set the layout manager
        favoritesRecyclerView.setLayoutManager(layoutManager);
        //changes in content shouldn't change the layout size
        favoritesRecyclerView.setHasFixedSize(true);

        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);

        mDb = dbHelper.getReadableDatabase();

        Cursor cursor = getAllFavorites();

        mFavoritesAdapter = new FavoritesAdapter(this, cursor);

        //set favorites adapter for recycler view
        favoritesRecyclerView.setAdapter(mFavoritesAdapter);
    }

    private Cursor getAllFavorites(){
        return mDb.query(
                FavoritesContract.FavoritesAdd.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    // https://stackoverflow.com/questions/28236390/recyclerview-store-restore-state-between-activities
    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = favoritesRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            favoritesRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }



}
