package com.marmont.movie.android.will.moviemarmot.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.widget.ListView;


import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.activities.MainActivity;
import com.marmont.movie.android.will.moviemarmot.adapters.MovieListAdapter;
import com.marmont.movie.android.will.moviemarmot.interfaces.MovieSelectionListener;
import com.marmont.movie.android.will.moviemarmot.model.Movie;

import java.util.ArrayList;

public class MovieListFragment extends ListFragment {
	private static final String			TAG						= "MovieListFragment";
	private static final int			NO_SELECTION			= -1;

	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private MovieSelectionListener movieSelectionListener;
	private MovieListAdapter movie_list_adapter;

	private int							selected_item_position	= NO_SELECTION;


	public MovieListFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			movieSelectionListener = (MovieSelectionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement MovieSelectionListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			Log.d(TAG, "onActivityCreated() : getting selected_item_position from savedInstanceState");
			selected_item_position = savedInstanceState.getInt("selected_item_position");
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated()");
		super.onActivityCreated(savedInstanceState);

		movie_list_adapter = new MovieListAdapter(getActivity(), R.layout.movie_list_item, R.id.movie_title, movies, ((MainActivity) getActivity()).getImageLoader());

		getListView().setAdapter(movie_list_adapter);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onResume()
	 * 
	 * The fragment is visible and 'alive'. Now we can do UI operations.
	 */
	public void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();

		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		setIsLoading(false);

		if (movie_list_adapter.getCount() > 0 && selected_item_position != NO_SELECTION) {
			getListView().setItemChecked(selected_item_position, true);
			getListView().setSelection(selected_item_position);
			getListView().smoothScrollToPositionFromTop(selected_item_position, 200, 0);
			movieSelectionListener.onMovieSelected(movie_list_adapter.getItem(selected_item_position));
		}
	}

	public void setIsLoading(boolean is_loading) {
		setListShown(!is_loading);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(TAG, "onListItemClick() : " + position);
		selected_item_position = position;

		getListView().setItemChecked(selected_item_position, true);

		movieSelectionListener.onMovieSelected(movie_list_adapter.getItem(selected_item_position));
	}

	public void update(ArrayList<Movie> retrieved_movies, boolean filter_changed) {
		Log.d(TAG, "update() : changed = " + filter_changed);

		setIsLoading(false);

		movies.clear();
		movies.addAll(retrieved_movies);
		movie_list_adapter.notifyDataSetChanged();

		if (filter_changed) {
			getListView().setItemChecked(selected_item_position, false);
			selected_item_position = NO_SELECTION;
			getListView().setSelection(0);
		} else if (movie_list_adapter.getCount() > 0 && selected_item_position != NO_SELECTION) {
			getListView().setItemChecked(selected_item_position, true);
			getListView().setSelection(selected_item_position);
			getListView().smoothScrollToPositionFromTop(selected_item_position, 200, 0);
			movieSelectionListener.onMovieSelected(movie_list_adapter.getItem(selected_item_position));
		}
	}

	/*
	 * Return the currently selected movie
	 */
	public Movie getSelectedMovie() {
		if (movie_list_adapter.getCount() > 0 && selected_item_position != NO_SELECTION) {
			return movie_list_adapter.getItem(selected_item_position);
		} else {
			return null;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("selected_item_position", selected_item_position);
		Log.d(TAG, "onSaveInstanceState() : selected_item_position = " + selected_item_position);
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
