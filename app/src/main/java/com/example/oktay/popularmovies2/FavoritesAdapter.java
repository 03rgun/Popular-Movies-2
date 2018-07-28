package com.example.oktay.popularmovies2;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oktay.popularmovies2.data.FavoritesContract;
import com.squareup.picasso.Picasso;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {
    private Cursor mCursor;
    private Context mContext;
    private final FavoritesAdapterOnClickHandler mClickHandler;

    //constructor
    public FavoritesAdapter(Context context, Cursor cursor, FavoritesAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mCursor = cursor;
        mClickHandler = clickHandler;
    }

    public interface FavoritesAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieListImageView;
        public final TextView mMovieTitle;

        public FavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieListImageView = (ImageView) itemView.findViewById(R.id.iv_movie_posters);
            mMovieTitle = (TextView) itemView.findViewById(R.id.tv_detail_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public FavoritesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        //inflate list item xml into a view
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoritesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapterViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String favoriteMoviePoster = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_POSTER));
        String favoriteMovieTitle = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME));
        String favoriteMovieRelease = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RELEASE));
        String favoriteMovieRate = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RATE));
        String favoriteMovieOverview = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_OVERVIEW));


        Picasso.get()
                .load(favoriteMoviePoster)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(holder.mMovieListImageView);
//        holder.tv_favoriteMovie.setText(favoriteMovieName);
//        holder.mMovieTitle.setText(favoriteMovieTitle);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
//    class FavoritesViewHolder extends RecyclerView.ViewHolder {
//        public final ImageView mMovieListImageView;
//
//        public FavoritesViewHolder(View itemView) {
//            super(itemView);
//            mMovieListImageView; = (ImageView) itemView.findViewById(R.id.tv_favorite_movie_names);
//        }
//    }
//
//
//    public void swapCursor(Cursor newCursor){
//        if (mCursor != null){
//            mCursor.close();
//        }
//        mCursor = newCursor;
//        if (newCursor != null){
//            this.notifyDataSetChanged();
//        }
//    }

}