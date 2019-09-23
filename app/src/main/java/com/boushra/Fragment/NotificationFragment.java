package com.boushra.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Adapter.NotificationAdapter;
import com.boushra.Model.Data;
import com.boushra.Model.Notification;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.NotificationUtils;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.notification_rv) RecyclerView notification_rv;
    private ProgressDailogHelper dailogHelper;
    List<Data> dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification,container,false);
        ButterKnife.bind(this,view);
        init();
        try {
            NotificationUtils.clearNotifications(getActivity());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        getNotificationListApi();


        return view;
    }

    private void getNotificationListApi() {
        if(new InternetCheck(getActivity()).isConnect())
        {
            dailogHelper.showDailog();
            RetroInterface api_service=RetrofitInit.getConnect().createConnection();
            Notification notification=new Notification();
            notification.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
            Call<Notification> call=api_service.getNotificationList(notification,SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.jwtToken));
            call.enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if(response.isSuccessful())
                    {
                      dailogHelper.dismissDailog();
                      Notification server_response=response.body();
                      if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                      {
                          dataList=server_response.getData();
                          settingAdapter(dataList);



                      }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                      {
                        Toast toast=Toast.makeText(getActivity(),server_response.getResponseMessage(),Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                      }
                    }
                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {

                }
            });



        }
        else
        {
            Toast toast=Toast.makeText(getActivity(),getString(R.string.check_internet),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    private void settingAdapter(List<Data> dataList) {
        notification_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        NotificationAdapter adapter=new NotificationAdapter(getActivity(),dataList);
        notification_rv.setAdapter(adapter);

    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        dailogHelper=new ProgressDailogHelper(getActivity(),"");
    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace, new NavigationHomeFragment()).commit();
                break;
        }
    }
}
