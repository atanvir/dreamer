package com.boushra.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.boushra.Fragment.NavigationMoreFragment;
import com.boushra.Model.WalletDetail;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Util.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;

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
                           Toast.makeText(MyWalletActivity.this,server_response.getResponseMessage(),Toast.LENGTH_LONG).show();

                       }


                    }
                }

                @Override
                public void onFailure(Call<WalletDetail> call, Throwable t) {

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
