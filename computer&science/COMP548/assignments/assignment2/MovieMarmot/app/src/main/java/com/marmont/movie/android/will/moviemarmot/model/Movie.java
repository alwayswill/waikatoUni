package com.marmont.movie.android.will.moviemarmot.model;

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
public class Movie {
    public String Id;
    public String PosterURL;
    public String Title;
    public String MpaaRating;
    public float Rating;
    public String Summary;
    public String RTurl;

    public Movie(){

    }

    public Movie(String id, String PosterURL, String title, String mpaaRating, float rating, String summary) {
        this.Id = id;
        this.PosterURL = PosterURL;
        this.Title = title;
        this.MpaaRating = mpaaRating;
        this.Rating = rating;
        this.Summary = summary;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPosterURL() {
        return PosterURL;
    }

    public void setPosterURL(String posterURL) {
        PosterURL = posterURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMpaaRating() {
        return MpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        MpaaRating = mpaaRating;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        this.Rating = rating;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String toJSONString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("Title", Title.replace("&", "%26"));
            return json;
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

}
