package com.boushra.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.MyWalletActivity;
import com.boushra.Adapter.ChatListAdapter;
import com.boushra.Model.Chat;
import com.boushra.Model.Chatlist;
import com.boushra.Model.Data;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationChatFragment extends Fragment {
    @BindView(R.id.chatRV) RecyclerView chatRV;
    @BindView(R.id.pointtxt) TextView pointtxt;
    List<Data> chats;
    ProgressDailogHelper dailogHelper;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat_navigation,container,false);
        ButterKnife.bind(this,view);

        pointtxt.setOnClickListener(this::OnClick);
        pointtxt.setText(""+ SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.totalPoints));
        dailogHelper=new ProgressDailogHelper(getActivity(),"");
        getchatListApi();


        return view;
    }

    private void getchatListApi() {
        if(new InternetCheck(getActivity()).isConnect())
        {
            dailogHelper.showDailog();
            Chatlist chatlist=new Chatlist();
            chatlist.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            Call<Chatlist> call= api_service.getchatList(chatlist);
            call.enqueue(new Callback<Chatlist>() {
                @Override
                public void onResponse(Call<Chatlist> call, Response<Chatlist> response) {
                    if(response.isSuccessful())
                    {
                       Chatlist server_resposne= response.body();
                        dailogHelper.dismissDailog();

                        if(server_resposne.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                       {
                           chats=server_resposne.getData();
                           LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
                           chatRV.setLayoutManager(layoutManager);
                           ChatListAdapter listAdapter=new ChatListAdapter(getActivity(), chats);
                           chatRV.setAdapter(listAdapter);



                       }else if(server_resposne.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                           {
                               Toast.makeText(getActivity(), server_resposne.getResponseMessage(), Toast.LENGTH_SHORT).show();
                           }


                    }
                }

                @Override
                public void onFailure(Call<Chatlist> call, Throwable t) {
                    Log.e("failure",t.getMessage());


                }
            });


        }
        else
        {
            Toast toast=Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

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
