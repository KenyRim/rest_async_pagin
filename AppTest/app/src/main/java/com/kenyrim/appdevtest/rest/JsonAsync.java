package com.kenyrim.appdevtest.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kenyrim.appdevtest.model.Model;
import com.kenyrim.appdevtest.interfaces.AsyncCallback;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonAsync extends AsyncTask<Void, Integer, String> {

    private final AsyncCallback mListener;
    private String jsonData, code, page;
    private ArrayList<Model> resultArr = new ArrayList<>();


    public JsonAsync(String code, String page, AsyncCallback listener) {
        mListener = listener;
        this.code = code;
        this.page = page;
    }


    @Override
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(Void... params) {

        try {

            Log.e("async code",code);
            HttpUrl.Builder urlBuilder = HttpUrl.parse(Api.DATA).newBuilder();
            urlBuilder.addQueryParameter("code",code);
            urlBuilder.addQueryParameter("p",page);
            String url = urlBuilder.build().toString();
            Log.e("urlBuilder",url);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert responses != null;
            assert responses.body() != null;
            jsonData = responses.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("responsesyyy",e.getMessage());
        }


        return jsonData;

    }

    @Override
    protected void onPostExecute(String items){

        if (mListener != null) {
            JsonParser parser = new JsonParser();
            JsonObject root;
            if (items != null) {
                root = (JsonObject) parser.parse(items);

                Log.e("onPostExecute",root.get("status").getAsString());

                Gson gson = new Gson();
                resultArr = gson.fromJson(root.get("data").getAsJsonArray(),
                        new TypeToken<ArrayList<Model>>() {
                        }.getType());

            }
            mListener.onCompleted(resultArr);
        }
    }
}