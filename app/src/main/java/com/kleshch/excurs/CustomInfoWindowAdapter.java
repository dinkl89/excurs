package com.kleshch.excurs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    String image, text;
    Context context;

    public CustomInfoWindowAdapter(Context context, String image, String text){
        this.context = context;
        this.image = image;
        this.text = text;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_info_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.info_window_image);
        TextView textView = (TextView) view.findViewById(R.id.info_window_text);

        textView.setText(text);
        MainActivity.loader.displayImage(image, imageView);

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
