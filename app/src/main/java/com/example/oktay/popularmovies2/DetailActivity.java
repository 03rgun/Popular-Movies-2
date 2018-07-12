package com.example.oktay.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oktay.popularmovies2.model.Trailer;
import com.example.oktay.popularmovies2.utilities.NetworkUtils;
import com.example.oktay.popularmovies2.utilities.TheMovieDbJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;
    private Trailer[] jsonTrailerData;

    @BindView(R.id.iv_detail_movie_poster)
    ImageView mMoviePosterDisplay;
    @BindView(R.id.tv_detail_title)
    TextView mMovieTitleDisplay;
    @BindView(R.id.tv_detail_rate)
    TextView mMovieRateDisplay;
    @BindView(R.id.tv_detail_release_date)
    TextView mMovieReleaseDisplay;
    @BindView(R.id.tv_plot_synopsis)
    TextView mMoviePlotSynopsisDisplay;
    @BindView(R.id.tv_movie_id)
    TextView mMovieIdDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_trailers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //set the layout manager
        mRecyclerView.setLayoutManager(layoutManager);
        //changes in content shouldn't change the layout size
        mRecyclerView.setHasFixedSize(true);

        //set movie adapter for recycler view
        mRecyclerView.setAdapter(mTrailerAdapter);


        ButterKnife.bind(this);

        // https://stackoverflow.com/questions/41791737/how-to-pass-json-image-from-recycler-view-to-another-activity
        String poster = getIntent().getStringExtra("poster");
        String title = getIntent().getStringExtra("title");
        String rate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");


        mMovieIdDisplay.setText("delete this.");
        mMovieTitleDisplay.setText(title);
        mMoviePlotSynopsisDisplay.setText(overview);
        mMovieRateDisplay.setText(rate + " / 10");
        mMovieReleaseDisplay.setText(release);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(mMoviePosterDisplay);

    }

    @Override
    public void onClick(int adapterPosition) {
        Context context = this;
        Intent intentToStartDetailActivity = new Intent(context, MainActivity.class);
        startActivity(intentToStartDetailActivity);
    }


    public class FetchTrailerTask extends AsyncTask<String, Void, Trailer[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Trailer[] doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }
            int id = getIntent().getIntExtra("id",0);

            URL movieRequestUrl = NetworkUtils.buildTrailerUrl(id);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                jsonTrailerData
                        = TheMovieDbJsonUtils.getTrailerInformationsFromJson(DetailActivity.this, jsonMovieResponse);

                return jsonTrailerData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Trailer[] trailerData) {
            if (trailerData != null) {
                mTrailerAdapter = new TrailerAdapter(trailerData,DetailActivity.this);
                mRecyclerView.setAdapter(mTrailerAdapter);
            } else {
                mMovieIdDisplay.setText("An error occured. Can't reach trailers. Please try again. ");
            }
        }

    }

}
