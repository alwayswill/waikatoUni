package com.marmont.movie.android.will.moviemarmot.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.activities.MainActivity;
import com.marmont.movie.android.will.moviemarmot.adapters.MovieListAdapter;
import com.marmont.movie.android.will.moviemarmot.interfaces.MovieSelectionListener;
import com.marmont.movie.android.will.moviemarmot.model.Movie;

import java.util.ArrayList;

/**
 * fragment for show the list of movies
 */
public class MovieListFragment extends ListFragment {
    private static final String TAG = "MovieListFragment";
    private static final int NO_SELECTION = -1;

    private ArrayList<Movie> mMovies = new ArrayList<Movie>();
    private MovieSelectionListener mMovieSelectionListener;
    private MovieListAdapter mMovieListAdapter;

    private int selectedItemPosition = NO_SELECTION;


    public MovieListFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);
        try {
            mMovieSelectionListener = (MovieSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MovieSelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d(TAG, "onActivityCreated() : getting selectedItemPosition from savedInstanceState");
            selectedItemPosition = savedInstanceState.getInt("selectedItemPosition");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        mMovieListAdapter = new MovieListAdapter(getActivity(), R.layout.movie_list_item, R.id.movie_title, mMovies, ((MainActivity) getActivity()).getImageLoader());

        getListView().setAdapter(mMovieListAdapter);

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        setIsLoading(false);

        if (mMovieListAdapter.getCount() > 0 && selectedItemPosition != NO_SELECTION) {
            getListView().setItemChecked(selectedItemPosition, true);
            getListView().setSelection(selectedItemPosition);
            getListView().smoothScrollToPositionFromTop(selectedItemPosition, 200, 0);
            mMovieSelectionListener.onMovieSelected(mMovieListAdapter.getItem(selectedItemPosition));
        }
    }

    /**
     * show loading status
     */
    public void setIsLoading(boolean is_loading) {
        setListShown(!is_loading);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "onListItemClick() : " + position);
        selectedItemPosition = position;

        getListView().setItemChecked(selectedItemPosition, true);

        mMovieSelectionListener.onMovieSelected(mMovieListAdapter.getItem(selectedItemPosition));
    }

    /**
     * if selection of options in dropdown list is changed, there is no item to be selected because the list is recreated
     */
    public void update(ArrayList<Movie> retrieved_movies, boolean filter_changed) {
        Log.d(TAG, "update() : changed = " + filter_changed);

        setIsLoading(false);

        mMovies.clear();
        mMovies.addAll(retrieved_movies);
        mMovieListAdapter.notifyDataSetChanged();

        if (filter_changed) {
            getListView().setItemChecked(selectedItemPosition, false);
            selectedItemPosition = NO_SELECTION;
            getListView().setSelection(0);
        } else if (mMovieListAdapter.getCount() > 0 && selectedItemPosition != NO_SELECTION) {
            getListView().setItemChecked(selectedItemPosition, true);
            getListView().setSelection(selectedItemPosition);
            getListView().smoothScrollToPositionFromTop(selectedItemPosition, 200, 0);
            mMovieSelectionListener.onMovieSelected(mMovieListAdapter.getItem(selectedItemPosition));
        }
    }

    /*
     * Return the currently selected movie
     */
    public Movie getSelectedMovie() {
        if (mMovieListAdapter.getCount() > 0 && selectedItemPosition != NO_SELECTION) {
            return mMovieListAdapter.getItem(selectedItemPosition);
        } else {
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedItemPosition", selectedItemPosition);
        Log.d(TAG, "onSaveInstanceState() : selectedItemPosition = " + selectedItemPosition);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

}
