package com.kleshch.excurs;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PointsList {

    private ArrayList<Point> list;

    private String stringToParse;

    private Activity activity;

    private static PointsList ourInstance = null;

    public static PointsList getInstance(Activity activity) {
        if (ourInstance == null){
            ourInstance = new PointsList(activity);
        }
        return ourInstance;
    }

    private PointsList(Activity activity) {
        list = new ArrayList<>();
        this.activity = activity;
    }

    public ArrayList<Point> getPointsList(int id){
        if (list != null){
            return list;
        } else {
            return null;
        }
    }

    public int getListLength(){
        return list.size();
    }

    public Point getPoint(int id){
        if (list != null && list.size()>id) {
            return list.get(id);
        } else {
            return null;
        }
    }

    private void getPointsListRequest(int idNum) {
        GetRequest request = new GetRequest();
        request.execute(activity.getResources().getString(R.string.url_address) + activity.getResources().getString(R.string.action_get_points_list) + String.valueOf(idNum));

        try {
            stringToParse = request.get();
            Log.d("111", stringToParse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void getPointRequest(int idNum) {
        GetRequest request = new GetRequest();
        request.execute(activity.getResources().getString(R.string.url_address) + activity.getResources().getString(R.string.action_get_info_point) + String.valueOf(idNum));

        try {
            stringToParse = request.get();
            Log.d("111", stringToParse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void refreshList(int id){
        getPointsListRequest(id);
        responseParse();
    }

    private void responseParse(){

        if (list != null){
            list.clear();
        } else {
            list = new ArrayList<>();
        }

        try {
            JSONArray array = new JSONArray(stringToParse);
            int size = array.length();
            for (int i=0; i< size; i++){
                JSONObject object = array.getJSONObject(i);
                String pointName = object.getString("name");
                String pointInfo = object.getString("info");
                String pointImage = object.getString("image");
                if (!pointImage.contains("http://")) {
                    pointImage = activity.getResources().getString(R.string.url_domain_name) +
                            activity.getResources().getString(R.string.images) + pointImage;
                }
                int pointId = tryParse(object.getString("id"));

                String pointAddress = object.getString("address");
                Double lat = tryDouble(object.getString("lat"));
                Double lng = tryDouble(object.getString("lng"));
                LatLng latLng = new LatLng(lat, lng);
                String story = object.getString("story");
                String question = object.getString("question");
                String answer = object.getString("answer");

                Point point = new Point(pointId, pointName, pointInfo, pointAddress, pointImage, latLng, story, question, answer);
                list.add(point);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int tryParse(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return 0;
    }

    private double tryDouble(String input){
        try{
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
