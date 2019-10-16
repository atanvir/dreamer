package com.boushra.Activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Adapter.StoreAdapter;
import com.boushra.Model.Common;
import com.boushra.Model.Data;
import com.boushra.Model.StorePoints;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {
    private StoreAdapter adapter;
    GridLayoutManager manager;
    List<Data> storePointsList;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.recView) RecyclerView recView;
    @BindView(R.id.avail_points_cv) CardView avail_points_cv;
    @BindView(R.id.avail_points_txt) TextView avail_points_txt;
    private ProgressDailogHelper dailogHelper;
    String fragment="";
    String StoreActivity="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        init();
        avail_points_txt.setText(""+SharedPreferenceWriter.getInstance(StoreActivity.this).getString(GlobalVariables.totalPoints));
        getStoreListApi();


    }

    private void getStoreListApi() {
        if(new InternetCheck(this).isConnect())
        {
            dailogHelper.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            Call<Common> call = api_service.getStoreList();
            call.enqueue(new Callback<Common>() {
                @Override
                public void onResponse(Call<Common> call, Response<Common> response) {
                    if(response.isSuccessful())
                    {
                        dailogHelper.dismissDailog();
                        Common server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                            storePointsList=server_response.getData();
                            settingAdapter();

                        }
                        else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            Toast.makeText(StoreActivity.this, server_response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Common> call, Throwable t) {
                    dailogHelper.dismissDailog();
                    Toast.makeText(StoreActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }else
        {
           Toast toast= Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT);
           toast.setGravity(Gravity.CENTER,0,0);
           toast.show();
        }
    }

    private void settingAdapter() {
        manager=new GridLayoutManager(this,2);
        adapter=new StoreAdapter(this,storePointsList);
        recView.setLayoutManager(manager);
        recView.setAdapter(adapter);


    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        avail_points_cv.setOnClickListener(this::OnClick);
        dailogHelper=new ProgressDailogHelper(this,"");
        fragment=getIntent().getStringExtra("Fragment");
        StoreActivity=getIntent().getStringExtra("StoreActivity");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick()
    void OnClick(View view)
    {
       switch (view.getId())
       {
           case R.id.backLL:
               if(fragment!=null)
               {
                   super.onBackPressed();
                   finish();

               }

               else if(StoreActivity!=null)
               {

                  super.onBackPressed();
                  finish();
               }
               else
               {
                   Intent intent=new Intent(StoreActivity.this,PaymentActivity.class);
                   startActivity(intent);
                   finish();
               }


               break;

           case R.id.avail_points_cv:
               Intent intent1=new Intent(StoreActivity.this,MyWalletActivity.class);
               startActivity(intent1);
               finish();
               break;
       }
    }


}
