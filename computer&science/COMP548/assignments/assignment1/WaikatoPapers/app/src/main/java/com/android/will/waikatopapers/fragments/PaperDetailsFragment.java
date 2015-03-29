package com.android.will.waikatopapers.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.will.waikatopapers.R;
import com.android.will.waikatopapers.model.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaperDetailsFragment extends Fragment {


    public PaperDetailsFragment() {
        // Required empty public constructor
    }

    /*
     * Update the UI with new data
     */
    public void setContent(Paper paper) {
        Log.d(getClass().getName(), "setContent()");

        TextView title_view = (TextView) getView().findViewById(R.id.paper_details_title);
        TextView lecturer_view = (TextView) getView().findViewById(R.id.paper_details_lecturer);
        TextView content_view = (TextView) getView().findViewById(R.id.paper_details_content);

        title_view.setText(paper.getTitle());
        lecturer_view.setText(paper.getLecturer());
        content_view.setText(paper.getContent());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paper_detail, container, false);
    }


}
