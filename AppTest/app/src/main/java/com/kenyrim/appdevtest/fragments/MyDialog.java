package com.kenyrim.appdevtest.fragments;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



import com.kenyrim.appdevtest.R;
import com.kenyrim.appdevtest.model.Model;


import java.util.ArrayList;


public class MyDialog extends DialogFragment{

    ArrayList<Model> list;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_description,null);


        Bundle b = getArguments();
        assert b != null;
        list = b.getParcelableArrayList("MY_ARRAY");
        position = b.getInt("POSITION");

        TextView tv_full = view.findViewById(R.id.tv_full);

        assert list != null;
        tv_full.setText(
                new StringBuilder()
                        .append(list.get(position).getId())
                        .append("\n")
                        .append(list.get(position).getName())
                        .append("\n").append(list.get(position).getCountry())
                        .append("\n").append(list.get(position).getLat())
                        .append("\n").append(list.get(position).getLon())
                        .append("\n").toString()
        );

        ImageView btn_back = view.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        WebView webview = view.findViewById(R.id.mapView);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl("http://maps.google.com/maps/place?q="
                +list.get(position).getLat()
                + ","+list.get(position).getLon()
                +"&ll="+list.get(position).getLat()
                + ","+list.get(position).getLon()
        +"&z=15");

        return view;
    }


}