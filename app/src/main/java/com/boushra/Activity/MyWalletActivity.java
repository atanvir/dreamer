package com.boushra.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.Model.WalletDetail;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.totalpoint_txt) TextView totalpoint_txt;
    @BindView(R.id.sus_bk_txt) TextView sus_bk_txt;
    @BindView(R.id.progress_bk_txt) TextView progress_bk_txt;
    @BindView(R.id.complete_bk_txt) TextView complete_bk_txt;
    @BindView(R.id.cancel_bk_txt) TextView cancel_bk_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        getwalletDetailApi();


    }

    private void getwalletDetailApi() {
        ProgressDailogHelper dailogHelper = new ProgressDailogHelper(this,"Checking Points");
        dailogHelper.showDailog();
        if(new InternetCheck(MyWalletActivity.this).isConnect())
        {
            WalletDetail walletDetail=new WalletDetail();
            walletDetail.setUserId(SharedPreferenceWriter.getInstance(MyWalletActivity.this).getString(GlobalVariables._id));
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            Call<WalletDetail> call=api_service.getWalletDetail(walletDetail,SharedPreferenceWriter.getInstance(MyWalletActivity.this).getString(GlobalVariables.jwtToken));
            call.enqueue(new Callback<WalletDetail>() {
                @Override
                public void onResponse(Call<WalletDetail> call, Response<WalletDetail> response) {
                    if (response.isSuccessful())
                    {
                        dailogHelper.dismissDailog();
                       WalletDetail server_response=response.body();
                       if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                       {
                           totalpoint_txt.setText(""+server_response.getData().getTotalPoints());
                           sus_bk_txt.setText(""+server_response.getData().getSpendedPoints());
                           progress_bk_txt.setText(""+server_response.getData().getProgressBooking());
                           complete_bk_txt.setText(""+server_response.getData().getCompleteBooking());
                           cancel_bk_txt.setText(""+server_response.getData().getCancelBooking());


                       }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                       {
                           if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                               Toast.makeText(MyWalletActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                               finish();
                               startActivity(new Intent(MyWalletActivity.this, LoginActivity.class));
                               SharedPreferenceWriter.getInstance(MyWalletActivity.this).clearPreferenceValues();
                               FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                       if (!task.isSuccessful()) {
                                           // Log.w(TAG, "getInstanceId failed", task.getException());
                                           return;
                                       }

                                       String auth_token = task.getResult().getToken();
                                       Log.w("firebaese", "token: " + auth_token);
                                       SharedPreferenceWriter.getInstance(MyWalletActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                   }
                               });
                           }
                           else {
                               Toast.makeText(MyWalletActivity.this, server_response.getResponseMessage(), Toast.LENGTH_LONG).show();
                           }
                       }


                    }
                }

                @Override
                public void onFailure(Call<WalletDetail> call, Throwable t) {
                    Log.e("Failure" +
                            "",t.getMessage());

                }
            });


        }
        else
        {
            Toast.makeText(MyWalletActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
        }


    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                super.onBackPressed();
                break;
        }
    }
}
