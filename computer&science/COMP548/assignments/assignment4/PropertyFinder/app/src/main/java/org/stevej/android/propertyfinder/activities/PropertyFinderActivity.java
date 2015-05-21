package org.stevej.android.propertyfinder.activities;

import org.json.JSONObject;
import org.stevej.android.propertyfinder.R;
import org.stevej.android.propertyfinder.fragments.PropertyDetailsFragment;
import org.stevej.android.propertyfinder.fragments.PropertyListFragment;
import org.stevej.android.propertyfinder.fragments.PropertyListFragment.PropertySelectionListener;
import org.stevej.android.propertyfinder.model.Property;
import org.stevej.android.propertyfinder.trademe.JSONParser;
import org.stevej.android.propertyfinder.trademe.TradeMeClientFragment;
import org.stevej.android.propertyfinder.trademe.TradeMeClientFragment.TradeMeResponseListener;
import org.stevej.android.propertyfinder.utils.CustomAnimator;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

public class PropertyFinderActivity extends Activity implements TradeMeResponseListener, PropertySelectionListener,
		ActionBar.OnNavigationListener {

	private static final String		TAG								= "PropertyFinderActivity";

	private static final String		TRADEME_CLIENT_FRAGMENT_TAG		= "TradeMeClientFragment";
	private static final String		PROPERTY_LIST_FRAGMENT_TAG		= "PropertyListFragment";
	private static final String		PROPERTY_DETAILS_FRAGMENT_TAG	= "PropertyDetailsFragment";
	private static final String		SELECTED_SUBURB_IDX				= "SELECTED_SUBURB_IDX";
	private static final int		NO_SELECTION					= -1;

	// stores position of currently selected suburb from menu so that we can store/restore it on config change
	private int						selected_suburb_position		= NO_SELECTION;

	// adapter for suburb menu in action bar
	private ArrayAdapter<String>	action_bar_nav_adapter;

	// stores device orientation
	private int						orientation;

	// fragment manager and fragments
	private FragmentManager			fragment_manager;
	private PropertyListFragment	property_list_fragment;
	private PropertyDetailsFragment	property_details_fragment;
	private TradeMeClientFragment	trademe_client;

	// we set these up but aren't using them (yet)
	private ShareActionProvider		share_action_provider;
	private Intent					share_intent;
	private MenuItem				search_menu_item;
	private SearchView				search_view;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.stevej.android.propertyfinder.activities.NavigationEnabledActivity #onCreate(android.os.Bundle)
	 * 
	 * Call superclass's onCreate. Set the content view (UI layout) of the superclass (NavigationEnabledActivity) to whatever is appropriate
	 * for this activity.
	 * 
	 * Do any initialisation that is specific to this activity, but does not require the UI to have been initialised.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);

		// set UI layout from resource file
		super.setContentView(R.layout.property_finder);
		
		PreferenceManager.setDefaultValues(this, R.xml.settings_all, true);

		// get the activity's fragment manager
		fragment_manager = getFragmentManager();

		// get current orientation
		orientation = getResources().getConfiguration().orientation;

		// add the trademe client, property list and property details fragments to this activity
		addFragments();

		// if we have saved state after a configuration change we can restore the previous suburb selection in the menu
		if (savedInstanceState != null) {
			selected_suburb_position = savedInstanceState.getInt(SELECTED_SUBURB_IDX);
		}

		// set up the intent for sharing data (sharing not yet implemented)
		configureShareIntent();
	}

	/*
	 * get references to the fragments. If we haven't already got the fragments (this is a 'clean' start of the activity) then create the
	 * fragment instances and add them to their UI containers
	 */
	private void addFragments() {
		Log.d(TAG, "addFragments()");

		property_list_fragment = (PropertyListFragment) getFragmentManager().findFragmentByTag(PROPERTY_LIST_FRAGMENT_TAG);
		property_details_fragment = (PropertyDetailsFragment) getFragmentManager().findFragmentByTag(PROPERTY_DETAILS_FRAGMENT_TAG);
		trademe_client = (TradeMeClientFragment) getFragmentManager().findFragmentByTag(TRADEME_CLIENT_FRAGMENT_TAG);

		FragmentTransaction ft = fragment_manager.beginTransaction();

		if (trademe_client == null) {
			trademe_client = new TradeMeClientFragment();
			ft.add(trademe_client, TRADEME_CLIENT_FRAGMENT_TAG);
		}

		if (property_list_fragment == null) {
			property_list_fragment = new PropertyListFragment();
			ft.add(R.id.property_list_container, property_list_fragment, PROPERTY_LIST_FRAGMENT_TAG);
		}

		if (property_details_fragment == null) {
			property_details_fragment = new PropertyDetailsFragment();
			ft.add(R.id.property_details_container, property_details_fragment, PROPERTY_DETAILS_FRAGMENT_TAG);
		}

		ft.commit();
		fragment_manager.executePendingTransactions();

	}

	/*
	 * We will eventually add sharing functionality via the Share ActionBar item. Create an initial Intent that we'll use to launch the app
	 * that will be used to share data.
	 */
	private void configureShareIntent() {
		share_intent = new Intent(Intent.ACTION_SEND);
		share_intent.setType("*/*");
	}

	/*
	 * Configure the ActionBar appearance and behaviour. Add a predefined list of items to the ActionBar navigation drop down list. Register
	 * this activity to handle navigation selections. Set the initial selection.
	 */
	private void configureActionBar() {
		Log.d(TAG, "configureActionBar()");

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		String[] suburb_list = getResources().getStringArray(R.array.suburb_list);

		action_bar_nav_adapter = new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1,
				android.R.id.text1);
		action_bar_nav_adapter.addAll(suburb_list);
		actionBar.setListNavigationCallbacks(action_bar_nav_adapter, this);

		actionBar.setSelectedNavigationItem(selected_suburb_position);
	}

	/******************************************
	 * 
	 * Handlers for responses returned by the requests to the Trademe service
	 * 
	 ******************************************/

	// Parse the trademe response and update the list fragment with the new data
	@Override
	public void onTradeMePropertyListResponse(JSONObject json_object) {
		Log.d(TAG, "onTradeMePropertyListResponse()");
		property_list_fragment.update(JSONParser.parsePropertyListJSON(json_object));
	}

	// not needed yet. We'll use it when we need more info about a property (eg its photos)
	@Override
	public void onTradeMePropertyDetailsResponse(JSONObject json_object) {
		// TODO Auto-generated method stub
	}

	/******************************************
	 * 
	 * User input handlers
	 * 
	 ******************************************/

	/*
	 * ActionBar drop down nav list selection handler. Issue a request to the trademe service for properties in the given suburb
	 */
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		Log.d(TAG, "onNavigationItemSelected : " + position);

		if (selected_suburb_position == position) {
			return true;
		}

		selected_suburb_position = position;
		String[] suburb_id_list = getResources().getStringArray(R.array.suburb_ids);
		String suburb_id = suburb_id_list[position];

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String num_to_load = Integer.toString(preferences.getInt("pref_num_to_load", 10));
		trademe_client.getPropertyList(suburb_id, "16", "14", num_to_load);
		property_list_fragment.setIsLoading(true);
		return true;
	}

	/*
	 * ActionBar items selection handler
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_sort_alpha:
			property_list_fragment.sortByTitle();
			return true;
		case R.id.action_sort_price_asc:
			property_list_fragment.sortByPriceAsc();
			return true;
		case R.id.action_sort_price_desc:
			property_list_fragment.sortByPriceDesc();
			return true;
		case R.id.action_legal:
			Toast.makeText(this, "Legal", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_map:
			Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_single_screen_settings:
			startActivity(new Intent(this, SingleScreenSettingsActivity.class));
			return true;
		case R.id.action_multi_screen_settings:
			startActivity(new Intent(this, MultiScreenSettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * If we are in portrait orientation and showing the property details we treat a click on the back button as returning to the property
	 * list - reverse the previous animation to redisplay the property list. Otherwise use the default behaviour.
	 */
	@Override
	public void onBackPressed() {
		View property_details_container = findViewById(R.id.property_details_container);
		boolean property_details_visible = property_details_container.getVisibility() == View.VISIBLE;

		if (orientation == Configuration.ORIENTATION_PORTRAIT && property_details_visible) {
			CustomAnimator.reversePrevious();
		} else {
			super.onBackPressed();
		}
	}

	/*
	 * Invoked from property list fragment when item is clicked
	 */
	@Override
	public void onPropertySelected(Property property) {
		property_details_fragment.update(property);
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			showPropertyDetails();
		}
	}
	


	/******************************************
	 * 
	 * Utility methods
	 * 
	 ******************************************/

	private void showPropertyDetails() {
		View property_list_container = findViewById(R.id.property_list_container);
		View property_details_container = findViewById(R.id.property_details_container);

		CustomAnimator.slide(property_details_container, property_list_container, CustomAnimator.DIRECTION_LEFT, 400);
	}

	public ImageLoader getImageLoader() {
		return trademe_client.getImageLoader();
	}

	/******************************************
	 * 
	 * Overridden lifecycle methods
	 * 
	 ******************************************/

	// make sure all pending network requests are cancelled when this activity stops
	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();
		if (trademe_client != null) {
			trademe_client.cancelAllRequests();
		}
	}

	// now the UI exists we can configure the action bar
	@Override
	protected void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();
		configureActionBar();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState");
		int selected_suburb_idx = getActionBar().getSelectedNavigationIndex();
		outState.putInt(SELECTED_SUBURB_IDX, selected_suburb_idx);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu()");

		getMenuInflater().inflate(R.menu.action_bar, menu);

		share_action_provider = (ShareActionProvider) menu.findItem(R.id.action_share).getActionProvider();
		share_action_provider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		share_action_provider.setShareIntent(share_intent);

		search_menu_item = (MenuItem) menu.findItem(R.id.action_search);
		search_view = (SearchView) search_menu_item.getActionView();

		return true;
	}


}
