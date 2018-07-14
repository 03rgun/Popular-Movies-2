package com.example.oktay.popularmovies2;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oktay.popularmovies2.data.FavoritesContract;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    //public static TextView mFavoriteListTextView = null;

    //constructor
    public FavoritesAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.favorites_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        //inflate list item xml into a view
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String favoriteMovieName = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME));
        holder.tv_favoriteMovie.setText(favoriteMovieName);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView tv_favoriteMovie;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            tv_favoriteMovie = (TextView) itemView.findViewById(R.id.tv_favorite_movie_names);
        }
    }


    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }

}