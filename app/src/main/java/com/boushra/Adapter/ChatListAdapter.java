package com.boushra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.ChatActivity;
import com.boushra.Model.Chat;
import com.boushra.Model.Data;
import com.boushra.R;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private Context context;
    private List<Chat> chat;

    public ChatListAdapter(Context context,List<Chat> chat)
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
        holder.user_nameText.setText(chat.get(position).getUser_name());
        holder.user_imageIM.setImageResource(chat.get(position).getUser_image());

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_nameText)
        TextView user_nameText;
        @BindView(R.id.user_imageIM)
        ImageView user_imageIM;
        @BindView(R.id.cardView)
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChatActivity.class);
                    context.startActivity(intent);

                }
            });
        }
    }
}
