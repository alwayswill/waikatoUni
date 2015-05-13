package com.android.will.wnews.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.will.wnews.R;
import com.android.will.wnews.activities.MainActivity;
import com.android.will.wnews.adapters.NewsListAdapter;
import com.android.will.wnews.interfaces.NewsSelectionListener;
import com.android.will.wnews.model.News;


import java.util.ArrayList;

public class NewsListFragment extends ListFragment {
	private static final String TAG = "NewsListFragment";
	private static final String KEY_SELECTED_NEWS_ID = "sSelectedMovieId";
	private static final int NO_SELECTION = -1;

	private ArrayList<News> mNews = new ArrayList<>();
	private NewsSelectionListener mNewsSelectionListener;
	private NewsListAdapter mNewsListAdapter;
	private int mSelectedNewsId = 0;

	private int selectedItemPosition = NO_SELECTION;


	public NewsListFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			mNewsSelectionListener = (NewsSelectionListener) activity;
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
			mSelectedNewsId = savedInstanceState.getInt(KEY_SELECTED_NEWS_ID);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated()");
		super.onActivityCreated(savedInstanceState);

		mNewsListAdapter = new NewsListAdapter(getActivity(), R.layout.news_list_item, R.id.news_title, mNews, ((MainActivity) getActivity()).getImageLoader());

		getListView().setAdapter(mNewsListAdapter);

	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();

		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		setIsLoading(false);

		if (mNewsListAdapter.getCount() > 0 && selectedItemPosition != NO_SELECTION) {
			getListView().setItemChecked(selectedItemPosition, true);
			getListView().setSelection(selectedItemPosition);
			getListView().smoothScrollToPositionFromTop(selectedItemPosition, 200, 0);
			mNewsSelectionListener.onNewsSelected(mNewsListAdapter.getItem(selectedItemPosition));
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

		mNewsSelectionListener.onNewsSelected(mNewsListAdapter.getItem(selectedItemPosition));
	}

	/**
	 * if selection of options in dropdown list is changed, there is no item to be selected because the list is recreated
	 */
	public void update(ArrayList<News> retrieved_news, boolean filter_changed) {
		Log.d(TAG, "update() : changed = " + filter_changed);

		setIsLoading(false);

		mNews.clear();
		mNews.addAll(retrieved_news);
		mNewsListAdapter.notifyDataSetChanged();
        /*
         * when device rotated during requesting with a selected item, It might choose wrong item or get a null pointer,
         * because the selected item has changed. If we still try to set selection to original position, then we may get
         * a null pointer because the item in that position may not exist.
         *
         * we use checking titles of two items to ensure the item we restored is the right one otherwise refresh the list and detail fragment.
         */

		if (filter_changed == false && selectedItemPosition != NO_SELECTION){
			try {
				News seletedMovie = mNewsListAdapter.getItem(selectedItemPosition);
				if (seletedMovie == null || !mNewsListAdapter.getItem(selectedItemPosition).id.equals(mSelectedNewsId)){
					filter_changed= true;
//					mNewsSelectionListener.clearDetailFragment();
				}
			}catch (IndexOutOfBoundsException e){
				filter_changed= true;
//				mNewsSelectionListener.clearDetailFragment();
			}
		}


		if (filter_changed) {
			getListView().setItemChecked(selectedItemPosition, false);
			selectedItemPosition = NO_SELECTION;
			getListView().setSelection(0);
		} else if (mNewsListAdapter.getCount() > 0 && selectedItemPosition != NO_SELECTION) {
			getListView().setItemChecked(selectedItemPosition, true);
			getListView().setSelection(selectedItemPosition);
			getListView().smoothScrollToPositionFromTop(selectedItemPosition, 200, 0);
			mNewsSelectionListener.onNewsSelected(mNewsListAdapter.getItem(selectedItemPosition));
		}
	}

	/*
     * Return the currently selected movie
     */
	public News getSelectedNews() {
		if (mNewsListAdapter.getCount() > 0 && selectedItemPosition != NO_SELECTION) {
			return mNewsListAdapter.getItem(selectedItemPosition);
		} else {
			return null;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("selectedItemPosition", selectedItemPosition);
		if (selectedItemPosition != NO_SELECTION){
			outState.putInt(KEY_SELECTED_NEWS_ID, getSelectedNews().id);
		}else{
			outState.putInt(KEY_SELECTED_NEWS_ID, 0);
		}

		Log.d(TAG, "onSaveInstanceState() : selectedItemPosition = " + selectedItemPosition);
		super.onSaveInstanceState(outState);
	}



}
