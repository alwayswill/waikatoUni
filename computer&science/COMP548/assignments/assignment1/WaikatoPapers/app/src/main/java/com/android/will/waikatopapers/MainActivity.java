package com.android.will.waikatopapers;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 14/03/15.
 */

public class MainActivity extends PaperBase implements ActionBar.OnNavigationListener, ActionBarFragment.onActionBarItemSelectedListener {
    private static final String Tag = "MainActivity";
    private DrawerLayout leftDrawerLayout;
    private ListView leftDrawerList;
    public ActionBarDrawerToggle leftDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();
        initDrawer();
        initPapersList();


    }

    public void initPapersList() {
        if (getFragmentManager().findFragmentById(R.id.content_layout) == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.content_layout,
                            new PaperListFragment(), "papersListFragment").commit();
        }
    }

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
                            new ActionBarFragment(), "actionBarFragment").commit();
        }

    }

    //    set drawer data
    public void initDrawer() {
        Log.d(Tag, "initDrawer");
        leftDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        leftDrawerToggle = new ActionBarDrawerToggle(this, leftDrawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open,
                R.string.drawer_close) {
//            here we do not need to recreate menu because that will triger refresh list many times
            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
//                super.onDrawerOpened(view);
//                invalidateOptionsMenu();
            }
        };

        leftDrawerLayout.setDrawerListener(leftDrawerToggle);

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

    @Override
    public void onActionBarItemSelected(int position) {
        if(position != 0){
            refreshPapers();
        }

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("DrawerItemClickListener", "onItemClick");
            Toast.makeText(getApplicationContext(), "onItemSelected(" + position + ", " + id + ") = " + parent.getAdapter().getItem(position),
                    Toast.LENGTH_SHORT).show();
            refreshPapers();
        }
    }
    //refresh list
    public void refreshPapers() {
        PaperListFragment paperListFragment = (PaperListFragment) getFragmentManager().findFragmentByTag("papersListFragment");
        paperListFragment.refreshPapers();
//            close Drawer
        leftDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawerLayout.closeDrawers();
        Toast.makeText(this, "Refresh Papers",
                Toast.LENGTH_SHORT).show();
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        leftDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        boolean drawerOpen = leftDrawerLayout.isDrawerOpen(leftDrawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (leftDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(getClass().getName(), "onConfigurationChanged");
        leftDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(getClass().getName(), "onPostCreate");
        leftDrawerToggle.syncState();
    }
}

