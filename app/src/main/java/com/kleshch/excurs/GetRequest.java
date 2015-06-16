package com.kleshch.excurs;

import android.os.AsyncTask;

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

        try{
            httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 401){
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
            } else if(httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
            }
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
}
