package com.android.will.waikatopapers.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.android.will.waikatopapers.R;
import com.android.will.waikatopapers.fragments.PaperDetailsFragment;
import com.android.will.waikatopapers.model.Paper;

public class DetailsActivityActivity extends BaseActivity {

    Paper paper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            Log.d(getClass().getName(), "onCreate() : not required in landscape, finishing");
            return;
        }

        super.setContentView(R.layout.activity_paper_details);

        paper = new Paper(intent.getStringExtra("paper_title"));
        paper.setTitle(intent.getStringExtra("paper_title"));
        paper.setLecturer(intent.getStringExtra("paper_lecturer"));
        paper.setContent(getString(R.string.paper_content_sample));


        initActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        PaperDetailsFragment paperDetailsFragment = (PaperDetailsFragment) getFragmentManager().findFragmentByTag("paper_details_fragment");
        paperDetailsFragment.setContent(paper);

    }

    private void initActionBar() {
        Log.d(getClass().getName(), "initActionBar()");
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getClass().getName(), "onSaveInstanceState");
    }
}
