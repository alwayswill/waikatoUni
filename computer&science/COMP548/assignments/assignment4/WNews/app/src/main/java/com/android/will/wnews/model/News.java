package com.android.will.wnews.model;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 13/05/15.
 */
public class News implements Parcelable{
    public Integer id;
    public String title;
    public String picURL;
    public String content;
    public String timestamp;
    public String orginalUrl;


    public News(){

    }

    private News(Parcel in) {

        this.id = in.readInt();
        this.title = in.readString();
        this.picURL = in.readString();
        this.content = in.readString();
        this.timestamp = in.readString();
        this.orginalUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(picURL);
        dest.writeString(content);
        dest.writeString(timestamp);
        dest.writeString(orginalUrl);
    }

    public static final Parcelable.Creator<News>	CREATOR	= new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };

}
