package org.stevej.android.propertyfinder.activities;

import java.util.ArrayList;

import org.stevej.android.propertyfinder.R;
import org.stevej.android.propertyfinder.adapters.PropertyListAdapter;
import org.stevej.android.propertyfinder.model.Property;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class PropertyFinderActivity extends Activity implements ActionBar.OnNavigationListener, OnItemClickListener {

	private static final String TAG = "PropertyFinderActivity";

	private ActionBarDrawerToggle nav_drawer_toggle;
	private ShareActionProvider share_action_provider;
	private Intent share_intent;

	private ArrayList<Property> properties = new ArrayList<Property>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate()");
		// Debug.startMethodTracing("PropertyFinder", 50 * 1024 * 1024);

		setContentView(R.layout.activity_propery_finder);

		setUpShareIntent();
		setUpActionBar();
		setUpNavigationDrawer();

		initialisePropertyList();
	}

	/*
	 * Set the initial content of the list of properties. Eventually this will
	 * be retrieved over the network. Hard coded data is used here for the
	 * example.
	 */
	private void initialisePropertyList() {
		properties.add(new Property("Exclusive Townhouse", "$450,000", R.drawable.ic_launcher));
		properties.add(new Property("Close to Uni", "$375,000", R.drawable.ic_launcher));
		properties.add(new Property("Large Family Home", "$400,000", R.drawable.ic_launcher));

		PropertyListAdapter adapter = new PropertyListAdapter(this, R.layout.property_list_item, R.id.property_title, properties);

		ListView property_list = (ListView) findViewById(R.id.property_list);
		property_list.setAdapter(adapter);

		property_list.setOnItemClickListener(this);
	}

	/*
	 * We will eventually add sharing functionality via the Share ActionBar
	 * item. Create an initial Intent that we'll use to launch the app that will
	 * be used to share data.
	 */
	private void setUpShareIntent() {
		share_intent = new Intent(Intent.ACTION_SEND);
		share_intent.setType("*/*");
	}

	/*
	 * Configure the ActionBar appearance and behaviour. Add a predefined list
	 * of items to the ActionBar navigation drop down list.
	 */
	private void setUpActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		String[] suburb_list = getResources().getStringArray(R.array.suburb_list);

		ArrayAdapter<String> suburb_list_adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
				android.R.layout.simple_list_item_1, android.R.id.text1, suburb_list);
		actionBar.setListNavigationCallbacks(suburb_list_adapter, this);
	}

	/*
	 * Configure the navigation drawer appearance and behaviour. Add a
	 * predefined list of items to drawer.
	 */
	private void setUpNavigationDrawer() {
		DrawerLayout nav_drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		nav_drawer_toggle = new ActionBarDrawerToggle(this, nav_drawer_layout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View view) {
				invalidateOptionsMenu();
			}
		};
		nav_drawer_layout.setDrawerListener(nav_drawer_toggle);
		nav_drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		String[] nav_list = getResources().getStringArray(R.array.nav_list);
		ListView nav_list_view = (ListView) nav_drawer_layout.findViewById(R.id.nav_drawer_list);
		ArrayAdapter<String> nav_list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nav_list);
		nav_list_view.setAdapter(nav_list_adapter);

		nav_list_view.setOnItemClickListener(this);
	}

	/*
	 * Initial configuration for the ActionBar items
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_property_finder, menu);

		MenuItem search_menu_item = (MenuItem) menu.findItem(R.id.action_search);
		SearchView search_view = (SearchView) search_menu_item.getActionView();
		search_view.setIconifiedByDefault(true);

		share_action_provider = (ShareActionProvider) menu.findItem(R.id.action_share).getActionProvider();
		share_action_provider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		share_action_provider.setShareIntent(share_intent);

		return true;
	}

	/*
	 * Configure the ActionBar items (just before it is displayed)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		DrawerLayout nav_drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView nav_list = (ListView) nav_drawer_layout.findViewById(R.id.nav_drawer_list);
		boolean drawer_open = nav_drawer_layout.isDrawerOpen(nav_list);

		menu.findItem(R.id.action_map).setVisible(!drawer_open);
		menu.findItem(R.id.action_search).setVisible(!drawer_open);
		menu.findItem(R.id.action_share).setVisible(!drawer_open);
		menu.findItem(R.id.action_sort).setVisible(!drawer_open);
		return super.onPrepareOptionsMenu(menu);
	}

	/*
	 * ActionBar items selection handler
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (nav_drawer_toggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_map:
			Toast.makeText(this, "Map action", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_sort_alpha:
			Toast.makeText(this, "Alpha sort action", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_sort_price_asc:
			Toast.makeText(this, "Price asc sort action", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_sort_price_desc:
			Toast.makeText(this, "Price desc action", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_legal:
			Toast.makeText(this, "Legal notices action", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_settings:
			Toast.makeText(this, "Settings action", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * ActionBar drop down nav list selection handler
	 */
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		String[] suburb_list = getResources().getStringArray(R.array.suburb_list);
		String selected_item = suburb_list[position];
		Toast.makeText(this, "onNavigationItemSelected(" + position + ", " + id + ") = " + selected_item, Toast.LENGTH_SHORT).show();
		return true;
	}

	/*
	 * Navigation drawer/property list selection handler
	 */
	@Override
	public void onItemClick(AdapterView<?> adapter_view, View clicked_item, int position, long id) {
		switch (adapter_view.getId()) {
		case R.id.nav_drawer_list:
			Toast.makeText(this, "NavDrawer : onItemClick(" + position + ", " + id + ") = " + adapter_view.getAdapter().getItem(position),
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.property_list:
			PropertyListAdapter adapter = (PropertyListAdapter) adapter_view.getAdapter();
			Property property = (Property) adapter.getItem(position);
			Toast.makeText(this, "PropertyList : onItemClick(" + position + ", " + id + ") = " + property.getTitle(), Toast.LENGTH_SHORT)
					.show();

			properties.add(new Property("Another Property", "$1,000,000", R.drawable.ic_launcher));
			adapter.notifyDataSetChanged();

			Intent intent = new Intent(this, PropertyDetailsActivity.class);

			intent.putExtra("property_title", property.getTitle());
			intent.putExtra("property_price", property.getPrice());

			startActivity(intent);

			break;
		}
	}

	/*
	 * Release 20.0.0 and upward of support-v4 library changed the behaviour of
	 * the back button when the navigation drawer is open so that it causes the
	 * app to close. Prior to that it simply closed the drawer.
	 * 
	 * This built-in method of android.app.Activity intercepts back button
	 * presses. We close the drawer if it is open.
	 */
	@Override
	public void onBackPressed() {
		DrawerLayout nav_drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (nav_drawer_layout.isDrawerOpen(Gravity.START))
			nav_drawer_layout.closeDrawer(Gravity.START);
		else
			super.onBackPressed();
	}

	/*
	 * Configuration change callback methods
	 */

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
		nav_drawer_toggle.onConfigurationChanged(newConfig);
	}

	/*
	 * Activity lifecycle callback methods
	 */

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.d(TAG, "onPostCreate");
		nav_drawer_toggle.syncState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart()");

	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		// Debug.stopMethodTracing();
	}

}
