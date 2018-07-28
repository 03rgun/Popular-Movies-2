package com.example.oktay.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oktay.popularmovies2.data.FavoritesContract;
import com.squareup.picasso.Picasso;



public class FavoritesCursorAdapter extends RecyclerView.Adapter<FavoritesCursorAdapter.FavoriteViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    public String name = "";
    public String poster = "";
    public String overview = "";
    public String release = "";
    public String rate = "";
    public int id;
    public int movieId;

    public FavoritesCursorAdapter(Context mContext){
        this.mContext = mContext;
    }

    public interface ForecastAdapterOnClickHandler {
        void onClick(int position);
    }


    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View  view = LayoutInflater.from(mContext).inflate(R.layout.movies_list_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position){
        int idIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd._ID);
        int movieIdIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_ID);
        int posterIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_POSTER);
        int nameIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME);
        int overviewIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_OVERVIEW);
        int releaseIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RELEASE);
        int rateIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RATE);

        mCursor.moveToPosition(position);

        id = mCursor.getInt(idIndex);
        movieId = mCursor.getInt(movieIdIndex);
        poster = mCursor.getString(posterIndex);
        name = mCursor.getString(nameIndex);
        overview = mCursor.getString(overviewIndex);
        release = mCursor.getString(releaseIndex);
        rate = mCursor.getString(rateIndex);

        //SETTERS
        holder.itemView.setTag(id);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(holder.mMovieListImageView);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    //update the cursor when a new data is added
    public Cursor swapCursor(Cursor c){
        if (mCursor == c){
            return null;
        }

        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null){
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mMovieListImageView;
//        TextView mMovieTitleDisplay;
//        TextView mMovieRateDisplay;
//        TextView mMovieReleaseDisplay;
//        TextView mMoviePlotSynopsisDisplay;
//        Button mFavorites;

        public FavoriteViewHolder(View itemView){
            super(itemView);

            mMovieListImageView = (ImageView) itemView.findViewById(R.id.iv_movie_posters);
//            mMovieTitleDisplay = (TextView) itemView.findViewById(R.id.tv_detail_title);
//            mMovieRateDisplay = (TextView) itemView.findViewById(R.id.tv_detail_rate);
//            mMovieReleaseDisplay = (TextView) itemView.findViewById(R.id.tv_detail_release_date);
//            mMoviePlotSynopsisDisplay = (TextView) itemView.findViewById(R.id.tv_plot_synopsis);
//            mFavorites = (Button) itemView.findViewById(R.id.add_to_favorites);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            Class destinationClass = DetailActivity.class;

            //int id = mCursor.getInt(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd._ID));
            String name = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_NAME));
            int movieId = mCursor.getInt(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_ID));
            String overview = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_OVERVIEW));
            String rate = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RATE));
            String release = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_RELEASE));
            String poster = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoritesAdd.COLUMN_MOVIE_POSTER));


            Intent intentToStartDetailActivity = new Intent(mContext, destinationClass);
            intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, adapterPosition);
            intentToStartDetailActivity.putExtra("title", name);
            intentToStartDetailActivity.putExtra("poster", poster);
            intentToStartDetailActivity.putExtra("rate", rate);
            intentToStartDetailActivity.putExtra("release", release);
            intentToStartDetailActivity.putExtra("overview", overview);
            intentToStartDetailActivity.putExtra("id", movieId);

            mContext.startActivity(intentToStartDetailActivity);

        }
    }

}
