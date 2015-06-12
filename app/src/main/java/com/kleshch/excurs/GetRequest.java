package com.kleshch.excurs;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GetRequest extends AsyncTask<String, Void, String> {
    String result;

    public GetRequest(){}

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(params[0]);
        HttpResponse httpResponse;
        Log.d("222", "Starting execute");

        try{
            Log.d("222", "inside try");
            httpResponse = httpClient.execute(httpGet);
            Log.d("22222222", "status code = " + httpResponse.getStatusLine().getStatusCode());
            if(httpResponse.getStatusLine().getStatusCode() == 401){
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
            } else if(httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
            }
            Log.d("info", result);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(String page){      //delete on release!
        Log.d("PostExecute getreq", "Completed");
        Log.d("222", "onPostExecute: " + page);
    }
}
