package com.marmont.movie.android.will.moviemarmot.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {


    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_details, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void clear() {
        Log.d(getClass().getName(), "clear()");

        TextView title = (TextView) getView().findViewById(R.id.movie_details_title);
        RatingBar ratingbar = (RatingBar) getView().findViewById(R.id.movie_details_rating);
        TextView mpaaRating = (TextView) getView().findViewById(R.id.movie_details_mpaa_rating);
        TextView summary = (TextView) getView().findViewById(R.id.movie_details_summary);
        TextView dataSource = (TextView) getView().findViewById(R.id.movie_details_data_source);

        title.setText("");
        ratingbar.setRating(0);
        ratingbar.setVisibility(View.INVISIBLE);
        mpaaRating.setText("");
        summary.setText("");
        dataSource.setVisibility(View.INVISIBLE);
    }


    public void update(Movie movie) {
        Log.d(getClass().getName(), "update()");



        TextView title = (TextView) getView().findViewById(R.id.movie_details_title);
        RatingBar ratingbar = (RatingBar) getView().findViewById(R.id.movie_details_rating);
        TextView mpaaRating = (TextView) getView().findViewById(R.id.movie_details_mpaa_rating);
        TextView summary = (TextView) getView().findViewById(R.id.movie_details_summary);
        TextView dataSource = (TextView) getView().findViewById(R.id.movie_details_data_source);

        title.setText(movie.getTitle());
        ratingbar.setRating(movie.getRating());
        ratingbar.setVisibility(View.VISIBLE);
        mpaaRating.setText(movie.getMpaaRating());
        summary.setText(movie.getSummary());
        dataSource.setVisibility(View.VISIBLE);
    }

}
