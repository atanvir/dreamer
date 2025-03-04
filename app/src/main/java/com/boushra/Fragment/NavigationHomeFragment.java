package com.boushra.Fragment;


import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.boushra.Activity.LoginActivity;
import com.boushra.Activity.MyWalletActivity;
import com.boushra.Activity.PsychologicalListFragment;
import com.boushra.Activity.StoreActivity;
import com.boushra.Adapter.ImageSliderAdapter;
import com.boushra.Model.BannerList;
import com.boushra.Model.Data;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationHomeFragment extends Fragment {
    @BindView(R.id.notification_im) ImageView notification_im;
    @BindView(R.id.image_viewpager) ViewPager image_viewpager;
    @BindView(R.id.logo_iv) ImageView logo_iv;
    @BindView(R.id.pointtxt) TextView pointtxt;
    int currentposition=0;
    Timer timer;
    List<Data> images;
    String lagncode="";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_navigation,container,false);
        ButterKnife.bind(this,view);
        lagncode=SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.langCode);
        if(lagncode.equalsIgnoreCase("ar"))
        {
            logo_iv.setImageResource(R.drawable.logo_heaader_ar);
        }
        notification_im.setOnClickListener(this::onClick);
        pointtxt.setOnClickListener(this::onClick);
        pointtxt.setText(""+SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.totalPoints));
        getBannerListApi();
        return view;
    }

    private void getBannerListApi() {
        if(new InternetCheck(getActivity()).isConnect())
        {
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            Call<BannerList> call=api_service.getBannerList();
            call.enqueue(new Callback<BannerList>() {
                @Override
                public void onResponse(Call<BannerList> call, Response<BannerList> response) {
                    if(response.isSuccessful())
                    {
                        BannerList server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                                createSlideShow();
                                images=server_response.getData();
                                ImageSliderAdapter adapter=new ImageSliderAdapter(getActivity(),server_response.getData());
                                image_viewpager.setAdapter(adapter);


                        }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE)) {
                            if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                Toast.makeText(getActivity(), getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                SharedPreferenceWriter.getInstance(getActivity()).clearPreferenceValues();
                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            // Log.w(TAG, "getInstanceId failed", task.getException());
                                            return;
                                        }

                                        String auth_token = task.getResult().getToken();
                                        Log.w("firebaese", "token: " + auth_token);
                                        SharedPreferenceWriter.getInstance(getActivity()).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                    }
                                });
                            } else {
                                Toast toast = Toast.makeText(getActivity(), server_response.getResponseMessage(), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BannerList> call, Throwable t) {

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.dreamLL,R.id.psychologicalLL})
    void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dreamLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new ForecasterListFragment()).addToBackStack(ForecasterListFragment.class.getSimpleName()).commit();
                GlobalVariables.setType("Dreamer");
                break;

            case R.id.psychologicalLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new PsychologicalListFragment()).addToBackStack(ForecasterListFragment.class.getSimpleName()).commit();
                break;

            case R.id.notification_im:
                getFragmentManager().beginTransaction().replace(R.id.replace,new NotificationFragment()).commit();
                break;

            case R.id.pointtxt:
                Intent intent=new Intent(getActivity(),StoreActivity.class);
                intent.putExtra("Fragment","yes");
                startActivity(intent);
                break;

        }
    }

    private void createSlideShow()
    {
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (image_viewpager.getCurrentItem() < images.size() - 1)
                {
                    image_viewpager.setCurrentItem(image_viewpager.getCurrentItem() + 1);
                } else {
                    image_viewpager.setCurrentItem(0);
                }
            }
        };
        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },2500,2500);


    }

}
