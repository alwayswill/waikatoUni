package com.android.will.wnews.activities;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.will.wnews.R;
import com.android.will.wnews.apiClients.ClientFragment;
import com.android.will.wnews.apiClients.JSONParser;
import com.android.will.wnews.fragments.NavigationDrawerFragment;
import com.android.will.wnews.fragments.NewsListFragment;
import com.android.will.wnews.fragments.UserLoginFragment;
import com.android.will.wnews.interfaces.ApiResponseListener;
import com.android.will.wnews.interfaces.NewsSelectionListener;
import com.android.will.wnews.interfaces.UserLoginListener;
import com.android.will.wnews.model.News;
import com.android.will.wnews.model.User;
import com.android.will.wnews.providers.NewsSuggestionProvider;
import com.android.will.wnews.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity implements NewsSelectionListener, ApiResponseListener, NavigationDrawerFragment.NavigationDrawerCallbacks, OnEditorActionListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, View.OnFocusChangeListener, UserLoginListener {

    public static final String TAG = "MainActivity";
    public static final String CLIENT_FRAGMENT_TAG = "ClientFragment";
    public static final String NEWS_LIST_FRAGMENT_TAG = "NewsListFragment";
    public static final String USER_LOGIN_FRAGMENT_TAG = "UserLoginFragment";
    private static final String SELECTED_CATEOGRY_ID = "selectedCategoryId";

    private static final int NO_SELECTION = -1;

    private int mSelectedCategoryId = NO_SELECTION;

    private static final String KEY_WAITING = "waitingStatus";
    private static final String KEY_SEARCH_VIEW_TEXT = "keySearchViewText";
    private static final String KEY_SEARCH_VIEW_EXPANDED = "keySearchViewExpanded";


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private boolean mNewsListChanged = false;//debug
    private boolean mWaiting = false;

    // fragment manager and dynamic fragments
    private FragmentManager mFragmentManager;
    private ClientFragment mClientFragment;
    public ArrayList<News> retrievedNews;
    public ProgressDialog ringProgressDialog;
    private CharSequence mTitle;
    private MenuItem mSearchMenuItem;
    private UserSession mUserSession;

    // holds action bar search widget text that we save/restore on configuration change
    private CharSequence mQueryText = "";

    // holds action bar search widget state that we save/restore on configuration change
    boolean mSearchViewExpanded = false;

    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mFragmentManager = getFragmentManager();
        addNonUIFragments();


        if (savedInstanceState != null) {
            mSelectedCategoryId = savedInstanceState.getInt(SELECTED_CATEOGRY_ID);
            mWaiting = savedInstanceState.getBoolean(KEY_WAITING);
            Log.d(TAG, "onCreate() : retrieved mSelectedCategoryId = " + mSelectedCategoryId);

            if (savedInstanceState.containsKey(KEY_SEARCH_VIEW_TEXT)) {
                mQueryText = savedInstanceState.getCharSequence(KEY_SEARCH_VIEW_TEXT);
            }

            if (savedInstanceState.containsKey(KEY_SEARCH_VIEW_EXPANDED)) {
                mSearchViewExpanded = savedInstanceState.getBoolean(KEY_SEARCH_VIEW_EXPANDED);
            }

            if (mWaiting) {
                showLoading();
            }


        }

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mUserSession = new UserSession(getApplicationContext());

        if (!isNetworkConnected()) {
            Toast.makeText(this, "Fail to connect to Internet.", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * add client fragment into this view for requesting data
     */
    private void addNonUIFragments() {
        Log.d(getClass().getName(), "addNonUIFragments()");

        mClientFragment = (ClientFragment) mFragmentManager.findFragmentByTag(CLIENT_FRAGMENT_TAG);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mClientFragment == null) {
            mClientFragment = new ClientFragment();
            fragmentTransaction.add(mClientFragment, CLIENT_FRAGMENT_TAG);
        }

        fragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {

        Log.d(TAG, "onNavigationDrawerItemSelected : mSelectedCategoryId" + mSelectedCategoryId + "||position:" + position);
        mNewsListChanged = mSelectedCategoryId != position;

        mSelectedCategoryId = position;

        //when device roated, it will reload data from storied data rather than api.
        if (mNewsListChanged) {
            Log.d(TAG, "update news from api");
            showLoading();
            mClientFragment.getNewsList(position);
        }



        UserLoginFragment userLoginFragment = (UserLoginFragment) mFragmentManager.findFragmentByTag(USER_LOGIN_FRAGMENT_TAG);
        if(userLoginFragment != null){
            mFragmentManager.popBackStack();
            mFragmentManager.beginTransaction().remove(userLoginFragment);
        }

        if (!fromSavedInstanceState) {
            Log.d(TAG, "replace newslist fragment");
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, new NewsListFragment(), NEWS_LIST_FRAGMENT_TAG).commit();
        }

//            mFragmentManager.executePendingTransactions();

        Log.d(TAG, "onNavigationDrawerItemSelected : " + position);


    }

    public void restoreActionBar() {
        Log.d(TAG, "restoreActionBar");
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");


        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            configureSearchView(menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void configureSearchView(Menu menu) {

        mSearchMenuItem = (MenuItem) menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName activity_name = getComponentName();
        SearchableInfo searchable_info = searchManager.getSearchableInfo(activity_name);
        searchView.setSearchableInfo(searchable_info);

        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("News name");
        searchView.setIconifiedByDefault(true);

        // if it was expanded before a config change, expand it again and hide the keyboard
        if (mSearchViewExpanded) {
            searchView.setIconified(false);
            mSearchMenuItem.expandActionView();
            searchView.clearFocus();
            searchView.setQuery(mQueryText, false);
        }

        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            case R.id.action_settings:
                onSearchRequested();
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_login:

                mFragmentManager.beginTransaction()
                        .replace(R.id.container, new UserLoginFragment(), USER_LOGIN_FRAGMENT_TAG).addToBackStack(null).commit();
                return true;
            // Respond to the action bar's Up/Home button
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNewsListResponse(JSONObject json_object) {
        Log.d(getClass().getName(), "onNewsListResponse()");
        NewsListFragment newsListFragment = (NewsListFragment) mFragmentManager.findFragmentByTag(NEWS_LIST_FRAGMENT_TAG);
        if (newsListFragment != null) {
            retrievedNews = JSONParser.parseNewsListJSON(json_object);
            newsListFragment.update(retrievedNews, mNewsListChanged);
        }
        hideLoading();
    }

    @Override
    public void onNewsSearchListResponse(JSONObject json_object) {
        Log.d(getClass().getName(), "onNewsSearchListResponse()");
        NewsListFragment newsListFragment = (NewsListFragment) mFragmentManager.findFragmentByTag(NEWS_LIST_FRAGMENT_TAG);
        if (newsListFragment != null) {
            retrievedNews = JSONParser.parseNewsListJSON(json_object);
            newsListFragment.update(retrievedNews, mNewsListChanged);
        }
        hideLoading();
    }

    @Override
    public void onNewsDetailsResponse(JSONObject json_object) {

        Log.d(TAG, "onNewsDetailsResponse");
    }

    @Override
    public void onUserLoginResponse(JSONObject json_object) {
        Log.d(TAG, "onUserLoginResponse");

        UserLoginFragment userLoginFragment = (UserLoginFragment) mFragmentManager.findFragmentByTag(USER_LOGIN_FRAGMENT_TAG);
        if (userLoginFragment != null) {
            Log.d(TAG, "onUserLoginResponse:update");
            User user = JSONParser.parseUserJSON(json_object);
            userLoginFragment.update(user);
        }
        hideLoading();

    }

    @Override
    public void onApiErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timeout, Please try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Network Exception, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onNewsSelected(News news) {
        Log.d(TAG, "onNewsSelected() : " + news.title);

        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.EXTRA_MESSAGE, news);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }

    }

    /*
    * This is for images in the movie list by using ImageLoader of Volley.
    * */
    public ImageLoader getImageLoader() {
        return mClientFragment.getImageLoader();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (mClientFragment != null) {
            mClientFragment.cancelAllRequests();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        int selectedCategoryId = mNavigationDrawerFragment.mCurrentSelectedPosition;
        outState.putInt(SELECTED_CATEOGRY_ID, selectedCategoryId);
        outState.putBoolean(KEY_WAITING, mWaiting);

        outState.putCharSequence(KEY_SEARCH_VIEW_TEXT, mQueryText);

        if (mSearchMenuItem != null) {
            outState.putBoolean(KEY_SEARCH_VIEW_EXPANDED, mSearchMenuItem.isActionViewExpanded());
        }

        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState() : stored selected category position = " + selectedCategoryId);
    }

    public void showLoading() {
        Log.d(TAG, "showLoading");
        mWaiting = true;
        ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...", "Downloading data ...", true);
    }

    public void hideLoading() {
        Log.d(TAG, "hideLoading");
        mWaiting = false;
        if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
            ringProgressDialog.dismiss();
        }

    }

    /*
    * User input
    * */
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit:" + query);
//        record search query
        NewsSuggestionProvider.getBridge(this).saveRecentQuery(query, null);

        mQueryText = query;
        showLoading();
        mClientFragment.searchNews(query);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.clearFocus();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            mSearchMenuItem.collapseActionView();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, "onEditorAction:event:" + event.toString());
        if (event == null || event.getAction() == KeyEvent.ACTION_UP) {

            InputMethodManager imm =
                    (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        return (true);
    }

    @Override
    public boolean onClose() {
        return true;
    }

    /**
     * User
     */

    @Override
    public void onAuthenticate(String username, String password) {
        Log.d(TAG, "onAuthenticate");
        showLoading();
        mClientFragment.userLogin("shuzuli", "9162fa316df079ef770c95e05b708abb");
    }

    @Override
    public void onLoginSuccessfully() {
        UserLoginFragment userLoginFragment = (UserLoginFragment) mFragmentManager.findFragmentByTag(USER_LOGIN_FRAGMENT_TAG);
        if(userLoginFragment != null){
            mFragmentManager.popBackStack();
            mFragmentManager.beginTransaction().remove(userLoginFragment);
            Log.d(TAG, "user:"+mUserSession.getUserDetails().username);//debug
        }

    }


    /**
     * check network, if network including WIFI and mobile is not working, we are not able to download movies.
     */
    private boolean isNetworkConnected() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
