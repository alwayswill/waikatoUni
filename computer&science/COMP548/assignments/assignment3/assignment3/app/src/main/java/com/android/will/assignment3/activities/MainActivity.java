package com.android.will.assignment3.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.will.assignment3.R;
import com.android.will.assignment3.adapters.PopupAdapter;
import com.android.will.assignment3.model.MyPhoto;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    static final String TAG = "MainActivity";
    private static final String KEY_PHOTO_LIST = "keyPhotoList";
    private static final String KEY_IMAGE_LIST = "keyImageList";
    private static final String KEY_CURRENT_POSITION = "keyCurrnetPosition";
    public GoogleMap mMap;

    private HashMap<String, Uri> mImages =new HashMap<String, Uri>();
    private PopupAdapter mPopupAdapter;
    public LatLng mHamilton = new LatLng(-37.7833, 175.2833);
    public CameraPosition mCurrentPosition;

    private ArrayList<MyPhoto> mPhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();


        if (savedInstanceState != null) {
            Log.d(TAG, "onActivityCreated() : getting photos from savedInstanceState");
            mPhotoList = savedInstanceState.getParcelableArrayList(KEY_PHOTO_LIST);
            mImages = (HashMap<String, Uri>) savedInstanceState.getSerializable(KEY_IMAGE_LIST);
            mCurrentPosition = savedInstanceState.getParcelable(KEY_CURRENT_POSITION);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_IMAGE_LIST, mImages);
        outState.putParcelableArrayList(KEY_PHOTO_LIST, mPhotoList);

        mCurrentPosition = mMap.getCameraPosition();
        outState.putParcelable(KEY_CURRENT_POSITION, mCurrentPosition);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(TAG, "onMapReady");
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mCurrentPosition != null){
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(mCurrentPosition.target);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(mCurrentPosition.zoom);

            map.moveCamera(center);
            map.animateCamera(zoom);

        }else{
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(mHamilton);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);

            map.moveCamera(center);
            map.animateCamera(zoom);
        }

        Log.d(TAG, "CameraMaxZoom" + map.getMaxZoomLevel());
        Log.d(TAG, "CameraPosition" + map.getCameraPosition().target);



        mPopupAdapter = new PopupAdapter(this,
                getLayoutInflater(),
                mImages);
        map.setInfoWindowAdapter(mPopupAdapter);
        map.setOnInfoWindowClickListener(this);
        if (mPhotoList.isEmpty()){
            initIntent();
        }
        addMarker();
    }

    public void addMarker(){
        Log.d(TAG, "addMarker");
        if (mPhotoList != null){
            for (MyPhoto mPhoto : mPhotoList){
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mPhoto.GPSLatitude, mPhoto.GPSLongitude))
                        .title(mPhoto.fileName).snippet(mPhoto.getExif()));

                mImages.put(marker.getId(),
                        Uri.parse(mPhoto.imageUri));
                mPopupAdapter.images = mImages;
            }
        }
    }

    private MyPhoto handlePhoto(Uri imageUri){
        Log.d(TAG, "handlePhoto");
        try{
            String filePath = getRealPathFromURI(imageUri);
            ExifInterface exif = new ExifInterface(filePath);

            MyPhoto mPhoto = new MyPhoto().extractExif(exif);
            File photoFile = new File(filePath);
            mPhoto.imageUri = imageUri.toString();
            mPhoto.fileName = photoFile.getName();
            if(mPhoto.checkPhoto() && !mPhotoList.contains(mPhoto)){
                mPhotoList.add(mPhoto);
                Log.d(TAG, "mPhotoList:"+mPhotoList.size());
                return mPhoto;
            }else{
                Toast.makeText(this, "Ignore "+mPhoto.fileName+" because of no location tag or duplicated", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ignore:"+photoFile.getName()+": no location tag or exists");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void handleSendImage(Intent intent) {
        Log.d(TAG, "handleSendImage");
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        handlePhoto(imageUri);
        addMarker();
    }



    private void handleSendMultipleImages(Intent intent) {
        Log.d(TAG, "handleSendMultipleImages");

        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            for(Uri imageUri : imageUris){
                if (imageUri != null){
                    handlePhoto(imageUri);
                }
            }
        }
        addMarker();

    }



    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);
        String action = intent.getAction();
        Log.d(TAG, "onNewIntent:action" + action);
        String type = intent.getType();
        if(Intent.ACTION_SEND.equals(action) && type != null){
            handleSendImage(intent);
        }else if(Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            handleSendMultipleImages(intent);
        }
    }

    protected void initIntent() {
        Log.d(TAG, "initIntent");
        Intent intent = getIntent();
        String action = intent.getAction();
        Log.d(TAG, "initIntent:action"+action);
        String type = intent.getType();
        if(Intent.ACTION_SEND.equals(action) && type != null){
            handleSendImage(intent);
        }else if(Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            handleSendMultipleImages(intent);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }



    //debug;

    private void ShowExif(ExifInterface exif)
    {
        String myAttribute="Exif information ---\n";

        for (String tag : MyPhoto.EXIF_TAGS) {
            myAttribute += getTagString(tag, exif);
        }


        Log.d(TAG, "Exif:" + myAttribute);
    }

    private String getTagString(String tag, ExifInterface exif)
    {
        return(tag + " : " + exif.getAttribute(tag) + "\n");
    }


}
