package com.android.will.wnews.fragments;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.will.wnews.R;
import com.android.will.wnews.interfaces.UserLoginListener;
import com.android.will.wnews.model.User;
import com.android.will.wnews.utils.Constants;
import com.android.will.wnews.utils.UserSession;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginFragment extends Fragment implements TextView.OnEditorActionListener {

    private static final String TAG = "UserLoginFragment";
    public String mUsername;
    public String mPassword;
    private EditText mUsernameView;
    private EditText mPasswordView;

    private UserLoginListener mUserLoginListener;
    private UserSession mUserSession;
    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);

        mUserSession = new UserSession(getActivity().getApplicationContext());

        try {
            mUserLoginListener = (UserLoginListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement UserLoginListener");
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mUsernameView = (EditText) getView().findViewById(R.id.user_login_username);
        mPasswordView = (EditText) getView().findViewById(R.id.user_login_password);
        String username = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString(Constants.KEY_USER_NAME, "");
        mUsernameView.setText(username);
        mPasswordView.setOnEditorActionListener(this);


    }


    private void doLogin() {
        Log.d(TAG, "doLogin");
        mUsername = mUsernameView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        if(mUsername.trim().length() > 0 && mPassword.trim().length() > 0){
            mUserLoginListener.onAuthenticate(mUsername, mPassword);
            return;
        }

        Toast.makeText(getActivity(),
                "Username/Password is incorrect",
                Toast.LENGTH_LONG).show();

    }

    public void update(User user){
        Log.d(TAG, "update");
        if (user.id != 0){
            mUserSession.createUserLoginSession(user);

            Toast.makeText(getActivity(),
                    "Login sucessfully",
                    Toast.LENGTH_LONG).show();

            mUsernameView.clearFocus();
            mPasswordView.clearFocus();

            mUserLoginListener.onLoginSuccessfully();
        }else{
            Toast.makeText(getActivity(),
                    "Username/Password is incorrect",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, "onEditorAction");
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_GO){

            doLogin();
            handled = true;
        }
        return handled;
    }
}
