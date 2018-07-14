package com.example.oktay.popularmovies2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        //mFavoritesAdapter.swapCursor(getAllFavorites());

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
}
