package com.kenyrim.appdevtest.interfaces;

import com.kenyrim.appdevtest.model.Model;

import java.util.ArrayList;

public interface AsyncCallback {
    void onCompleted(ArrayList<Model> catsDogs);

}