package com.android.will.waikatopapers;


import android.app.ActionBar;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.AdapterView.OnItemSelectedListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActionBarFragment extends Fragment implements OnItemSelectedListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate");
        setRetainInstance(true);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(getClass().getName(), "onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_main, menu);


//      set up dropdown for level
        String[] level_list = getResources().getStringArray(R.array.level_list);

        Spinner levelSpinner = (Spinner) menu.findItem(R.id.action_filter_level).getActionView();
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, level_list);

        levelSpinner.setOnItemSelectedListener(this);
        levelSpinner.setAdapter(levelAdapter);


//      set up dropdown for semester
        String[] sem_list = getResources().getStringArray(R.array.sem_list);
        Spinner semester_spinner =
                (Spinner) menu.findItem(R.id.action_filter_semester).getActionView();
        ArrayAdapter<String> semAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sem_list);

        semester_spinner.setOnItemSelectedListener(this);
        semester_spinner.setAdapter(semAdapter);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(getClass().getName(), "onOptionsItemSelected");

        switch (item.getItemId()) {
            case R.id.action_sort_date:
                Toast.makeText(getActivity(), "sort by date", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_sort_hours:
                Toast.makeText(getActivity(), "sort by course hourses", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(getClass().getName(), "onItemSelected");
        Toast.makeText(getActivity(), "onItemSelected(" + position + ", " + id + ") = " + parent.getAdapter().getItem(position),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity(), "onNothingSelected" + parent.toString(),
                Toast.LENGTH_SHORT).show();
        Log.d(getClass().getName(), "onNothingSelected");
    }
}
