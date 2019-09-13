package com.boushra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.ForecasterDetailsActivity;
import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecasterDetailsAdapter extends RecyclerView.Adapter<ForecasterDetailsAdapter.MyViewHolder> {
    private Context context;

    public ForecasterDetailsAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ForecasterDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.adapter_forecasterdetails,parent,false);
        return new ForecasterDetailsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecasterDetailsAdapter.MyViewHolder holder, int position) {
//        holder.textView.setText();

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
