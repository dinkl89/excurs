package com.kleshch.excurs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FragmentFollowToPoint extends Fragment {

   //final String image = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Nicholi_Chapel_in_Novosibirsk.jpg/200px-Nicholi_Chapel_in_Novosibirsk.jpg";
    final String text = "Какой-то текст об этой часовне. Тут наверно должно быть че то интересное, да?";

    private int idNum;

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
        final String image = point.getImage();

        tvAddress.setText(address);

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show map fragment, coordinates to go
                FragmentMap fragmentMap = FragmentMap.newInstance(latLng, image, shortInfo);
                getFragmentManager().beginTransaction()
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
                        .replace(R.id.fragment_container, info)
                        .addToBackStack("point_info")
                        .commit();
            }
        });

        return view;
    }


}