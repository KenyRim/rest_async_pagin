package com.kenyrim.appdevtest.interfaces;

import com.google.gson.JsonObject;

public interface AuthCallback {
    void onCompleted(JsonObject result);
}