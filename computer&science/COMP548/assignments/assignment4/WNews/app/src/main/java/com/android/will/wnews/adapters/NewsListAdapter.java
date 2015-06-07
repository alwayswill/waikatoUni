package com.android.will.wnews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.will.wnews.R;
import com.android.will.wnews.model.News;

import java.util.List;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 13/05/15.
 *
 */

/**
 * Tailored adapter for news list.
 */
public class NewsListAdapter extends ArrayAdapter<News> {
    private LayoutInflater mLayoutInflater;
    private ImageLoader mImageLoader = null;

    public NewsListAdapter(Context context, int resource, int textViewResourceId, List<News> objects, ImageLoader mImageLoader) {
        super(context, resource, textViewResourceId, objects);
        mLayoutInflater = LayoutInflater.from(context);
        this.mImageLoader = mImageLoader;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.news_list_item, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (NetworkImageView) convertView.findViewById(R.id.news_thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.news_title);
            holder.date = (TextView) convertView.findViewById(R.id.news_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News news = this.getItem(position);

        holder.title.setText(news.title);
        holder.date.setText(news.timestamp);
        holder.thumbnail.setDefaultImageResId(R.drawable.image_holder);
        holder.thumbnail.setImageUrl(news.picURL, mImageLoader);

        return convertView;
    }

    static class ViewHolder {
        NetworkImageView thumbnail;
        TextView title;
        TextView date;
    }
}
