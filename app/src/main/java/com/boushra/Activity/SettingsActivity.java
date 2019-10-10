package com.boushra.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.boushra.Fragment.NavigationMoreFragment;
import com.boushra.Model.ChangePassword;
import com.boushra.Model.Logout;
import com.boushra.Model.UserSetting;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.Validation;
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

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.changeLanguageCL) ConstraintLayout changeLanguageCL;
    @BindView(R.id.changePasswordCL) ConstraintLayout changePasswordCL;
    @BindView(R.id.logoutCL) ConstraintLayout logoutCL;
    @BindView(R.id.contact_us_cl) ConstraintLayout contact_us_cl;
    @BindView(R.id.trun_notifation_im) ImageView turn_notifation_im;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.touch_id_iv) ImageView touch_id_iv;
    EditText oldpass_txt,newpass_txt,confirmpass_txt;
    Boolean notificationStatus;
    String language;
    private ProgressDailogHelper dailogHelper;
    int clickcount=0,clickcount2=0,clickcount3=0;
    FingerprintManager fingerprintManager;
    boolean touch_id=false;

    TextView one_txt,two_txt,three_txt,four_txt,fifth_txt,six_txt,seven_txt,eight_txt,nine_txt,zero_txt,back_txt,create_passcode_txt;
    ImageView first_iv,secound_iv,third_iv,fourth_iv;
    int count_image=0;
    CountDownTimer timer;
    String code="";
    Dialog fingerprint_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        checkingNotificationStatus();
        checkingLanguage();


    }

    private void checkingLanguage() {
        language=SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables.language);
    }

    private void checkingNotificationStatus() {
        notificationStatus=SharedPreferenceWriter.getInstance(SettingsActivity.this).getBoolean(GlobalVariables.notificationStatus);
        if(notificationStatus)
        {
            turn_notifation_im.setImageDrawable(getDrawable(R.drawable.on));
        }
        else
        {
            turn_notifation_im.setImageDrawable(getDrawable(R.drawable.off));
        }
    }


    private void init() {
        changePasswordCL.setOnClickListener(this::onClick);
        changeLanguageCL.setOnClickListener(this::onClick);
        logoutCL.setOnClickListener(this::onClick);
        contact_us_cl.setOnClickListener(this::onClick);

        turn_notifation_im.setOnClickListener(this::onClick);
        backLL.setOnClickListener(this::onClick);
        dailogHelper=new ProgressDailogHelper(this,"");
        touch_id_iv.setOnClickListener(this::onClick);
        touch_id=SharedPreferenceWriter.getInstance(SettingsActivity.this).getBoolean(GlobalVariables.touchid);
        if(touch_id)
        {
            touch_id_iv.setImageResource(R.drawable.on);
        }
        else
        {
            touch_id_iv.setImageResource(R.drawable.off);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick()
    void onClick(View view) {
        switch (view.getId())
        {
            case R.id.logoutCL:
                UserLogoutApi();
                break;

            case R.id.changePasswordCL:
                ChangePasswordPopup();
                break;


            case R.id.backLL:
                  super.onBackPressed();
                  break;



            case R.id.contact_us_cl:
                Intent intent1=new Intent(SettingsActivity.this,ContactUsActivity.class);
                startActivity(intent1);
                break;

            case R.id.changeLanguageCL:
                ChangeLanguagePopup();
                break;

            case R.id.trun_notifation_im:
                notificationStatus=SharedPreferenceWriter.getInstance(SettingsActivity.this).getBoolean(GlobalVariables.notificationStatus);
                if(notificationStatus)
                {
                    turn_notifation_im.setImageDrawable(getDrawable(R.drawable.off));
                    updateUserSettingApi(false);
                }
                else
                {
                    turn_notifation_im.setImageDrawable(getDrawable(R.drawable.on));
                    updateUserSettingApi(true);
                }

                break;

            case R.id.touch_id_iv:

                settingTouchId();
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void settingTouchId() {
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        fingerprint_popup =new Dialog(SettingsActivity.this,android.R.style.Theme_Black);
        fingerprint_popup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        fingerprint_popup.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        fingerprint_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(!touch_id)
        {

            if(!fingerprintManager.isHardwareDetected())
            {
                settingPopup("Error","Your device doesn't support fingerprint authentication");
                touch_id_iv.setImageDrawable(getDrawable(R.drawable.off));
                touch_id=false;

            }
            else
            {
                settingPopup("Done","");
                //touch_id_iv.setImageDrawable(getDrawable(R.drawable.on));


            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // If your app doesn't have this permission, then display the following text//
                settingPopup("Error","Please enable the fingerprint permission");

                touch_id_iv.setImageDrawable(getDrawable(R.drawable.off));

                touch_id=false;

            }
            else
            {
                settingPopup("Done","");
                //touch_id_iv.setImageDrawable(getDrawable(R.drawable.on));

            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                // If the user hasn’t configured any fingerprints, then display the following message//
                settingPopup("Error","No fingerprint configured. Please register at least one fingerprint in your device's Settings");
//                        text.setText("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
                touch_id_iv.setImageDrawable(getDrawable(R.drawable.off));
                touch_id=false;
            }
            else
            {
                settingPopup("Done","");
                // touch_id_iv.setImageDrawable(getDrawable(R.drawable.on));

            }


        }
        else
        {

            touch_id=false;
            touch_id_iv.setImageDrawable(getDrawable(R.drawable.off));
            SharedPreferenceWriter.getInstance(SettingsActivity.this).writeBooleanValue(GlobalVariables.touchid,false);

        }
    }

    private void settingPopup(String view,String error) {
        if(view.equalsIgnoreCase("Error")) {
            fingerprint_popup.setContentView(R.layout.fingerprint_error_popup);
            LinearLayout close_ll= fingerprint_popup.findViewById(R.id.close_ll);
            TextView text=fingerprint_popup.findViewById(R.id.text);
            text.setText(error);
            close_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fingerprint_popup.dismiss();
                }
            });
            fingerprint_popup.show();
            SharedPreferenceWriter.getInstance(SettingsActivity.this).writeBooleanValue(GlobalVariables.touchid,false);

        }else if(view.equalsIgnoreCase("Done"))
        {
            fingerprint_popup.setContentView(R.layout.pop_pin);
            init2();
            fingerprint_popup.show();


        }


    }

    private void init2() {
        first_iv=fingerprint_popup.findViewById(R.id.first_iv);
        secound_iv=fingerprint_popup.findViewById(R.id.secound_iv);
        third_iv=fingerprint_popup.findViewById(R.id.third_iv);
        fourth_iv=fingerprint_popup.findViewById(R.id.fourth_iv);
        one_txt=fingerprint_popup.findViewById(R.id.one_txt);
        two_txt=fingerprint_popup.findViewById(R.id.two_txt);
        three_txt=fingerprint_popup.findViewById(R.id.three_txt);
        four_txt=fingerprint_popup.findViewById(R.id.four_txt);
        fifth_txt=fingerprint_popup.findViewById(R.id.fifth_txt);
        six_txt=fingerprint_popup.findViewById(R.id.six_txt);
        seven_txt=fingerprint_popup.findViewById(R.id.seven_txt);
        eight_txt=fingerprint_popup.findViewById(R.id.eight_txt);
        nine_txt=fingerprint_popup.findViewById(R.id.nine_txt);
        zero_txt=fingerprint_popup.findViewById(R.id.zero_txt);
        back_txt=fingerprint_popup.findViewById(R.id.back_txt);
        create_passcode_txt=fingerprint_popup.findViewById(R.id.create_passcode_txt);

        one_txt.setOnClickListener(this::DailogOnClickListner);
        two_txt.setOnClickListener(this::DailogOnClickListner);
        three_txt.setOnClickListener(this::DailogOnClickListner);
        four_txt.setOnClickListener(this::DailogOnClickListner);
        fifth_txt.setOnClickListener(this::DailogOnClickListner);
        six_txt.setOnClickListener(this::DailogOnClickListner);
        seven_txt.setOnClickListener(this::DailogOnClickListner);
        eight_txt.setOnClickListener(this::DailogOnClickListner);
        nine_txt.setOnClickListener(this::DailogOnClickListner);
        zero_txt.setOnClickListener(this::DailogOnClickListner);
        back_txt.setOnClickListener(this::DailogOnClickListner);

    }

    private void DailogOnClickListner(View v) {
        switch (v.getId())
        {
            case R.id.one_txt:
                count_image++;
                settingBackground(one_txt,count_image,1);
                break;

            case R.id.two_txt:
                count_image++;
                settingBackground(two_txt,count_image,2);
                break;
            case R.id.three_txt:
                count_image++;
                settingBackground(three_txt,count_image,3);

                break;
            case R.id.four_txt:
                count_image++;
                settingBackground(four_txt,count_image,4);
                break;
            case R.id.fifth_txt:
                count_image++;
                settingBackground(fifth_txt,count_image,5);
                break;
            case R.id.six_txt:
                count_image++;
                settingBackground(six_txt,count_image,6);
                break;
            case R.id.seven_txt:
                count_image++;
                settingBackground(seven_txt,count_image,7);
                break;
            case R.id.eight_txt:
                count_image++;
                settingBackground(eight_txt,count_image,8);
                break;
            case R.id.nine_txt:
                count_image++;
                settingBackground(nine_txt,count_image,9);
                break;
            case R.id.zero_txt:
                count_image++;
                settingBackground(zero_txt,count_image,0);

                break;
            case R.id.back_txt:
                if(back_txt.getText().equals(getString(R.string.delete)))
                {

                    if(count_image==1)
                    {
                        count_image--;
                        code=code.substring(0,code.length()-1);
                        Log.e("DELETE",code);
                        first_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                        back_txt.setText(getString(R.string.back));

                    }else if(count_image==2)
                    {
                        count_image--;
                        code=code.substring(0,code.length()-1);
                        Log.e("DELETE",code);
                        secound_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                    }
                    else if(count_image==3)
                    {
                        count_image--;
                        code=code.substring(0,code.length()-1);
                        Log.e("DELETE",code);
                        third_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                    }
                    else if(count_image==4)
                    {
                        code=code.substring(0,code.length()-1);
                        count_image--;
                        Log.e("DELETE",code);
                        fourth_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                    }

                }
                else
                {
                    fingerprint_popup.dismiss();
                }
                break;
        }



    }

    private void settingBackground(TextView txtview,int count,int value) {
        if (count <= 4) {
            timer = new CountDownTimer(300, 300) {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onTick(long millisUntilFinished) {
                    txtview.setBackground(getDrawable(R.drawable.green_circle_shape4));
                    txtview.setTextColor(getColor(R.color.white));
                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onFinish() {
                    txtview.setBackground(getDrawable(R.drawable.green_circle_shape3));
                    txtview.setTextColor(getColor(R.color.black));
                    if(count==1)
                    {
                        first_iv.setBackground(getDrawable(R.drawable.pin_circle_background2));
                        back_txt.setText(getString(R.string.delete));
                        code+=value;
                        Log.e("code",code);

                    }
                    else if(count==2) {
                        secound_iv.setBackground(getDrawable(R.drawable.pin_circle_background2));
                        code+=value;
                        Log.e("code",code);
                    }
                    else if(count==3)
                    {
                        third_iv.setBackground(getDrawable(R.drawable.pin_circle_background2));
                        code+=value;
                        Log.e("code",code);
                    }
                    else if(count==4) {
                        fourth_iv.setBackground(getDrawable(R.drawable.pin_circle_background2));
                        code+=value;



                        Log.e("code",code);



                        if(!create_passcode_txt.getText().toString().equalsIgnoreCase(getString(R.string.please_reenter_confirm_password)))
                        {
                            SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.old_passcode,code);
                            fingerprint_popup.setContentView(R.layout.pop_pin);
                            init2();

                            count_image=0;
                            code="";
                            TextView create_passcode_txt2=fingerprint_popup.findViewById(R.id.create_passcode_txt);
                            create_passcode_txt.setText(getString(R.string.please_reenter_confirm_password));
                            fingerprint_popup.show();



                        }
                        else
                        {
                            String old_pass= SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables.old_passcode);
                            if(!code.equalsIgnoreCase("")) {

                                if (old_pass.equalsIgnoreCase(code)) {
                                    fingerprint_popup.dismiss();
                                    Log.e("matched","yes");
                                    Dialog dialog = new Dialog(SettingsActivity.this, android.R.style.Theme_Black);
                                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.setContentView(R.layout.fingerprint_done_popup);
                                    LinearLayout close_ll = dialog.findViewById(R.id.close_ll);
                                    close_ll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

                                        }
                                    });
                                    dialog.show();
                                    touch_id_iv.setImageDrawable(getDrawable(R.drawable.on));
                                    SharedPreferenceWriter.getInstance(SettingsActivity.this).writeBooleanValue(GlobalVariables.touchid, true);
                                    touch_id = true;


                                } else {
                                    Log.e("matched","no");
                                    Vibrator v= (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        //deprecated in API 26
                                        v.vibrate(200);
                                    }
                                    Toast.makeText(SettingsActivity.this,getString(R.string.password_not_match),Toast.LENGTH_LONG).show();
                                    count_image=0;
                                    code="";
                                    Log.e("count", String.valueOf(code));
                                    first_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                                    secound_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                                    third_iv.setBackground(getDrawable(R.drawable.pin_circle_background));
                                    fourth_iv.setBackground(getDrawable(R.drawable.pin_circle_background));


                                    touch_id_iv.setImageDrawable(getDrawable(R.drawable.off));
                                    SharedPreferenceWriter.getInstance(SettingsActivity.this).writeBooleanValue(GlobalVariables.touchid, false);
                                   // Toast.makeText(SettingsActivity.this, getString(R.string.confirm_password_not_match), Toast.LENGTH_LONG).show();
                                    touch_id = false;
                                }
                            }


                        }


                    }



                }
            }.start();

        }
        else
        {

            count_image--;

        }

    }


    private void updateUserSettingApi(Boolean notificationStatus) {
        dailogHelper.showDailog();
        RetroInterface api_service=RetrofitInit.getConnect().createConnection();
        UserSetting userSetting=new UserSetting();
        userSetting.setLangCode("en");
        userSetting.setNotificationStatus(notificationStatus);
        userSetting.setLanguage("English");
        userSetting.setUserId(SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables._id));
        Call<UserSetting> call=api_service.updateUserSetting(userSetting,SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables.jwtToken));
        call.enqueue(new Callback<UserSetting>() {
            @Override
            public void onResponse(Call<UserSetting> call, Response<UserSetting> response) {
                if(response.isSuccessful())
                {
                    UserSetting server_response=response.body();
                    if(server_response.getStatus().equalsIgnoreCase("SUCCESS"))
                    {
                        dailogHelper.dismissDailog();
                      // Toast.makeText(SettingsActivity.this,server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
                       settingPreferences(server_response);
                    }
                    else if(server_response.getStatus().equalsIgnoreCase("FAILURE"))
                    {
                        dailogHelper.dismissDailog();

                        if(server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken))
                        {
                            Toast.makeText(SettingsActivity.this,getString(R.string.other_device_logged_in),Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                            SharedPreferenceWriter.getInstance(SettingsActivity.this).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese","token: "+auth_token);
                                    SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.firebase_token,auth_token);
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SettingsActivity.this, server_response.getResponseMessage(), Toast.LENGTH_LONG).show();

                        }


                    }

                }
            }

            @Override
            public void onFailure(Call<UserSetting> call, Throwable t) {

            }
        });



    }

    private void settingPreferences(UserSetting server_response) {
        SharedPreferenceWriter.getInstance(SettingsActivity.this).writeBooleanValue(GlobalVariables.notificationStatus, server_response.getData().getNotificationStatus());
        SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.language, server_response.getData().getLanguage());
        SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables._id, server_response.getData().getId());

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ChangeLanguagePopup() {
        final Dialog dialog2=new Dialog(SettingsActivity.this,android.R.style.Theme_Black);
        dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.activity_select_language);
        TextView eng_txt=dialog2.findViewById(R.id.eng_txt);
        ImageView eng_im=dialog2.findViewById(R.id.eng_im);
        TextView arabic_txt=dialog2.findViewById(R.id.arabic_txt);
        ImageView arb_iv=dialog2.findViewById(R.id.arb_iv);
        TextView savetext=dialog2.findViewById(R.id.savetext);

        if(language.equalsIgnoreCase("English"))
        {
            arb_iv.setImageDrawable(getDrawable(R.drawable.uncheck_ic));
            eng_im.setImageDrawable(getDrawable(R.drawable.checked_ic));
            arabic_txt.setTextColor(getColor(R.color.black));
            eng_txt.setTextColor(getColor(R.color.app_theme));

        }
        else
        {
            arb_iv.setImageDrawable(getDrawable(R.drawable.checked_ic));
            eng_im.setImageDrawable(getDrawable(R.drawable.uncheck_ic));
            arabic_txt.setTextColor(getColor(R.color.app_theme));
            eng_txt.setTextColor(getColor(R.color.black));

        }


        arb_iv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                arb_iv.setImageDrawable(getDrawable(R.drawable.checked_ic));
                eng_im.setImageDrawable(getDrawable(R.drawable.uncheck_ic));
                arabic_txt.setTextColor(getColor(R.color.app_theme));
                eng_txt.setTextColor(getColor(R.color.black));
                language="Arabic";

            }
        });

        eng_im.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                arb_iv.setImageDrawable(getDrawable(R.drawable.uncheck_ic));
                eng_im.setImageDrawable(getDrawable(R.drawable.checked_ic));
                arabic_txt.setTextColor(getColor(R.color.black));
                eng_txt.setTextColor(getColor(R.color.app_theme));
                language="English";


            }
        });


        savetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                updateLanguageSettingApi(language);
            }
        });
        dialog2.show();

    }

    private void updateLanguageSettingApi(String language) {
        dailogHelper.showDailog();
        RetroInterface api_service=RetrofitInit.getConnect().createConnection();
        UserSetting userSetting=new UserSetting();
        userSetting.setLanguage(language);
        userSetting.setNotificationStatus(SharedPreferenceWriter.getInstance(SettingsActivity.this).getBoolean(GlobalVariables.notificationStatus));
        userSetting.setLangCode("en");
        userSetting.setUserId(SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables._id));
        Call<UserSetting> call=api_service.updateUserSetting(userSetting,SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables.jwtToken));
        call.enqueue(new Callback<UserSetting>() {
            @Override
            public void onResponse(Call<UserSetting> call, Response<UserSetting> response) {
                if(response.isSuccessful())
                {
                    UserSetting server_resposne=response.body();
                    if(server_resposne.getStatus().equalsIgnoreCase("SUCCESS"))
                    {
                        dailogHelper.dismissDailog();
                        //Toast.makeText(SettingsActivity.this,server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();
                        settingPreferences(server_resposne);
                        Intent intent=new Intent(SettingsActivity.this,CategorySelectionActivity.class);
                        startActivity(intent);


                    }else if(server_resposne.getStatus().equalsIgnoreCase("FAILURE"))
                    {
                        dailogHelper.dismissDailog();
                        if(server_resposne.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken))
                        {
                            Toast.makeText(SettingsActivity.this,getString(R.string.other_device_logged_in),Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                            SharedPreferenceWriter.getInstance(SettingsActivity.this).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese","token: "+auth_token);
                                    SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.firebase_token,auth_token);
                                }
                            });
                        }
                        else {
                            Toast.makeText(SettingsActivity.this, server_resposne.getResponseMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<UserSetting> call, Throwable t) {

            }
        });


    }

    private void ChangePasswordPopup() {
        final Dialog dialog=new Dialog(SettingsActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_change_password);


        TextView changePasswordTextView=dialog.findViewById(R.id.changePasswordTextView);
        oldpass_txt=dialog.findViewById(R.id.oldpass_txt);
        newpass_txt=dialog.findViewById(R.id.newpass_txt);
        confirmpass_txt=dialog.findViewById(R.id.confirmpass_txt);
        TouchListner();
        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    dailogHelper.showDailog();
                    RetroInterface api_service = RetrofitInit.getConnect().createConnection();
                    ChangePassword password = new ChangePassword();
                    password.setUserId(SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables._id));
                    password.setPassword(oldpass_txt.getText().toString().trim());
                    password.setNewPassword(newpass_txt.getText().toString().trim());
                    password.setLangCode("en");
                    Call<ChangePassword> call = api_service.userChangePassword(password, SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables.jwtToken));
                    call.enqueue(new Callback<ChangePassword>() {
                        @Override
                        public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                            if (response.isSuccessful()) {
                                ChangePassword server_response = response.body();
                                if (server_response.getStatus().equalsIgnoreCase("SUCCESS")) {
                                    dialog.dismiss();
                                    dailogHelper.dismissDailog();
                                    Toast.makeText(SettingsActivity.this, "" + server_response.getResponseMessage(), Toast.LENGTH_LONG).show();

                                } else if (server_response.getStatus().equalsIgnoreCase("FAILURE")) {
                                    dialog.dismiss();
                                    dailogHelper.dismissDailog();
                                    if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                        Toast.makeText(SettingsActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                                        SharedPreferenceWriter.getInstance(SettingsActivity.this).clearPreferenceValues();
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    // Log.w(TAG, "getInstanceId failed", task.getException());
                                                    return;
                                                }

                                                String auth_token = task.getResult().getToken();
                                                Log.w("firebaese", "token: " + auth_token);
                                                SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                            }
                                        });
                                    } else {

                                        Toast.makeText(SettingsActivity.this, "" + server_response.getResponseMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePassword> call, Throwable t) {

                        }
                    });

                }
            }
        });
        dialog.show();
    }

    private void TouchListner() {
        oldpass_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (oldpass_txt.getRight() - oldpass_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount % 2 ==0)
                        {
                            clickcount=clickcount+1;
                            oldpass_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            oldpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount=clickcount+1;
                            oldpass_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            oldpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.un_view, 0);
                            return true;


                        }

                    }
                }


                return false;
            }
        });
        newpass_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newpass_txt.getRight() - newpass_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount2 % 2 ==0)
                        {
                            clickcount2=clickcount2+1;
                            newpass_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            newpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount2=clickcount2+1;
                            newpass_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            newpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.un_view, 0);
                            return true;


                        }

                    }
                }


                return false;
            }
        });

        confirmpass_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confirmpass_txt.getRight() - confirmpass_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount3 % 2 ==0)
                        {
                            clickcount3=clickcount3+1;
                            confirmpass_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confirmpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount3=clickcount3+1;
                            confirmpass_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirmpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.un_view, 0);
                            return true;


                        }

                    }
                }


                return false;
            }
        });

    }

    private void UserLogoutApi() {
        dailogHelper.showDailog();
        RetroInterface api_service =RetrofitInit.getConnect().createConnection();
        Logout logout=new Logout();
        logout.setUserId(SharedPreferenceWriter.getInstance(SettingsActivity.this).getString(GlobalVariables._id));
        logout.setLangCode("en");
        Call<Logout> call= api_service.userLogout(logout);
        call.enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(Call<Logout> call, Response<Logout> response) {
                if(response.isSuccessful())
                {
                    Logout server_resposne=response.body();
                    if(server_resposne.getStatus().equalsIgnoreCase("SUCCESS"))
                    {
                        dailogHelper.dismissDailog();
                        Toast.makeText(SettingsActivity.this,""+server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();
                        SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.islogin,"No");
                        Intent intent=new Intent(SettingsActivity.this,LoginActivity.class);
                        finish();
                        startActivity(intent);
                        SharedPreferenceWriter.getInstance(SettingsActivity.this).clearPreferenceValues();
                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    // Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }

                                String auth_token = task.getResult().getToken();
                                Log.w("firebaese","token: "+auth_token);
                                SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.firebase_token,auth_token);
                            }
                        });
                    }
                    else if(server_resposne.getStatus().equalsIgnoreCase("FAILURE"))
                    {
                        dailogHelper.dismissDailog();
                        if (server_resposne.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                            Toast.makeText(SettingsActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                            SharedPreferenceWriter.getInstance(SettingsActivity.this).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese", "token: " + auth_token);
                                    SharedPreferenceWriter.getInstance(SettingsActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                }
                            });
                        }
                        else{
                        Toast.makeText(SettingsActivity.this,""+server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();
                    }
                    }
                }


            }

            @Override
            public void onFailure(Call<Logout> call, Throwable t) {

            }
        });
    }

    private boolean checkValidation() {
        boolean ret=true;
        if(!Validation.hasText(oldpass_txt,getString(R.string.please_enter_old_password))
                || !Validation.hasText(newpass_txt,getString(R.string.please_enter_new_password))
                || !Validation.hasText(confirmpass_txt,getString(R.string.please_enter_confirm_password))
                || !confirmpass_txt.getText().toString().equalsIgnoreCase(newpass_txt.getText().toString().trim())
        )
        {
            if(!Validation.hasText(oldpass_txt,getString(R.string.please_enter_old_password)))
            {
                ret=false;
                oldpass_txt.requestFocus();
            }

            else if(!Validation.hasText(newpass_txt,getString(R.string.please_enter_new_password)))
            {
                ret=false;
                newpass_txt.requestFocus();

            }else if(!Validation.hasText(confirmpass_txt,getString(R.string.please_enter_confirm_password)))
            {
                ret=false;
                confirmpass_txt.requestFocus();
            }
            else if(!confirmpass_txt.getText().toString().equalsIgnoreCase(newpass_txt.getText().toString().trim()))
            {
                confirmpass_txt.setError(getString(R.string.confirm_password_not_match));
                confirmpass_txt.setFocusable(true);
                confirmpass_txt.requestFocus();
                ret = false;

            }
        }

        return ret;
    }
}
