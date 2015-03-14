package com.android.will.waikatopapers;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 14/03/15.
 */

public class MainActivity extends PaperBase implements ActionBar.OnNavigationListener {
    private static final String Tag = "MainActivity";
    private DrawerLayout leftDrawerLayout;
    private ListView leftDrawerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();
        initDrawer();
//        initPapersList();


    }

//    public void initPapersList(){
//        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.content_layout,
//                            new PaperListFragment()).commit();
//        }
//    }

    public void initActionBar() {
        final android.app.ActionBar actionBar = this.getActionBar();
        Log.d(getClass().getName(), "initActionBar");

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

//        set up for actionbar fragment
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content,
                            new ActionBarFragment()).commit();
        }

    }

    //    set drawer data
    public void initDrawer() {
        Log.d(Tag, "initDrawer");
        leftDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        String[] subject_list = getResources().getStringArray(R.array.subject_list);
        ArrayAdapter<String> subject_list_adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, subject_list);

        leftDrawerList.setAdapter(subject_list_adapter);
        leftDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Log.d(Tag, "onNavigationItemSelected");
        return false;
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("DrawerItemClickListener", "onItemClick");
            Toast.makeText(getApplicationContext(), "onItemSelected(" + position + ", " + id + ") = " + parent.getAdapter().getItem(position),
                    Toast.LENGTH_SHORT).show();
        }
    }
}

