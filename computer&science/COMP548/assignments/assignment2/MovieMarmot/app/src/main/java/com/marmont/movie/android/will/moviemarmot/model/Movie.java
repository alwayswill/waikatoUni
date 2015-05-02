package com.marmont.movie.android.will.moviemarmot.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 22/04/15.
 */
public class Movie implements Parcelable {
    public String id;
    public String posterURL;
    public String title;
    public String mpaaRating;
    public float rating;
    public String summary;

    public Movie(){

    }

    public Movie(String id, String PosterURL, String title, String mpaaRating, float rating, String summary) {
        this.id = id;
        this.posterURL = PosterURL;
        this.title = title;
        this.mpaaRating = mpaaRating;
        this.rating = rating;
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String toJSONString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("title", title.replace("&", "%26"));
            return json;
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    private Movie(Parcel in) {

        this.id = in.readString();
        this.posterURL = in.readString();
        this.title = in.readString();
        this.mpaaRating = in.readString();
        this.rating = in.readFloat();
        this.summary = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(posterURL);
        dest.writeString(title);
        dest.writeString(mpaaRating);
        dest.writeFloat(rating);
        dest.writeString(summary);
    }

    public static final Parcelable.Creator<Movie>	CREATOR	= new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            Movie m = new Movie(in);
            return m;
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
