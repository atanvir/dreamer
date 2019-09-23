package com.boushra.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
    EditText oldpass_txt,newpass_txt,confirmpass_txt;
    Boolean notificationStatus;
    String language;
    private ProgressDailogHelper dailogHelper;
    int clickcount=0,clickcount2=0,clickcount3=0;

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
                        Toast.makeText(SettingsActivity.this,server_response.getResponseMessage(),Toast.LENGTH_LONG).show();

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
                        Toast.makeText(SettingsActivity.this,server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();


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
                                    Toast.makeText(SettingsActivity.this, "" + server_response.getResponseMessage(), Toast.LENGTH_LONG).show();


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
                        Toast.makeText(SettingsActivity.this,""+server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();
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

        if(oldpass_txt.getText().toString().trim().isEmpty())
        {
            oldpass_txt.setError("Please enter old password");
            oldpass_txt.requestFocus();
            oldpass_txt.setFocusable(true);
            ret=false;
        }
        if(newpass_txt.getText().toString().trim().isEmpty())
        {
            newpass_txt.setError("Please enter new password");
            newpass_txt.requestFocus();
            newpass_txt.setFocusable(true);
            ret=false;
        }
        if(confirmpass_txt.getText().toString().trim().isEmpty() ||
           !confirmpass_txt.getText().toString().equalsIgnoreCase(newpass_txt.getText().toString().trim()))

        {
            if(confirmpass_txt.getText().toString().trim().isEmpty())
            {

                confirmpass_txt.setError("Please enter confirm password");
                confirmpass_txt.setFocusable(true);
                confirmpass_txt.requestFocus();
                ret=false;

            }
            else if(!confirmpass_txt.getText().toString().equalsIgnoreCase(newpass_txt.getText().toString().trim()))
            {
                confirmpass_txt.setError("Confirm password do not match");
                confirmpass_txt.setFocusable(true);
                confirmpass_txt.requestFocus();
                ret=false;

            }

        }


        return ret;
    }
}
