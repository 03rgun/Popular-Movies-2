package com.example.oktay.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oktay.popularmovies2.model.Trailer;
import com.example.oktay.popularmovies2.utilities.NetworkUtils;
import com.example.oktay.popularmovies2.utilities.TheMovieDbJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.oktay.popularmovies2.TrailerAdapter.mTrailerURL;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;
    private Trailer[] jsonTrailerData;
    private int id = 0;

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
    @BindView(R.id.trailer_error_message)
    TextView mTrailerErrorMessage;

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
        id = getIntent().getIntExtra("id",0);


        mMovieTitleDisplay.setText(title);
        mMoviePlotSynopsisDisplay.setText(overview);
        mMovieRateDisplay.setText(rate + " / 10");
        mMovieReleaseDisplay.setText(release);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(mMoviePosterDisplay);

        loadTrailerData();

    }


    private void loadTrailerData() {
        String trailerId = String.valueOf(id);
        new FetchTrailerTask().execute(trailerId);
    }


    @Override
    public void onClick(int adapterPosition) {
        Uri openTrailerVideo = Uri.parse(mTrailerURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, openTrailerVideo);
        //check if user does have the required apps
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
                mTrailerErrorMessage.setVisibility(View.VISIBLE);
            }
        }

    }
}
