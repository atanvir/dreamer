package com.boushra.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.MyWalletActivity;
import com.boushra.Adapter.ChatListAdapter;
import com.boushra.Model.Chat;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationChatFragment extends Fragment {
    @BindView(R.id.chatRV) RecyclerView chatRV;
    @BindView(R.id.pointtxt) TextView pointtxt;
    List<Chat> chats;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat_navigation,container,false);
        ButterKnife.bind(this,view);
        pointtxt.setOnClickListener(this::OnClick);
        pointtxt.setText(""+ SharedPreferenceWriter.getInstance(getActivity()).getInt(GlobalVariables.totalPoints));
        SettingListvalues();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        chatRV.setLayoutManager(layoutManager);
        ChatListAdapter listAdapter=new ChatListAdapter(getActivity(), chats);
        chatRV.setAdapter(listAdapter);


        return view;
    }


    private void SettingListvalues() {
        chats =new ArrayList<>();
        chats.add(new Chat(R.drawable.img_e,"John Deo"));
        chats.add(new Chat(R.drawable.img_g,"John Deo"));
        chats.add(new Chat(R.drawable.img_e,"John Deo"));
        chats.add(new Chat(R.drawable.img_g,"John Deo"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @OnClick({R.id.notification_im})
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.notification_im:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NotificationFragment()).commit();
                break;

            case R.id.pointtxt:
                Intent intent=new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent);

                break;

        }
    }

}
