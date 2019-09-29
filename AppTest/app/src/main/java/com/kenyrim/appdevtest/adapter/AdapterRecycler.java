package com.kenyrim.appdevtest.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kenyrim.appdevtest.model.Model;
import com.kenyrim.appdevtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    private Context context;
    private ArrayList<Model> data;
    private FragmentActivity activity;
    private OnItemClickListener onItemClickListener;

    public AdapterRecycler(FragmentActivity activity,Context context,ArrayList<Model> data,OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        Model model = data.get(position);
        holder.title.setText(model.getName());

        Picasso.with(context)
                .load("https://web.getmonero.org/press-kit/symbols/monero-symbol-800.png")
                .into(holder.image);

    }

    public void add(int position, Model item) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView image;

        ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_item);
            image = v.findViewById(R.id.iv_item);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}