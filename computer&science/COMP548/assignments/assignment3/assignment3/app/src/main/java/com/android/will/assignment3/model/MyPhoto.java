package com.android.will.assignment3.model;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.will.assignment3.utilities.GeoDegree;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 23/05/15.
 */
public class MyPhoto implements Parcelable{

    public static final String[] EXIF_TAGS = { ExifInterface.TAG_APERTURE,
            ExifInterface.TAG_DATETIME, ExifInterface.TAG_EXPOSURE_TIME,
            ExifInterface.TAG_FLASH, ExifInterface.TAG_FOCAL_LENGTH,
            "GPSAltitude", "GPSAltitudeRef", ExifInterface.TAG_GPS_DATESTAMP,
            ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF,
            ExifInterface.TAG_GPS_LONGITUDE,
            ExifInterface.TAG_GPS_LONGITUDE_REF,
            ExifInterface.TAG_GPS_PROCESSING_METHOD,
            ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_IMAGE_LENGTH,
            ExifInterface.TAG_IMAGE_WIDTH, "ISOSpeedRatings",
            ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL,
            ExifInterface.TAG_WHITE_BALANCE, };

    public String fileName;
    public String imageUri;
    public String GPSLatitudeRef;
    public String GPSLongitudeRef;
    public double GPSLatitude;
    public double GPSLongitude;
    public int ImageLength;
    public int ImageWidth;
    public String Make;
    public String Model;
    public String DateTime;

    public MyPhoto(){

    }

    public MyPhoto extractExif(ExifInterface exif){
        this.Model = exif.getAttribute(ExifInterface.TAG_MODEL);
        this.Make = exif.getAttribute(ExifInterface.TAG_MAKE);
        this.DateTime = exif.getAttribute(ExifInterface.TAG_DATETIME);
        this.ImageLength = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
        this.ImageLength = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
        GeoDegree geoDegree = new GeoDegree(exif);
        this.GPSLatitude = geoDegree.Latitude != 0f ? geoDegree.Latitude : 0;
        this.GPSLongitude = geoDegree.Longitude != 0f ? geoDegree.Longitude : 0;
        this.GPSLatitudeRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        this.GPSLongitudeRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        return this;
    }

    public String getExif(){
        String myExif="Exif information:\n\n";
        myExif += String.format("DateTime: %s\r\n", this.DateTime);
        myExif += String.format("Make: %s\r\n", this.Make);
        myExif += String.format("Model: %s\r\n", this.Model);
        myExif += String.format("Dimensions: %d x %d\r\n", this.ImageWidth, ImageLength);
        myExif += String.format("Compass bearing: %.4f %s, %.4f %s\r\n", GPSLatitude, GPSLatitudeRef, GPSLongitude, GPSLongitudeRef);

        return myExif;
    }



    public int describeContents() {
        return 0;
    }

    public MyPhoto(Parcel in) {

        this.fileName = in.readString();
        this.imageUri = in.readString();
        this.GPSLatitudeRef = in.readString();
        this.GPSLongitudeRef = in.readString();
        this.GPSLatitude = in.readDouble();
        this.GPSLongitude = in.readDouble();
        this.ImageLength = in.readInt();
        this.ImageWidth = in.readInt();
        this.Make = in.readString();
        this.Model = in.readString();
        this.DateTime = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeString(fileName);
        dest.writeString(imageUri);
        dest.writeString(GPSLatitudeRef);
        dest.writeString(GPSLongitudeRef);
        dest.writeDouble(GPSLatitude);
        dest.writeDouble(GPSLongitude);
        dest.writeInt(ImageLength);
        dest.writeInt(ImageWidth);
        dest.writeString(Make);
        dest.writeString(Model);
        dest.writeString(DateTime);


    }

    public static final Parcelable.Creator<MyPhoto>	CREATOR	= new Parcelable.Creator<MyPhoto>() {
        public MyPhoto createFromParcel(Parcel in) {
            return new MyPhoto(in);
        }

        public MyPhoto[] newArray(int size) {
            return new MyPhoto[size];
        }
    };

    public boolean checkPhoto(){
        if (this.GPSLatitude != 0 && this.GPSLongitude != 0){
            return true;

        }
        return false;
    }

    @Override
    public boolean equals(Object o) {

        boolean isSame = false;
        Log.d(getClass().getName(), "o equals:"+((MyPhoto) o).fileName);
        Log.d(getClass().getName(), "this:"+this.fileName);
        if (((MyPhoto) o).fileName.equals(this.fileName)){
            isSame = true;
        }
        return isSame;
    }
}
