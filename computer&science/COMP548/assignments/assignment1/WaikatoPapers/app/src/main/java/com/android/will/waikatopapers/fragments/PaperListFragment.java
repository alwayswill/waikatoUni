package com.android.will.waikatopapers.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import com.android.will.waikatopapers.interfaces.PaperSelectionListener;
import com.android.will.waikatopapers.R;
import com.android.will.waikatopapers.adapters.PaperListAdapter;
import com.android.will.waikatopapers.model.Paper;

import java.util.ArrayList;
import java.util.Random;

public class PaperListFragment extends ListFragment {

    public PaperListAdapter paperListAdapter;
    private ArrayList<Paper> papers = new ArrayList<Paper>();
    private PaperSelectionListener paper_selection_listener = null;
    private static final int NO_SELECTION = -1;
    private int selected_item_position;

    public PaperListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate()");

        /*
        * if set this one, the fragment will only call onAttach and onDetach because, it won't get new instance on the on create won't ne called.
        * */
        //setRetainInstance(true);//debug
        super.onCreate(savedInstanceState);
        //set up data
        initPapers();
        selected_item_position = NO_SELECTION;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        paperListAdapter = new PaperListAdapter(getActivity(), R.layout.paper_list_item, R.id.paper_title, papers);
        getListView().setAdapter(paperListAdapter);

        /*
        * when activity created, this method will be called. if we can get what we stored in savedInstanceState, e.g. position of selection in paper list
        * */
        if (savedInstanceState != null) {
            Log.d(getClass().getName(), "onActivityCreated() : getting selected_item_position from savedInstanceState");
            selected_item_position = savedInstanceState.getInt("selected_item_position");
        }

        if (selected_item_position != NO_SELECTION) {
            paper_selection_listener.onPaperSelected(paperListAdapter.getItem(selected_item_position));
        }

        Log.d(getClass().getName(), "onActivityCreated() : selected_item_position = " + selected_item_position);
        super.onActivityCreated(savedInstanceState);
    }


    public void initPapers() {
//        Random randomGenerator = new Random();
//        int limits = randomGenerator.nextInt(10) + 1;
        Log.d(getClass().getName(), "initPapers");
        for (int i = 0; i < 30; i++) {
            papers.add(new Paper(getPaperTitle(), "Paper:" + i, "content", "Steve", "LEVEL 100", "B Semester", R.drawable.ic_paper));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(getClass().getSimpleName(), "onAttach()");
        super.onAttach(activity);
        //if want to call intent this act, must implement this mesthod
        try {
            paper_selection_listener = (PaperSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement PropertySelectionListener");
        }


    }

    public void refreshPapers() {
//        papers = new ArrayList<>();
//        Random randomGenerator = new Random();
//        int limits = randomGenerator.nextInt(10) + 1;
//        for (int i = 0; i < limits; i++) {
//            papers.add(new Paper(getPaperTitle(), "Paper:" + getPaperTitle(), "content", "Steve", "LEVEL 100", "B Semester", R.drawable.ic_paper));
//        }
//        paperListAdapter.clear();
//        paperListAdapter.addAll(papers);
//        paperListAdapter.notifyDataSetChanged();
    }


    public String getPaperTitle() {
        String title = "";
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(900) + 100;

        for (int i = 0; i < 4; i++) {
            char random_Char = (char) (97 + randomGenerator.nextInt(25));
            title = title + "" + random_Char;
        }
        title = title.toUpperCase() + randomInt;
        return title;
    }

    @Override
    public void onDetach() {
        Log.d(getClass().getSimpleName(), "onDetach()");
        super.onDetach();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(getClass().getSimpleName(), "onListItemClick()");
        selected_item_position = position;
        getListView().setItemChecked(position, true);
        paper_selection_listener.onPaperSelected(paperListAdapter.getItem(position));
        super.onListItemClick(l, v, position, id);
    }

    /*
    * This method will be called before placing the activity in such a background state, and this bundle will be recived by onCreate later.
    * but the official doc says we should use onPause method, because this method is not part of life cycle and is not guaranteed to be called.
    *
    * */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //store our selection
        outState.putInt("selected_item_position", selected_item_position);
        Log.d(getClass().getName(), "onSaveInstanceState() : selected_item_position = " + selected_item_position);
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        Log.d(getClass().getName(), "onResume()");
        super.onResume();
        //show data until it is ready
        setListShown(true);
        //only single selection
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //scroll to previous selected one
        if (paperListAdapter.getCount() > 0 && selected_item_position != NO_SELECTION) {
            Log.d(getClass().getName(), "onResume() : checking and scrolling");
            getListView().setItemChecked(selected_item_position, true);
            getListView().setSelection(selected_item_position);
            getListView().smoothScrollToPositionFromTop(selected_item_position, 200, 200);
        }
    }
}
