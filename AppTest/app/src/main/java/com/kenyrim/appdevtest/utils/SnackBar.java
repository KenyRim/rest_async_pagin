package com.kenyrim.appdevtest.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.kenyrim.appdevtest.R;

public class SnackBar {


    public void displaySnackbar(String message,Activity activity) {
        final TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), message, TSnackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        ViewGroup.LayoutParams params = snackBarView.getLayoutParams();
        params.height = 150;
        snackBarView.setLayoutParams(params);
        snackBarView.setBackgroundColor(activity.getResources().getColor(R.color.snackBar));
        TextView textView = snackBarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
