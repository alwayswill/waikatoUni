package com.marmont.movie.android.will.moviemarmot.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 02/05/15.
 */
public class Movie implements Parcelable {
    public String id;
    public String posterURL;
    public String title;
    public String mpaaRating;
    public float rating;
    public String summary;

    public String getUrlIMDB() {
        return urlIMDB;
    }

    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    public String urlIMDB;

    public Movie(){

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
        this.urlIMDB = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(posterURL);
        dest.writeString(title);
        dest.writeString(mpaaRating);
        dest.writeFloat(rating);
        dest.writeString(summary);
        dest.writeString(urlIMDB);
    }

    public static final Parcelable.Creator<Movie>	CREATOR	= new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
