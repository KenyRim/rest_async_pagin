package com.kenyrim.appdevtest.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kenyrim.appdevtest.R;
import com.kenyrim.appdevtest.adapter.AdapterRecycler;
import com.kenyrim.appdevtest.interfaces.AsyncCallback;
import com.kenyrim.appdevtest.listener.PaginationListener;
import com.kenyrim.appdevtest.interfaces.PagenCallback;
import com.kenyrim.appdevtest.model.Model;
import com.kenyrim.appdevtest.rest.JsonAsync;
import com.kenyrim.appdevtest.utils.SPHelper;

import java.util.ArrayList;

public class ListFragment extends Fragment implements AdapterRecycler.OnItemClickListener{

    private int scrollPos;
    private ArrayList<Model> myArray = new ArrayList<>();
    private AdapterRecycler adapter;

    private int page = 1;

    private ProgressDialog dialog;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scrollPos", scrollPos);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            scrollPos = savedInstanceState.getInt("scrollPos");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_list_layout, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        adapter = new AdapterRecycler(getActivity(),getContext(), myArray,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        dialog = ProgressDialog.show(getContext(), "","Loading..Wait.." , true);

        recyclerView.addOnScrollListener(new PaginationListener(new PagenCallback() {
            @Override
            public void onListStateChanged(PaginationListener.State state) {
                page = page+1;
                getArrays(page);

            }

        }));
        recyclerView.setLayoutManager(layoutManager);


        if (savedInstanceState != null){
            recyclerView.scrollTo(0,scrollPos);
        }


        getArrays(page);

        return v;

    }

    private void getArrays(final int page){

        dialog.show();
        String code = new SPHelper().getString(getContext(),"CODE");
        Log.e("xcode_frag",code);

        new JsonAsync(code,String.valueOf(page),new AsyncCallback() {
            @Override
            public void onCompleted(ArrayList<Model> arr) {


                if (arr.size() > 0) {
                    dialog.dismiss();
                    if (myArray.size() == 0) {
                        myArray.addAll(arr);
                        adapter.notifyDataSetChanged();

                    } else {

                        if (page <= 8) {
                            for (int i = 0; i < arr.size(); i++) {
                                adapter.add(myArray.size() - 1 + 1,arr.get(i));
                            }
                        }
                    }
                }else{
                    getArrays(page);
                }
            }

        }).execute();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(),""+ myArray.get(position),Toast.LENGTH_SHORT).show();

        MyDialog dialog = new MyDialog();
        Bundle b = new Bundle();
        b.putParcelableArrayList("MY_ARRAY", myArray);
        b.putInt("POSITION", position);
        dialog.setArguments(b);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        dialog.show(fragmentManager,null);

    }
}


