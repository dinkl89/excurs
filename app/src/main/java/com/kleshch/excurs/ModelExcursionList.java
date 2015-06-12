package com.kleshch.excurs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ModelExcursionList {

    private String stringToParse;
    private ArrayList<ExcursionListItem> list;
    private Context context;
    private ProgressDialog dialog;
    private View view;

    public ModelExcursionList(final Activity activity){

        this.context = activity.getApplicationContext();

        view = new View(activity);

        dialog = new ProgressDialog(activity);

        dialog.show();

        getResponse();
    }

    private void buildList(){
        list = new ArrayList<>(getListLength());
        list.clear();

        for (int i=0; i<getListLength(); i++){
            Log.d("111", "i = " + i);
            ExcursionListItem listItem = item(i);
            list.add(listItem);
        }
    }

    public ArrayList<ExcursionListItem> getList() {
        return list;
    }

    private void getResponse(){
        GetRequest request = new GetRequest();
        request.execute(context.getResources().getString(R.string.url_address) + context.getResources().getString(R.string.action_get_list) + "ru");

        try {
            stringToParse = request.get();
            Log.d("111", stringToParse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        buildList();
        dialog.dismiss();
        /*new Thread(){
            public void run() {
                try {
                    sleep(500);
                    GetRequest request = new GetRequest();
                    request.execute(context.getResources().getString(R.string.url_address) + context.getResources().getString(R.string.action_get_list) + "ru");

                    try {
                        stringToParse = request.get();
                        Log.d("111", stringToParse);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            buildList();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

    }

    private int getListLength(){
        try {
            JSONArray array = new JSONArray(stringToParse);
            Log.d("111", "Array length: " + array.length());
            return array.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private ExcursionListItem item(int position){
        ExcursionListItem item;
        int itemId;
        String name, image;

        if (getListLength()>=0){
            try {
                JSONArray array = new JSONArray(stringToParse);
                JSONObject object = array.getJSONObject(position);

                itemId = tryParse(object.getString("id"));
                name = object.getString("name");
                image = object.getString("image");

                Log.d ("111", "itemId: " + itemId + ", name = " + name + ", image: " + image);

                item  = new ExcursionListItem(itemId, name, image);

                return item;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private int tryParse(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return 0;
    }
}
