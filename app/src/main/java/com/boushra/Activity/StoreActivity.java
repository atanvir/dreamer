package com.boushra.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Adapter.StoreAdapter;
import com.boushra.Model.StorePoints;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreActivity extends AppCompatActivity {
    private StoreAdapter adapter;
    GridLayoutManager manager;
    List<StorePoints> storePointsList;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.recView) RecyclerView recView;
    @BindView(R.id.avail_points_cv) CardView avail_points_cv;
    @BindView(R.id.avail_points_txt) TextView avail_points_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        init();
        avail_points_txt.setText(""+SharedPreferenceWriter.getInstance(StoreActivity.this).getInt(GlobalVariables.totalPoints));

        storePointsList=new ArrayList<>();
        SettingValues();

        manager=new GridLayoutManager(this,2);
        adapter=new StoreAdapter(this,storePointsList);
        recView.setLayoutManager(manager);
        recView.setAdapter(adapter);
    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        avail_points_cv.setOnClickListener(this::OnClick);
    }

    @OnClick()
    void OnClick(View view)
    {
       switch (view.getId())
       {
           case R.id.backLL:
               Intent intent=new Intent(StoreActivity.this,PaymentActivity.class);
               startActivity(intent);
               finish();
               break;

           case R.id.avail_points_cv:
               Intent intent1=new Intent(StoreActivity.this,MyWalletActivity.class);
               startActivity(intent1);
               finish();
               break;
       }
    }


    private void SettingValues() {
        storePointsList.add(new StorePoints("20",R.drawable.a,"Purchase 20SAR"));
        storePointsList.add(new StorePoints("30",R.drawable.b,"Purchase 30SAR"));
        storePointsList.add(new StorePoints("50",R.drawable.c,"Purchase 50SAR"));
        storePointsList.add(new StorePoints("150",R.drawable.d,"Purchase 150SAR"));
        storePointsList.add(new StorePoints("300",R.drawable.e,"Purchase 300SAR"));
    }
}
