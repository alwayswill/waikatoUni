package com.marmont.movie.android.will.moviemarmot.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.fragments.MovieDetailsFragment;
import com.marmont.movie.android.will.moviemarmot.fragments.MovieListFragment;
import com.marmont.movie.android.will.moviemarmot.interfaces.MYFResponseListener;
import com.marmont.movie.android.will.moviemarmot.interfaces.MovieSelectionListener;
import com.marmont.movie.android.will.moviemarmot.model.Movie;
import com.marmont.movie.android.will.moviemarmot.myapifilms.ClientFragment;
import com.marmont.movie.android.will.moviemarmot.myapifilms.JSONParser;

import org.json.JSONArray;

import java.util.ArrayList;

/*
 * the initial activity when the app is launched
 */
public class MainActivity extends Activity implements MYFResponseListener, MovieSelectionListener, ActionBar.OnNavigationListener {

    private static final String TAG = "MainActivity";
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "MovieDetailsFragment";
    private static final String CLIENT_FRAGMENT_TAG = "ClientFragment";
    private static final String MOVIE_LIST_FRAGMENT_TAG = "MovieListFragment";
    private static final String KEY_MOVIE_LIST = "MovieList";

    private static final String SELECTED_FILTER_IDX = "SELECTED_FILTER_IDX";
    private static final int NO_SELECTION = -1;
    public static final String DATA_SOURCE_URL = "http://www.imdb.com";

    private int mSelectedMoviePosition = NO_SELECTION;
    private boolean mMovieFilterChanged = false;

    public ArrayList<Movie> retrievedMovies;
    private ArrayAdapter<String> mActionBarNavAdapter;
    private FragmentManager mFragmentManager;
    private ClientFragment mClientFragment;


    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        sliding_layout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        sliding_layout.setPanelSlideListener(new SliderListener());
        sliding_layout.openPane();

        mFragmentManager = getFragmentManager();
        addNonUIFragments();

        setUpShareIntent();
        initActionBar();

        if (!isNetworkConnected()) {
            Toast.makeText(this, "Fail to connect to Internet.", Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null) {
            mSelectedMoviePosition = savedInstanceState.getInt(SELECTED_FILTER_IDX);
            retrievedMovies = savedInstanceState.getParcelableArrayList(KEY_MOVIE_LIST);
            getActionBar().setSelectedNavigationItem(mSelectedMoviePosition);
            Log.d(TAG, "onCreate() : retrieved selected filter position = " + mSelectedMoviePosition);
        } else {
            Log.d(TAG, "onCreate() is null");
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

    /**
     * set up Share intent
     */
    private void setUpShareIntent() {
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType("*/*");
    }

    /**
     * set up actionBar
     */
    public void initActionBar() {
        Log.d(TAG, "initActionBar");
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        String[] movie_menu_list = getResources().getStringArray(R.array.movie_filter_menu);

        mActionBarNavAdapter = new ArrayAdapter<>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1);
        mActionBarNavAdapter.addAll(movie_menu_list);

        actionBar.setListNavigationCallbacks(mActionBarNavAdapter, this);
    }

    @Override
    public void onMYFMovieListResponse(JSONArray json_array) {
        Log.d(getClass().getName(), "onMYFMovieListResponse()");
        MovieListFragment movie_list_fragment = (MovieListFragment) mFragmentManager.findFragmentByTag(MOVIE_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {
            retrievedMovies = JSONParser.parseMovieListJSON(json_array);
            movie_list_fragment.update(retrievedMovies, mMovieFilterChanged);
        }
    }

    @Override
    public void onMYFErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timeout, Please try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Network Exception, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        String movieType = ClientFragment.IN_THEATRES_MOVIES;

        if (itemPosition != 0) {
            movieType = ClientFragment.OPENING_MOVIES;
        }

        Log.d(TAG, "onNavigationItemSelected : " + itemPosition + ":" + itemId);

        mMovieFilterChanged = mSelectedMoviePosition != itemPosition;

        mSelectedMoviePosition = itemPosition;
        Log.v(TAG, "mSelectedMoviePosition:" + mSelectedMoviePosition + "--------itemPosition:" + itemPosition);

        MovieListFragment movie_list_fragment = (MovieListFragment) mFragmentManager.findFragmentByTag(MOVIE_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {
            movie_list_fragment.setIsLoading(true);
        }
        //when device roated, it will reload data from storied data rather than api.
        if (retrievedMovies != null && !mMovieFilterChanged) {
            Log.d(TAG, "update movies from storaged data");
            if (movie_list_fragment != null) {
                movie_list_fragment.update(retrievedMovies, mMovieFilterChanged);
            }

        } else {
            Log.d(TAG, "update movies from api");
            mClientFragment.getMovieList(movieType);
        }

        if (mMovieFilterChanged) {
            clearDetailFragment();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
                if (!sliding_layout.isOpen()) {
                    sliding_layout.openPane();
                }
                return true;

            case R.id.action_legal_notices:
                Toast.makeText(this, "Legal", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {

        Log.d(getClass().getName(), "onMovieSelected() : " + movie.title);

        MovieDetailsFragment movie_details_fragment = (MovieDetailsFragment) mFragmentManager
                .findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG);
        if (movie_details_fragment != null) {
            movie_details_fragment.update(movie);
        }

        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        sliding_layout.closePane();

    }

    @Override
    public void clearDetailFragment() {
        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        MovieDetailsFragment movie_details_fragment = (MovieDetailsFragment) mFragmentManager
                .findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG);
        if (movie_details_fragment != null) {
            movie_details_fragment.clear();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && !sliding_layout.isOpen()) {
                Log.d(TAG, "close slide");
                sliding_layout.openPane();
            }
        }
    }

    /*
    * This is for images in the movie list by using ImageLoader of Volley.
    * */
    public ImageLoader getImageLoader() {
        return mClientFragment.getImageLoader();
    }


    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (mClientFragment != null) {
            mClientFragment.cancelAllRequests();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        int selected_filter_idx = getActionBar().getSelectedNavigationIndex();
        outState.putInt(SELECTED_FILTER_IDX, selected_filter_idx);
        outState.putParcelableArrayList(KEY_MOVIE_LIST, retrievedMovies);
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState() : stored selected filter position = " + selected_filter_idx);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu()");

        getMenuInflater().inflate(R.menu.menu_main, menu);

        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.action_share).getActionProvider();
        mShareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        mShareActionProvider.setShareIntent(mShareIntent);

        return true;
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        if (sliding_layout.isOpen()) {
            super.onBackPressed();
        } else {
            sliding_layout.openPane();
        }
    }

    /**
     * click the brower icon to access homepage of website
     */
    public void DBSourceHomepage(MenuItem item) {
        String tagetUrl = DATA_SOURCE_URL;
        Movie selected_movie = new Movie();
        MovieListFragment movie_list_fragment = (MovieListFragment) mFragmentManager.findFragmentByTag(MOVIE_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {
            selected_movie = movie_list_fragment.getSelectedMovie();
            if (selected_movie != null){
                tagetUrl = selected_movie.getUrlIMDB();
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(tagetUrl));
        startActivity(intent);
    }

    /**
     * set listener for slide pane
     */
    private class SliderListener extends SlidingPaneLayout.SimplePanelSlideListener {
        @Override
        public void onPanelOpened(View panel) {
            getActionBar().setHomeButtonEnabled(false);
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }

        @Override
        public void onPanelClosed(View panel) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
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
