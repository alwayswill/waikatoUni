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


public class MainActivity extends Activity implements ActionBar.OnNavigationListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private static final String Tag = "MainActivity";
    private DrawerLayout leftDrawerLayout;
    private ListView leftDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Tag, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initDrawer();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(Tag, "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        set the second dropdown for semester
        String[] sem_list = getResources().getStringArray(R.array.sem_list);
        Spinner semester_spinner =
                (Spinner) menu.findItem(R.id.action_filter_semester).getActionView();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sem_list);

        semester_spinner.setOnItemSelectedListener(this);
        semester_spinner.setAdapter(adapter);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(Tag, "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initActionBar() {
        final android.app.ActionBar actionBar = getActionBar();
        Log.d(Tag, "initActionBar");
//        set the first dropdown on actionbar
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        String[] level_list = getResources().getStringArray(R.array.level_list);

        ArrayAdapter<String> level_list_adapter;
        level_list_adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, level_list);
        actionBar.setListNavigationCallbacks(level_list_adapter, this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(Tag, "onItemClick");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(Tag, "onItemSelected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(Tag, "onNothingSelected");
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DrawerItemClickListener", "onItemClick");
//            selectItem(position);
        }
    }
}

