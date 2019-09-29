package com.kenyrim.appdevtest.fragments;

import android.os.Bundle;

import com.google.gson.JsonObject;
import com.kenyrim.appdevtest.rest.Api;
import com.kenyrim.appdevtest.R;
import com.kenyrim.appdevtest.interfaces.AuthCallback;
import com.kenyrim.appdevtest.rest.AuthAsync;
import com.kenyrim.appdevtest.utils.Internet;
import com.kenyrim.appdevtest.utils.SPHelper;
import com.kenyrim.appdevtest.utils.SnackBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginFragment extends Fragment {

    private EditText et_password, et_login;
    private Button btn_login;
    private RelativeLayout rootView;
    private String login, password;
    private SnackBar snackBar = new SnackBar();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable final ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login, container, false);

        et_password = v.findViewById(R.id.et_password);
        et_login = v.findViewById(R.id.et_login);

        et_password.setText("123");
        et_login.setText("test");

        btn_login = v.findViewById(R.id.btn_login);

        rootView = v.findViewById(R.id.root_view);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Internet.isOnline(getContext())) {
                    checkFields();
                }else {
                    snackBar.displaySnackbar("No Internet Connection",getActivity());
                }
            }
        });

        return v;
    }

    public void checkFields(){

        login = et_login.getText().toString();
        password = et_password.getText().toString();
        if (!login.isEmpty()&&!password.isEmpty()){
            Log.e("checkFields", "success");
            startLogin();
        }else {
            snackBar.displaySnackbar("Please fill both fields",getActivity());

        }
    }


    private void startLogin() {

        new AuthAsync(Api.AUTH,login,password,new AuthCallback() {
            @Override
            public void onCompleted(JsonObject object) {
                if (object != null) {
                    String status = object.get("status").getAsString();

                    Log.e("xcode_async",object.get("code").getAsString());
                    if (status.equals("ok")) {

                        new SPHelper().saveString(getContext(),"CODE",object.get("code").getAsString());

                        ListFragment fragment = new ListFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.listContainer, fragment, "LIST_FRAGMENT");
                        transaction.commit();

                    } else {

                        snackBar.displaySnackbar("Invalid Password or Login",getActivity());
                    }
                }else {
                    snackBar.displaySnackbar("Sorry, something went wrong. Please try again",getActivity());
                }
            }

        }).execute();

    }

}
