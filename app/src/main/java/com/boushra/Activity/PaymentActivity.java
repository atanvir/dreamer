package com.boushra.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.Model.BookForcaster;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.AddBody;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    @BindView(R.id.paytext) TextView paytext;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.avail_points_txt) TextView avail_points_txt;
    @BindView(R.id.forcaster_fees_txt) TextView forcaster_fees_txt;
    String avail_point;
    String forcaster_fees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();




    }

    private void init() {
        paytext.setOnClickListener(this::OnClick);
        backLL.setOnClickListener(this::OnClick);
        avail_points_txt.setText(""+SharedPreferenceWriter.getInstance(PaymentActivity.this).getString(GlobalVariables.totalPoints));
        forcaster_fees_txt.setText(getIntent().getStringExtra(GlobalVariables.points));
        avail_point= SharedPreferenceWriter.getInstance(PaymentActivity.this).getString(GlobalVariables.totalPoints);
        forcaster_fees= getIntent().getStringExtra(GlobalVariables.points);
    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.paytext:
                if(Float.parseFloat(avail_point)>=Float.parseFloat(forcaster_fees))
                {
                    bookForcasterApi();

                }
                else
                {
                    insufficientPopUp();

                }

                break;


            case R.id.backLL:
                Intent intent=new Intent(PaymentActivity.this,ForcasterForPriceActivity.class);
                startActivity(intent);
                finish();


                break;
        }

    }

    private void bookForcasterApi() {
        if(new InternetCheck(PaymentActivity.this).isConnect())
        {
            ProgressDailogHelper dailogHelper=new ProgressDailogHelper(PaymentActivity.this,"Booking under process");
            dailogHelper.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            BookForcaster bookforcaster=new BookForcaster();
            bookforcaster.setForecasterId(getIntent().getStringExtra(GlobalVariables.forecasterId));
            bookforcaster.setUserId(getIntent().getStringExtra(GlobalVariables.userId));
            bookforcaster.setName(getIntent().getStringExtra(GlobalVariables.name));
            bookforcaster.setDob(getIntent().getStringExtra(GlobalVariables.dob));
            bookforcaster.setGender(getIntent().getStringExtra(GlobalVariables.gender));
            bookforcaster.setLangCode(SharedPreferenceWriter.getInstance(PaymentActivity.this).getString(GlobalVariables.langCode));
            bookforcaster.setMaritalStatus(getIntent().getStringExtra(GlobalVariables.maritalStatus));

            if(getIntent().getStringExtra(GlobalVariables.points)!=null) {
                bookforcaster.setPoints(Float.valueOf(getIntent().getStringExtra(GlobalVariables.points)));
            }
            else
            {
                bookforcaster.setPoints(Float.valueOf(0));
            }

            bookforcaster.setCategoryName(getIntent().getStringExtra(GlobalVariables.categoryName));
            if(getIntent().getStringExtra(GlobalVariables.dream)!=null) {
                bookforcaster.setQuestion(getIntent().getStringExtra(GlobalVariables.dream));
            }
            else
            {
                bookforcaster.setQuestion("");
            }
            AddBody body=new AddBody(bookforcaster);

            RequestBody requestBody;
            MultipartBody.Part voice=null;

            if(getIntent().getStringExtra(GlobalVariables.voice)!=null) {
                File audio=new File(getIntent().getStringExtra(GlobalVariables.voice));
                requestBody = RequestBody.create(MediaType.parse("*/*"), audio);
                voice = MultipartBody.Part.createFormData("voiceRecording", audio.getName(), requestBody);
            }
            Call<BookForcaster> call=api_service.bookForcaster(voice,body.getBody());
            call.enqueue(new Callback<BookForcaster>() {
                @Override
                public void onResponse(Call<BookForcaster> call, Response<BookForcaster> response) {
                    if(response.isSuccessful())
                    {
                        dailogHelper.dismissDailog();
                        BookForcaster server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                            setPreference(server_response);
                            requestPlaceSuccessfullyPopUp(server_response.getResponseMessage());


                        }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE)) {
                            if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                Toast.makeText(PaymentActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PaymentActivity.this, LoginActivity.class));
                                SharedPreferenceWriter.getInstance(PaymentActivity.this).clearPreferenceValues();
                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            // Log.w(TAG, "getInstanceId failed", task.getException());
                                            return;
                                        }

                                        String auth_token = task.getResult().getToken();
                                        Log.w("firebaese", "token: " + auth_token);
                                        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                    }
                                });
                            } else {
                                requestPlaceSuccessfullyPopUp(server_response.getResponseMessage());
//                                Toast.makeText(PaymentActivity.this, server_response.getResponseMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BookForcaster> call, Throwable t) {

                }
            });

        }
        else
        {
            Toast.makeText(PaymentActivity.this,getString(R.string.check_internet),Toast.LENGTH_LONG).show();
        }

    }

    private void setPreference(BookForcaster server_response) {
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.bookingStatus,server_response.getData().getBookingStatus());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.paymentStatusVerifyByAdmin,server_response.getData().getPaymentStatusVerifyByAdmin());
        //SharedPreferenceWriter.getInstance(ForcasterForPriceActivity.this).writeStringValue(GlobalVariables._id,server_response.getData().getId());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.userId,server_response.getData().getUserId());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.forecasterId,server_response.getData().getForecasterId());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.question,server_response.getData().getQuestion());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.points,server_response.getData().getPoints());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.transactionStatus,server_response.getData().getTransactionStatus());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.categoryName,server_response.getData().getCategoryName());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.roomId,server_response.getData().getRoomId());
        float available_point= Float.parseFloat(SharedPreferenceWriter.getInstance(PaymentActivity.this).getString(GlobalVariables.totalPoints));
        float deducted_points=Float.parseFloat(server_response.getData().getPoints());
        SharedPreferenceWriter.getInstance(PaymentActivity.this).writeStringValue(GlobalVariables.totalPoints, String.valueOf((available_point-deducted_points)));
    }

    private void requestPlaceSuccessfullyPopUp(String response) {
        final Dialog dialog=new Dialog(PaymentActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_bank_transfer_payment_popup);
        TextView response_txt=dialog.findViewById(R.id.response_txt);
        response_txt.setText(response);
        LinearLayout closell=dialog.findViewById(R.id.closell);
        dialog.setCancelable(true);
        closell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(PaymentActivity
                        .this,CategorySelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }



    private void insufficientPopUp() {
        final Dialog dialog=new Dialog(PaymentActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_insufficient);
        TextView rechargeText=dialog.findViewById(R.id.rechargeText);
        LinearLayout closell=dialog.findViewById(R.id.closell);
        TextView cancelText=dialog.findViewById(R.id.cancelText);

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rechargeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(PaymentActivity.this,StoreActivity.class);
                startActivity(intent);

            }
        });

        closell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
