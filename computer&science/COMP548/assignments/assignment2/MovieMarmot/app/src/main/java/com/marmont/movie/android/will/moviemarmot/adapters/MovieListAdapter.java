package com.marmont.movie.android.will.moviemarmot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.model.Movie;

import java.util.List;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 02/05/15.
 * adapter for the list of movies that retrieved from apis.
 */

public class MovieListAdapter extends ArrayAdapter<Movie> {
    private LayoutInflater mLayoutInflater;
    private ImageLoader mImageLoader = null;

    public MovieListAdapter(Context context, int resource, int textViewResourceId, List<Movie> objects, ImageLoader mImageLoader) {
        super(context, resource, textViewResourceId, objects);
        mLayoutInflater = LayoutInflater.from(context);
        this.mImageLoader = mImageLoader;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.movie_list_item, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (NetworkImageView) convertView.findViewById(R.id.movie_thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.movie_title);
            holder.rating = (RatingBar) convertView.findViewById(R.id.movie_rating);
            holder.mpaaRating = (TextView) convertView.findViewById(R.id.movie_mpaa_rating);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = this.getItem(position);

        holder.title.setText(movie.getTitle());
        holder.thumbnail.setImageUrl(movie.getPosterURL(), mImageLoader);
        holder.rating.setRating(movie.getRating());
        holder.mpaaRating.setText(movie.getMpaaRating());

        return convertView;
    }

    static class ViewHolder {
        NetworkImageView thumbnail;
        TextView title;
        RatingBar rating;
        TextView mpaaRating;
    }
}
