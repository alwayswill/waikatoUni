package com.android.will.waikatopapers;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

public class PaperListFragment extends ListFragment {

    public static ArrayList<String> papers = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(getClass().getSimpleName(), "onCreate()");
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, papers));
    }


    @Override
    public void onAttach(Activity activity) {
        Log.d(getClass().getSimpleName(), "onAttach()");
        super.onAttach(activity);
        //set up data

        for (int i = 0; i < 20; i++) {
            papers.add("Paper:"+getPaperTitle());
        }

    }

    public String getPaperTitle(){
        String title = "";
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(900)+100;
        for (int i = 0; i < 3; i++) {
            char random_Char = (char) (97 + randomGenerator.nextInt(25));
            title = title+""+random_Char;
        }
        title = title.toUpperCase()+randomInt;
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
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getActivity(), "onListItemClick(" + position + ", " + id + ") = " + l.getAdapter().getItem(position),
                Toast.LENGTH_SHORT).show();
    }

}
