package com.android.will.wnews.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.will.wnews.R;
import com.android.will.wnews.model.News;

/**
 * fragment for show details of a movie.
 */
public class NewsDetailsFragment extends Fragment {
    public static final String NEWS_DETAIL = "newsDetail";
    public News mNews;



    TextView title;
    TextView timestamp;
    TextView content;
    TextView dataSource;


    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    public static NewsDetailsFragment newInstance(News news) {
        NewsDetailsFragment f = new NewsDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(NEWS_DETAIL, news);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news_details, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNews = getArguments().getParcelable(NEWS_DETAIL);
        title = (TextView) getView().findViewById(R.id.news_details_title);
        timestamp = (TextView) getView().findViewById(R.id.news_details_timestamp);
        content = (TextView) getView().findViewById(R.id.news_details_content);
        dataSource = (TextView) getView().findViewById(R.id.movie_details_data_source);

        if(mNews != null){
            this.update(mNews);
        }else{
            this.clear();
        }

    }


    /**
     * set all views in detail view to be invisible
     */
    public void clear() {
        Log.d(getClass().getName(), "clear()");

        title.setText("");
        timestamp.setText("");
        content.setText("");
        dataSource.setVisibility(View.INVISIBLE);
    }

    /**
     * update content of all elements of detail view of the news
     */
    public void update(News news) {
        Log.d(getClass().getName(), "update()");

        title.setText(news.title);
        timestamp.setText(news.timestamp);
        content.setText(news.content);
        dataSource.setVisibility(View.VISIBLE);
    }

}
