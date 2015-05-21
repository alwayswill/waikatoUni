package org.stevej.android.propertyfinder.fragments;

import java.util.ArrayList;
import java.util.Collections;

import org.stevej.android.propertyfinder.R;
import org.stevej.android.propertyfinder.activities.PropertyFinderActivity;
import org.stevej.android.propertyfinder.adapters.PropertyListAdapter;
import org.stevej.android.propertyfinder.model.Property;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class PropertyListFragment extends ListFragment {
	private static final String TAG = "PropertyListFragment";
	private static final int NO_SELECTION = -1;

	private ArrayList<Property> properties = new ArrayList<Property>();
	private PropertySelectionListener property_selection_listener;
	private PropertyListAdapter property_list_adapter;

	private int selected_item_position = NO_SELECTION;

	public interface PropertySelectionListener {
		public void onPropertySelected(Property property);
	}

	/*
	 * requires empty constructor
	 */
	public PropertyListFragment() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 * 
	 * This fragment has now been added to the activity, which we use as the
	 * listener for property list selections
	 */
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			property_selection_listener = (PropertySelectionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement PropertySelectionListener");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 * 
	 * Set up the data/list view adapter
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);

		property_list_adapter = new PropertyListAdapter(getActivity(), R.layout.property_list_item, R.id.property_title, properties,
				((PropertyFinderActivity) getActivity()).getImageLoader());

		// stops onDestroy() and onCreate() being called when the parent
		// activity is destroyed/recreated on configuration change
		setRetainInstance(true);

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

		getListView().setAdapter(property_list_adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		Log.d(TAG, "onResume : adapter count = " + property_list_adapter.getCount() + ", selected_item_position = "
				+ selected_item_position);

		setIsLoading(false);

		if (property_list_adapter.getCount() > 0 && selected_item_position != NO_SELECTION) {
			getListView().setItemChecked(selected_item_position, true);
			getListView().smoothScrollToPosition(selected_item_position+2);
		}
	}

	public void setIsLoading(boolean is_loading) {
		setListShown(!is_loading);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		selected_item_position = position;

		getListView().setItemChecked(position, true);

		property_selection_listener.onPropertySelected(property_list_adapter.getItem(position));
	}

	// there is new data to display. Stop the loading image, unset the
	// selection, empty the list and add the new data, update the display
	// and move the list to the top
	public void update(ArrayList<Property> retrieved_properties) {
		Log.d(TAG, "update()");

		setIsLoading(false);
		getListView().setItemChecked(selected_item_position, false);

		properties.clear();
		properties.addAll(retrieved_properties);
		property_list_adapter.notifyDataSetChanged();
		getListView().setSelection(0);
	}

	/*
	 * Return the currently selected property
	 */
	public Property getSelectedProperty() {
		if (property_list_adapter.getCount() > 0 && selected_item_position != NO_SELECTION) {
			return property_list_adapter.getItem(selected_item_position);
		} else {
			return null;
		}
	}

	// sort the list of albums by different fields
	public void sortByTitle() {
		Collections.sort(properties, Property.COMPARE_BY_TITLE);
		property_list_adapter.notifyDataSetChanged();
		getListView().setSelection(0);
	}

	public void sortByPriceAsc() {
		Collections.sort(properties, Property.COMPARE_BY_PRICE_ASC);
		property_list_adapter.notifyDataSetChanged();
		getListView().setSelection(0);
	}

	public void sortByPriceDesc() {
		Collections.sort(properties, Property.COMPARE_BY_PRICE_DESC);
		property_list_adapter.notifyDataSetChanged();
		getListView().setSelection(0);
	}

}
