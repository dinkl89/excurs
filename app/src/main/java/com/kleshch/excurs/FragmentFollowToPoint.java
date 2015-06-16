package com.kleshch.excurs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class FragmentFollowToPoint extends Fragment {

    private int idNum;
    private String image;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_point, container, false);

        PointsList pointsList = PointsList.getInstance(getActivity());

        Button btnShowMap = (Button) view.findViewById(R.id.follow_show_map);
        final Button btnCheck = (Button) view.findViewById(R.id.follow_check);
        TextView tvAddress = (TextView) view.findViewById(R.id.follow_address);

        if(getArguments() != null) {
            idNum = getArguments().getInt("id");
        }

        Point point = pointsList.getPoint(idNum);
        String address = point.getAddress();
        final String shortInfo = point.getShortInfo();
        final LatLng latLng = point.getCoordinates();
        image = point.getImage();
        if (!image.contains("http://")) {
            image = getResources().getString(R.string.url_domain_name) +
                    getResources().getString(R.string.images) + image;
        }

        tvAddress.setText(address);

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show map fragment, coordinates to go
                FragmentMap fragmentMap = FragmentMap.newInstance(latLng, image, shortInfo);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
                        .replace(R.id.fragment_container, fragmentMap)
                        .addToBackStack("point_map")
                        .commit();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make place checker, if check_ok:
                FragmentPointInfo info = new FragmentPointInfo();
                Bundle bundle = new Bundle();
                bundle.putInt("id", idNum);
                info.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
                        .replace(R.id.fragment_container, info)
                        .addToBackStack("point_info")
                        .commit();
            }
        });

        return view;
    }


}
