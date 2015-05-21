package com.android.will.wnews.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.will.wnews.R;
import com.android.will.wnews.apiClients.ClientFragment;
import com.android.will.wnews.apiClients.JSONParser;
import com.android.will.wnews.apiClients.UserClientFragment;
import com.android.will.wnews.fragments.NewsDetailsFragment;
import com.android.will.wnews.fragments.UserLoginFragment;
import com.android.will.wnews.interfaces.UserLoginListener;
import com.android.will.wnews.model.User;
import com.android.will.wnews.utils.CommonHelper;
import com.android.will.wnews.utils.Constants;
import com.android.will.wnews.utils.UserSession;

import org.json.JSONObject;

public class UserActivity extends BaseActivity implements UserLoginListener{

    public static final String TAG = "UserActivity";
    private FragmentManager mFragmentManager;
    private UserClientFragment mUserClientFragment;
    private UserSession mUserSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_user);
        mFragmentManager = getFragmentManager();

        addNonUIFragments();
        mUserSession = new UserSession(getApplicationContext());

        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {

            UserLoginFragment userLoginFragment = new UserLoginFragment();

            getFragmentManager().beginTransaction()
                    .add(android.R.id.content,
                            userLoginFragment, Constants.USER_LOGIN_FRAGMENT_TAG).commit();
        }
        if (savedInstanceState != null) {
            //showing loading
            if (mWaiting) {
                showLoading(this);
            }
        }

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    /**
     * add client fragment into this view for requesting data
     */
    private void addNonUIFragments() {
        Log.d(TAG, "addNonUIFragments()");

        mUserClientFragment = (UserClientFragment) mFragmentManager.findFragmentByTag(Constants.CLIENT_FRAGMENT_TAG);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mUserClientFragment == null) {
            mUserClientFragment = new UserClientFragment();
            fragmentTransaction.add(mUserClientFragment, Constants.CLIENT_FRAGMENT_TAG);
        }

        fragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();

    }

    public ImageLoader getImageLoader() {
        return mUserClientFragment.getImageLoader();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (mUserClientFragment != null) {
            mUserClientFragment.cancelAllRequests();
        }
    }


    @Override
    public void onAuthenticate(String username, String password) {
        Log.d(TAG, "onAuthenticate");
        showLoading(this);
        mUserClientFragment.userLogin(username, CommonHelper.md5(password));
    }

    @Override
    public void onLoginSuccessfully() {
        this.onBackPressed();
        Log.d(TAG, "onLoginSuccessfully:" + mUserSession.getUserDetails().username);//debug

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.edit().putBoolean(Constants.KEY_UPDATE_ACTIONBAR, true).apply();
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
    public void onUserLoginResponse(JSONObject json_object) {
        Log.d(TAG, "onUserLoginResponse");

        UserLoginFragment userLoginFragment = (UserLoginFragment) mFragmentManager.findFragmentByTag(Constants.USER_LOGIN_FRAGMENT_TAG);
        if (userLoginFragment != null) {
            Log.d(TAG, "onUserLoginResponse:update");
            User user = JSONParser.parseUserJSON(json_object);
            userLoginFragment.update(user);
        }
        hideLoading();
    }
}
