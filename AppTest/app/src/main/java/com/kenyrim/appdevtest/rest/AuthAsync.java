package com.kenyrim.appdevtest.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kenyrim.appdevtest.interfaces.AuthCallback;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthAsync extends AsyncTask<Void, Integer, String> {

    private final AuthCallback listener;
    private String jsonData, api, login, password;


    public AuthAsync(String api, String login, String password, AuthCallback listener) {
        this.listener = listener;
        this.api = api;
        this.login = login;
        this.password = password;
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(Void... params) {


        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(api).newBuilder();
            urlBuilder.addQueryParameter("username",login);
            urlBuilder.addQueryParameter("password",password);
            String url = urlBuilder.build().toString();
            Log.e("urlBuilder",url);

            OkHttpClient.Builder client = new OkHttpClient().newBuilder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response responses = null;

            try {
                responses = client.build().newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert responses != null;
            assert responses.body() != null;
            jsonData = responses.body().string();

        } catch (Exception e) {
            Log.e("JSONException",Objects.requireNonNull(e.getMessage()));
        }

        return jsonData;

    }


    @Override
    protected void onPostExecute(String items){
         if (listener != null) {

             JsonParser parser = new JsonParser();
             JsonObject object = null;
             if (items != null) {
                 object = (JsonObject) parser.parse(items);
             }

              listener.onCompleted(object);
         } 
    }


}