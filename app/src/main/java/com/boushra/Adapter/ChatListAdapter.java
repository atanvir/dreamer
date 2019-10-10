package com.boushra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.ChatDetailsActivity;
import com.boushra.Model.Data;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private Context context;
    private List<Data> chat;

    public ChatListAdapter(Context context,List<Data> chat)
    {
        this.context=context;
        this.chat = chat;
    }



    @NonNull
    @Override
    public ChatListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.adapter_chat_list,parent,false);
        return new ChatListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.MyViewHolder holder, int position) {
        if(chat.get(position).getForecasterData()!=null) {

            holder.user_nameText.setText(chat.get(position).getForecasterData().getName());
            Glide.with(context).load(chat.get(position).getForecasterData().getProfilePic()).into(holder.user_imageIM);
        }
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_nameText) TextView user_nameText;
        @BindView(R.id.user_imageIM) CircleImageView user_imageIM;
        @BindView(R.id.cardView) CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChatDetailsActivity.class);
                    intent.putExtra("chat_detail",chat.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
