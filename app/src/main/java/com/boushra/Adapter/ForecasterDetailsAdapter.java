package com.boushra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.ForecasterDetailsActivity;
import com.boushra.Model.Data;
import com.boushra.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecasterDetailsAdapter extends RecyclerView.Adapter<ForecasterDetailsAdapter.MyViewHolder> {
    private Context context;
    private List<Data> dataList;

    public ForecasterDetailsAdapter(Context context,List<Data> dataList) {
        this.context=context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public ForecasterDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.adapter_forecasterdetails,parent,false);
        return new ForecasterDetailsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecasterDetailsAdapter.MyViewHolder holder, int position) {
        if(dataList.get(position).getRatingData().getUserData()!=null) {
            Glide.with(context).load(dataList.get(position).getRatingData().getUserData().getProfilePic()).into(holder.profilepic_iv);
            holder.name_txt.setText(dataList.get(position).getRatingData().getUserData().getName());
            holder.comment_txt.setText(dataList.get(position).getRatingData().getRatingMessage());

            String getDate = dataList.get(position).getRatingData().getCreatedAt();
            String server_format = getDate;    //server comes format ?
            String server_format1 = "2019-04-04T13:27:36.591Z";    //server comes format ?
            String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            try {
                Date date = sdf.parse(server_format);
                System.out.println(date);
                String your_format = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
                System.out.println(your_format);
                String[] splitted = your_format.split(" ");
                System.out.println(splitted[1]);    //The second part of the splitted string, i.e time
                // Now you can set the TextView here
                holder.date_txt.setText(String.valueOf(splitted[0]));
            } catch (Exception e) {
                System.out.println(e.toString()); //date format error
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profilepic_iv) ImageView profilepic_iv;
        @BindView(R.id.name_txt) TextView name_txt;
        @BindView(R.id.date_txt) TextView date_txt;
        @BindView(R.id.comment_txt) TextView comment_txt;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
