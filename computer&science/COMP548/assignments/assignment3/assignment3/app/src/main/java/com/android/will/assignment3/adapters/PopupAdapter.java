package com.android.will.assignment3.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.will.assignment3.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import java.util.HashMap;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 23/05/15.
 */
public class PopupAdapter implements GoogleMap.InfoWindowAdapter {

    private View popup=null;
    private LayoutInflater inflater=null;
    public HashMap<String, Uri> images=null;
    private Context ctxt=null;
    private int iconWidth=-1;
    private int iconHeight=-1;
    private Marker lastMarker=null;

    public PopupAdapter(Context ctxt, LayoutInflater inflater,
                        HashMap<String, Uri> images) {
        this.ctxt=ctxt;
        this.inflater=inflater;
        this.images=images;

        iconWidth=
                ctxt.getResources().getDimensionPixelSize(R.dimen.icon_width);
        iconHeight=
                ctxt.getResources().getDimensionPixelSize(R.dimen.icon_height);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null) {
            popup=inflater.inflate(R.layout.popup, null);
        }

        if (lastMarker == null
                || !lastMarker.getId().equals(marker.getId())) {
            lastMarker=marker;

            TextView tv=(TextView)popup.findViewById(R.id.title);

            tv.setText(marker.getTitle());
            tv=(TextView)popup.findViewById(R.id.snippet);
            tv.setText(marker.getSnippet());

            Uri image=images.get(marker.getId());
            ImageView icon=(ImageView)popup.findViewById(R.id.icon);

            if (image == null) {
                icon.setVisibility(View.GONE);
            }
            else {
                Picasso.with(ctxt).load(image).resize(iconWidth, iconHeight)
                        .centerCrop().noFade()
                        .placeholder(R.drawable.placeholder)
                        .into(icon, new MarkerCallback(marker));
            }
        }

        return(popup);
    }

    static class MarkerCallback implements Callback {
        Marker marker=null;

        MarkerCallback(Marker marker) {
            this.marker=marker;
        }

        @Override
        public void onError() {
            Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()) {
                marker.showInfoWindow();
            }
        }
    }
}
