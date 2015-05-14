package com.android.will.wnews.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.will.wnews.R;
import com.android.will.wnews.fragments.NewsDetailsFragment;
import com.android.will.wnews.model.News;

public class NewsDetailActivity extends Activity {
    public static final String EXTRA_MESSAGE="selectedNews";
    public static final String NEWS_DETAIL_FRAGMENT_TAG = "NewsDetailFragment";
    public News mSelectedNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectedNews = getIntent().getParcelableExtra(EXTRA_MESSAGE);

        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {

            NewsDetailsFragment mNewsDetailsFragment = NewsDetailsFragment.newInstance(mSelectedNews);

            getFragmentManager().beginTransaction()
                    .add(android.R.id.content,
                            mNewsDetailsFragment, NEWS_DETAIL_FRAGMENT_TAG).commit();
        }

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

//        setContentView(R.layout.activity_news_detail);

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


}
