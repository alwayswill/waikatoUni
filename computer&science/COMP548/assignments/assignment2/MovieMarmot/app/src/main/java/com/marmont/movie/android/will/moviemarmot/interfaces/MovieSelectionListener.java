package com.marmont.movie.android.will.moviemarmot.interfaces;


import com.marmont.movie.android.will.moviemarmot.model.Movie;

/**
 * the listener for movie selection
 */
public interface MovieSelectionListener {
    /*
     * the action when movie is selected
     */
    void onMovieSelected(Movie movie);
    /*
    * the method to clear all data in detail fragment.
    * */
    void clearDetailFragment();
}
