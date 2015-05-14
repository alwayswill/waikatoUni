package com.android.will.wnews.activities;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.ShareActionProvider;

import com.android.volley.toolbox.ImageLoader;
import com.android.will.wnews.R;
import com.android.will.wnews.apiClients.ClientFragment;
import com.android.will.wnews.apiClients.JSONParser;
import com.android.will.wnews.fragments.NavigationDrawerFragment;
import com.android.will.wnews.fragments.NewsListFragment;
import com.android.will.wnews.interfaces.ApiResponseListener;
import com.android.will.wnews.interfaces.NewsSelectionListener;
import com.android.will.wnews.model.News;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity implements NewsSelectionListener,ApiResponseListener, NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String		TAG								= "MainActivity";
    public static final String CLIENT_FRAGMENT_TAG = "ClientFragment";
    public static final String NEWS_LIST_FRAGMENT_TAG = "NewsListFragment";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    private static final int NO_SELECTION = -1;


    private int mSelectedMoviePosition = NO_SELECTION;
    private boolean mNewsListChanged = true;//debug


    // fragment manager and dynamic fragments
    private FragmentManager			mFragmentManager;
    private ClientFragment mClientFragment;
    public ArrayList<News> retrievedNews;
    private CharSequence mTitle;


    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getFragmentManager();
        addNonUIFragments();
//        set up list
        mFragmentManager.beginTransaction()
                .replace(R.id.container, new NewsListFragment(), NEWS_LIST_FRAGMENT_TAG).commit();

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

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
    public void onNavigationDrawerItemSelected(int position) {

        mClientFragment = (ClientFragment) mFragmentManager.findFragmentByTag(CLIENT_FRAGMENT_TAG);
        mClientFragment.getMovieList();

        Log.d(TAG, "onNavigationDrawerItemSelected");


    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            case R.id.action_settings:
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
    }

    @Override
    public void onNewsDetailsResponse(JSONObject json_object) {

        Log.d(TAG, json_object.toString());
    }


    @Override
    public void onNewsSelected(News news) {
        Log.d(TAG, "onNewsSelected() : " + news.title);

        Intent intent= new Intent(this, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.EXTRA_MESSAGE, news);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        }else{
            super.onBackPressed();
        }

    }

    /*
    * This is for images in the movie list by using ImageLoader of Volley.
    * */
    public ImageLoader getImageLoader() {
        return mClientFragment.getImageLoader();
    }


}
