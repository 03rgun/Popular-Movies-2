package com.example.oktay.popularmovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oktay.popularmovies2.model.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private Trailer[] mTrailerData;
    private final TrailerAdapterOnClickHandler mClickHandler;
    public static TextView mTrailerListTextView = null;
    public static String mTrailerURL;

    public TrailerAdapter(Trailer[] trailer, TrailerAdapterOnClickHandler clickHandler) {
        mTrailerData = trailer;
        mClickHandler = clickHandler;
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            mTrailerListTextView = (TextView) itemView.findViewById(R.id.tv_trailer_names);
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
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailers_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        //inflate list item xml into a view
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        //set the Trailer for list item's position
        String TrailerToBind = mTrailerData[position].getName();
        String TrailerToWatch = mTrailerData[position].getKey();
        mTrailerListTextView.setText(TrailerToBind);
        mTrailerURL = TrailerToWatch;
    }

    @Override
    public int getItemCount() {
        if (null == mTrailerData) {
            return 0;
        }
        return mTrailerData.length;
    }
}
