package com.kleshch.excurs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Зайка on 05.05.2015.
 */
public class FragmentMap extends MapFragment {

    private LatLng latLng;
    String image, text;

    public FragmentMap(){
        super();
    }

    public static FragmentMap newInstance(LatLng latLng, String image, String text){
        FragmentMap fragmentMap = new FragmentMap();
        fragmentMap.latLng = latLng;
        fragmentMap.text = text;
        fragmentMap.image = image;
        return fragmentMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        initMap();
        return v;
    }

    private void initMap() {

        MarkerOptions options = new MarkerOptions().position(latLng);

        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);

        getMap().setMyLocationEnabled(true);
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        getMap().addMarker(options);
        getMap().setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity().getApplicationContext(),image, text));

        getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
                //.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.));
    }
}
