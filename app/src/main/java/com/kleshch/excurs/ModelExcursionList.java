package com.kleshch.excurs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ModelExcursionList {

    private String stringToParse;
    private ArrayList<ExcursionListItem> list;
    private Context context;
    private IFace iFace;
    private ProgressDialog dialog;

    public ModelExcursionList(final Activity activity){

        this.context = activity.getApplicationContext();
        this.iFace = (IFace)activity;

        dialog = new ProgressDialog(activity);

        dialog.show();

        getResponse();
    }

    private void buildList(){
        list = new ArrayList<>(getListLength());
        list.clear();

        for (int i=0; i<getListLength(); i++){
            ExcursionListItem listItem = item(i);
            list.add(listItem);
        }
    }

    public ArrayList<ExcursionListItem> getList() {
        return list;
    }

    private void getResponse(){
        GetRequest request = new GetRequest();
        String locale = iFace.isEng()?"en":"ru";
        request.execute(context.getResources().getString(R.string.url_address) + context.getResources().getString(R.string.action_get_list) + locale);

        try {
            stringToParse = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        buildList();
        dialog.dismiss();
    }

    private int getListLength(){
        try {
            JSONArray array = new JSONArray(stringToParse);
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
