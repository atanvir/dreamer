package com.boushra.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.boushra.Model.ForgotPassword;
import com.boushra.Model.Login;
import com.boushra.Model.Signup;
import com.boushra.Model.SocialLogin;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.Validation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.heetch.countrypicker.Country;
import com.heetch.countrypicker.CountryPickerCallbacks;
import com.heetch.countrypicker.CountryPickerDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.singup) TextView singup;
    @BindView(R.id.googleLL) LinearLayout googleLL;
    @BindView(R.id.facebookll) LinearLayout facebookll;
    @BindView(R.id.mobile_number_ed) EditText mobile_number_ed;
    @BindView(R.id.password_ed) EditText password_ed;
    @BindView(R.id.forgetPasswordtxt) TextView forgetPasswordtxt;
    @BindView(R.id.countryCodePicker) TextView countryCodePicker;
    @BindView(R.id.log_in) Button log_in;
    String deviceToken;
    ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;
    String verificationCode;

    public static final int req_code=100;
    String auth_token;
    FirebaseAuth auth;;

    EditText newpass_txt,confirmpass_txt;
    String countrycode="";
    TextView resend_code_txt;
    long clickcount=0;
    EditText opt_phone_ed;
    ProgressDailogHelper dailogHelper;
    int clickcount2=0,clickcount3=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        init();

        auth_token=SharedPreferenceWriter.getInstance(LoginActivity.this).getString(GlobalVariables.firebase_token);
        auth= FirebaseAuth.getInstance();
        deviceToken= SharedPreferenceWriter.getInstance(LoginActivity.this).getString(GlobalVariables.firebase_token);
        settingProgressDialog();


        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        password_ed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password_ed.getRight() - password_ed.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount % 2 ==0)
                        {
                            clickcount=clickcount+1;
                            password_ed.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            password_ed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount=clickcount+1;
                            password_ed.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password_ed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.un_view, 0);
                            return true;


                        }

                    }
                }


                return false;
            }
        });


    }

    private void init() {
        singup.setOnClickListener(this::click);
        log_in.setOnClickListener(this::click);
        forgetPasswordtxt.setOnClickListener(this::click);
        googleLL.setOnClickListener(this::click);
        facebookll.setOnClickListener(this::click);
        countryCodePicker.setOnClickListener(this::click);

    }

    private void settingProgressDialog() {
        dailogHelper=new ProgressDailogHelper(this,"");
    }

    @OnClick()
    void click(View view)
    {
        switch (view.getId()){

            case R.id.singup:
                Intent singup=new Intent(LoginActivity.this,SignupActivity.class);
                finish();
                startActivity(singup);
                break;
            case R.id.log_in:
                if(checkValidation()) {
                    ProgressDailogHelper dailogHelper=new ProgressDailogHelper(this,"");
                    dailogHelper.showDailog();
                    Login login=new Login();
                    if(countrycode.equalsIgnoreCase(""))
                    {
                        countrycode="+91";
                        login.setCountryCode(countrycode);


                    }
                    else
                    {
                        login.setCountryCode(countrycode);
                    }

                    login.setDeviceType("Android");
                    login.setMobileNumber(mobile_number_ed.getText().toString().trim());
                    login.setPassword(password_ed.getText().toString().trim());
                    login.setDeviceToken(deviceToken);
                    login.setLangCode("en");
                    RetroInterface api_service =RetrofitInit.getConnect().createConnection();
                    Call<Login> call=api_service.checkLogin(login);
                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if(response.isSuccessful())
                            {
                                dailogHelper.dismissDailog();
                                Login server_response=response.body();
                                if(server_response.getStatus().equalsIgnoreCase("SUCCESS"))
                                {
                                    setPreferences(server_response);
                                    Intent intent=new Intent(LoginActivity.this,CategorySelectionActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else if(server_response.getStatus().equalsIgnoreCase("FAILURE"))
                                {
                                    if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                        Toast.makeText(LoginActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                        SharedPreferenceWriter.getInstance(LoginActivity.this).clearPreferenceValues();
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    // Log.w(TAG, "getInstanceId failed", task.getException());
                                                    return;
                                                }

                                                String auth_token = task.getResult().getToken();
                                                Log.w("firebaese", "token: " + auth_token);
                                                SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                            }
                                        });
                                    }else
                                    {
                                    Toast.makeText(LoginActivity.this,""+server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
                                }
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Log.e("error",t.getMessage());

                        }
                    });

                }

                break;
            case R.id.googleLL:
//                signIn();
                Intent intent=new Intent(LoginActivity.this,CategorySelectionActivity.class);
                startActivity(intent);

                break;


            case R.id.facebookll:
                Intent intent1=new Intent(LoginActivity.this,CategorySelectionActivity.class);
                startActivity(intent1);
                break;

            case R.id.forgetPasswordtxt:
                forgotPasswordPopup();
//                changePasswordPopUp();
                break;

            case R.id.countryCodePicker:
                CountryPickerDialog countryPicker = new CountryPickerDialog(LoginActivity.this, new CountryPickerCallbacks() {
                            @Override
                            public void onCountrySelected(Country country, int flagResId) {
                                //country.toString();
                                countryCodePicker.setText("+"+country.getDialingCode());
                                countrycode=countryCodePicker.getText().toString();


                                // TODO handle callback
                            }
                        });
                countryPicker.show();


                break;

        }
    }

    private void forgotPasswordPopup() {
        final Dialog dialog=new Dialog(LoginActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_forget_password);
        TextView countrycode_txt=dialog.findViewById(R.id.countrycode_txt);
        LinearLayout closell=dialog.findViewById(R.id.closell);
        TextView forgetPasswordtxt=dialog.findViewById(R.id.forgetPasswordtxt);
        EditText phone_editText=dialog.findViewById(R.id.phone_editText);
        Button submitBtn=dialog.findViewById(R.id.submitBtn);

        countrycode_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryPickerDialog countryPicker = new CountryPickerDialog(LoginActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country, int flagResId) {
                        countrycode_txt.setText("+"+country.getDialingCode());
                        countrycode="+"+country.getDialingCode();
                        // TODO handle callback
                    }
                });
                countryPicker.show();


            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phone_editText.getText().toString().trim().isEmpty() && phone_editText.getText().toString().trim().length()==10)
                {
                    dailogHelper.showDailog();
                    RetroInterface api_service=RetrofitInit.getConnect().createConnection();
                    Signup signup=new Signup();
                    signup.setMobileNumber(phone_editText.getText().toString().trim());
                    if(countrycode.equalsIgnoreCase(""))
                    {
                        countrycode="+91";
                        signup.setCountryCode(countrycode);
                    }
                    else
                    {
                        signup.setCountryCode(countrycode);
                    }

                    signup.setType("Forgot");
                    signup.setLangCode("en");
//                           signup.setUserId(SharedPreferenceWriter.getInstance(LoginActivity.this).getString(GlobalVariables._id));
                    Call<Signup> call=api_service.checkUserPhoneNumber(signup);
                    call.enqueue(new Callback<Signup>() {
                        @Override
                        public void onResponse(Call<Signup> call, Response<Signup> response) {
                            if(response.isSuccessful())
                            {
                                Signup server_response=response.body();
                                if(server_response.getStatus().equalsIgnoreCase("SUCCESS"))
                                {
                                    dialog.dismiss();
                                    dailogHelper.dismissDailog();
                                    if(server_response.getResponse_message().equalsIgnoreCase("Mobile number is registered")) {
                                        setPreferencesForSignup(server_response);
                                        sendVerificationCode(phone_editText.getText().toString().trim());
                                        final Dialog dialog=new Dialog(LoginActivity.this,android.R.style.Theme_Black);
                                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.setContentView(R.layout.activity_otp_verify);
                                        LinearLayout closell=dialog.findViewById(R.id.closell);
                                        opt_phone_ed=dialog.findViewById(R.id.opt_phone_ed);
                                        resend_code_txt=dialog.findViewById(R.id.resend_code_txt);

                                        startCountDown();
                                        closell.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        resend_code_txt.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                sendVerificationCode(phone_editText.getText().toString());
                                            }
                                        });




                                        Button verifyBtn=dialog.findViewById(R.id.verifyBtn);

                                        verifyBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(checkValidationOtp())
                                                {
                                                    dailogHelper.showDailog();
                                                    verifyVerificationCode(opt_phone_ed.getText().toString().trim(),dialog);

                                                }

                                            }
                                        });

                                        dialog.show();
                                    }
                                }
                                else if(server_response.getStatus().equalsIgnoreCase("FAILURE"))
                                {
                                    //dialog.dismiss();
                                    dailogHelper.dismissDailog();

                                    if (server_response.getResponse_message().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                        Toast.makeText(LoginActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                        SharedPreferenceWriter.getInstance(LoginActivity.this).clearPreferenceValues();
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    // Log.w(TAG, "getInstanceId failed", task.getException());
                                                    return;
                                                }

                                                String auth_token = task.getResult().getToken();
                                                Log.w("firebaese", "token: " + auth_token);
                                                SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                            }
                                        });
                                    }
                                    else {
                                        phone_editText.setError(server_response.getResponse_message());
                                        phone_editText.setFocusable(true);
                                        phone_editText.requestFocus();
                                    }




//                                           mobile_number_ed.setError("Invalid mobile number");
//                                           password_ed.setError("Invalid password");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Signup> call, Throwable t) {

                        }
                    });


                }
                else
                {
                    if(phone_editText.getText().toString().isEmpty() || phone_editText.getText().toString().trim().length()!=10)
                    {
                        if(phone_editText.getText().toString().isEmpty())
                        {
                            phone_editText.setError("Please enter mobile number");
                            phone_editText.setFocusable(true);
                            phone_editText.requestFocus();
                        }
                        else if(phone_editText.getText().toString().trim().length()!=10)
                        {
                            phone_editText.setError("Please enter valid mobile number");
                            phone_editText.setFocusable(true);
                            phone_editText.requestFocus();
                        }
                    }


                }

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

    private boolean checkValidationOtp() {
        boolean ret=true;

        if(!Validation.hasText(opt_phone_ed,getString(R.string.please_enter_otp))
                || opt_phone_ed.getText().toString().length()!=6
        )
        {
            if(!Validation.hasText(opt_phone_ed,getString(R.string.please_enter_otp)))
            {
                ret=false;
                opt_phone_ed.requestFocus();
            }
            else if(opt_phone_ed.getText().toString().length()!=6)
            {
                ret=false;
                opt_phone_ed.setError(getString(R.string.please_enter_six_digit));
                opt_phone_ed.requestFocus();
                opt_phone_ed.setFocusable(true);

            }


        }


        return ret;

    }

    private void otpVerifyPopup() {
    }

    private void startCountDown() {
        CountDownTimer countDownTimer = null;
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
            countDownTimer.start();
        }
        else {
            countDownTimer = new CountDownTimer(120000, 1000) {

                public void onTick(long l) {
                    //mtime is a textview
//                                                    resend_code_txt.setText(l/60000+":"+l/5000);

                    resend_code_txt.setText(""+String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes( l),
                            TimeUnit.MILLISECONDS.toSeconds(l) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));


                }

                public void onFinish() {
//                                                    dialog.show();
//                                                    //here mnext is the button from which we can get next question.
//
                    resend_code_txt.setText("Resend code");
                    //  resend_code_txt.performClick();//this is used to perform clik automatically

                }
            }.start();
        }



    }

    private void setPreferencesForSignup(Signup server_response) {
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.profilePic,server_response.getData().getProfilePic());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.status,server_response.getData().getStatus());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeBooleanValue(GlobalVariables.notificationStatus,server_response.getData().getNotificationStatus());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.language,server_response.getData().getLanguage());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.totalPoints, String.valueOf(server_response.getData().getTotalPoints()));
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.name,server_response.getData().getName());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.birthPlace,server_response.getData().getBirthPlace());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.email,server_response.getData().getEmail());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.mobileNumber,server_response.getData().getMobileNumber());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.countryCode,server_response.getData().getCountryCode());

        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.dob,server_response.getData().getDob());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.gender,server_response.getData().getGender());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.deviceToken,server_response.getData().getDeviceToken());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.device_type,server_response.getData().getDeviceType());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.maritalStatus,server_response.getData().getMaritalStatus());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.createdAt,server_response.getData().getCreatedAt());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.updatedAt,server_response.getData().getUpdatedAt());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeIntValue(GlobalVariables.__v,server_response.getData().getV());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables._id,server_response.getData().getId());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.jwtToken,server_response.getData().getJwtToken());




    }

    private void verifyVerificationCode(String otp, Dialog dialog) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential,dialog);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, Dialog dialog) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            dailogHelper.dismissDailog();
                            changePasswordPopUp();
                        } else {
                            dailogHelper.dismissDailog();
                            opt_phone_ed.setError(getString(R.string.otp_not_match));
                            opt_phone_ed.setFocusable(true);
                            opt_phone_ed.requestFocus();

                        }
                    }
                });
    }

    private void changePasswordPopUp() {
        final Dialog dialog=new Dialog(LoginActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_change_password);
        TextView oldpass_txt=dialog.findViewById(R.id.oldpass_txt);
        oldpass_txt.setVisibility(View.GONE);
        newpass_txt=dialog.findViewById(R.id.newpass_txt);
        confirmpass_txt=dialog.findViewById(R.id.confirmpass_txt);
        TouchListner();

        TextView changePasswordTextView=dialog.findViewById(R.id.changePasswordTextView);

        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidationChangePassword())
                {

                    RetroInterface api_service=RetrofitInit.getConnect().createConnection();
                    ForgotPassword password=new ForgotPassword();
                    password.setUserId(SharedPreferenceWriter.getInstance(LoginActivity.this).getString(GlobalVariables._id));
                    password.setPassword(newpass_txt.getText().toString().trim());
                    password.setLangCode("en");
                    Call<ForgotPassword> call= api_service.userResetPassword(password);
                    call.enqueue(new Callback<ForgotPassword>() {
                        @Override
                        public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                            if(response.isSuccessful())
                            {

                                ForgotPassword server_response=response.body();
                                if(server_response.getStatus().equalsIgnoreCase("SUCCESS"))
                                {
                                    dialog.dismiss();
                                    setPreferencesForgotPassword(server_response);
                                    Toast.makeText(LoginActivity.this,""+server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
                                }
                                else if(server_response.getStatus().equalsIgnoreCase("FAILURE"))
                                {
                                    if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                        Toast.makeText(LoginActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                        SharedPreferenceWriter.getInstance(LoginActivity.this).clearPreferenceValues();
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    // Log.w(TAG, "getInstanceId failed", task.getException());
                                                    return;
                                                }

                                                String auth_token = task.getResult().getToken();
                                                Log.w("firebaese", "token: " + auth_token);
                                                SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                            }
                                        });
                                    }
                                    else
                                    {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this,""+server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
                                }
                                }

                            }
                        }

                        private void setPreferencesForgotPassword(ForgotPassword server_response) {
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.profilePic,server_response.getData().getProfilePic());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.status,server_response.getData().getStatus());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeBooleanValue(GlobalVariables.notificationStatus,server_response.getData().getNotificationStatus());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.totalPoints, String.valueOf(server_response.getData().getTotalPoints()));
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.name,server_response.getData().getName());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.birthPlace,server_response.getData().getBirthPlace());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.email,server_response.getData().getEmail());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.mobileNumber,server_response.getData().getMobileNumber());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.dob,server_response.getData().getDob());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.gender,server_response.getData().getGender());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.countryCode,server_response.getData().getCountryCode());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.maritalStatus,server_response.getData().getMaritalStatus());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.createdAt,server_response.getData().getCreatedAt());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.updatedAt,server_response.getData().getUpdatedAt());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeIntValue(GlobalVariables.__v,server_response.getData().getV());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables._id,server_response.getData().getId());
                            SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.jwtToken,server_response.getData().getJwtToken());

                        }

                        @Override
                        public void onFailure(Call<ForgotPassword> call, Throwable t) {

                        }
                    });

                }
            }
        });

        dialog.show();
    }

    private void TouchListner() {

        newpass_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newpass_txt.getRight() - newpass_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount3 % 2 ==0)
                        {
                            clickcount3=clickcount3+1;
                            newpass_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            newpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount3=clickcount3+1;
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
                        if(clickcount2 % 2 ==0)
                        {
                            clickcount2=clickcount2+1;
                            confirmpass_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confirmpass_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount2=clickcount2+1;
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

    private boolean checkValidationChangePassword() {
        boolean ret=true;

        if(!Validation.hasText(newpass_txt,getString(R.string.please_enter_new_password))
        || !Validation.hasText(confirmpass_txt,getString(R.string.please_enter_confirm_password))
        || !confirmpass_txt.getText().toString().equalsIgnoreCase(newpass_txt.getText().toString().trim())
        )
        {
            if(!Validation.hasText(newpass_txt,getString(R.string.please_enter_new_password)))
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


    private void sendVerificationCode(String phone_number) {
        if(countrycode.equalsIgnoreCase(""))
        {
            countrycode="+91";
        }
        else
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(countrycode+phone_number, 60, TimeUnit.SECONDS, LoginActivity.this, mCallback);
        }    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d("Completed", "onVerificationCompleted:" + credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.w("Failed", "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {

            } else if (e instanceof FirebaseTooManyRequestsException) {

            }
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
            Log.d("Code Sent", "onCodeSent:" + verificationId);
            verificationCode = verificationId;
           // Toast.makeText(LoginActivity.this,getString(R.string.otp_send_successfully),Toast.LENGTH_LONG).show();
        }
    };


    private void setPreferences(Login server_response) {
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.profilePic,server_response.getData().getProfilePic());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.status,server_response.getData().getStatus());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeBooleanValue(GlobalVariables.notificationStatus,server_response.getData().getNotificationStatus());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.totalPoints, String.valueOf(server_response.getData().getTotalPoints()));
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.name,server_response.getData().getName());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.birthPlace,server_response.getData().getBirthPlace());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.email,server_response.getData().getEmail());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.mobileNumber,server_response.getData().getMobileNumber());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.dob,server_response.getData().getDob());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.gender,server_response.getData().getGender());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.maritalStatus,server_response.getData().getMaritalStatus());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.createdAt,server_response.getData().getCreatedAt());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.updatedAt,server_response.getData().getUpdatedAt());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeIntValue(GlobalVariables.__v,server_response.getData().getV());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables._id,server_response.getData().getId());
        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.jwtToken,server_response.getData().getJwtToken());





    }

    private boolean checkValidation() {
        boolean ret=true;
        if(!Validation.hasText(mobile_number_ed,getString(R.string.enter_mobile_number))
        || !Validation.isPhoneNumber(mobile_number_ed,true)
        || !Validation.hasText(password_ed,getString(R.string.enter_password))
        )
        {
            if(!Validation.hasText(mobile_number_ed,getString(R.string.enter_mobile_number)))
            {
                ret=false;
                mobile_number_ed.requestFocus();
            }
            else if(!Validation.isPhoneNumber(mobile_number_ed,true))
            {
                ret=false;
                mobile_number_ed.requestFocus();

            }
            else if(!Validation.hasText(password_ed,getString(R.string.enter_password)))
            {
                ret=false;
                password_ed.requestFocus();
            }
        }

           return ret;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {



    }

    private void signIn()
    {
        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,req_code);
    }

    private void handleResult(GoogleSignInResult result)
    {

        if(result.isSuccess())
        {
            GoogleSignInAccount  account=result.getSignInAccount();
            String name=account.getDisplayName();
            String email=account.getEmail();
            String id=account.getId();

            String img_url=account.getPhotoUrl().toString();
            progressDialog.setMessage("Checking Google Credential");
            progressDialog.show();

            SocialLogin socialLogin=new SocialLogin();
            socialLogin.setSocialType(GlobalVariables.google);
            socialLogin.setSocialId(id);
            socialLogin.setDeviceType(GlobalVariables.device_type);
            socialLogin.setDeviceToken(deviceToken);
            socialLogin.setEmail(email);
            socialLogin.setProfilePic(img_url);
            socialLogin.setName(name);


            RetroInterface api_service=RetrofitInit.getConnect().createConnection();
            Call<SocialLogin> call=api_service.socialLogin(socialLogin);
            call.enqueue(new Callback<SocialLogin>() {
                @Override
                public void onResponse(Call<SocialLogin> call, Response<SocialLogin> response) {
                    if(response.isSuccessful())
                    {
                        SocialLogin server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase("SUCCESS"))
                        {
                            progressDialog.dismiss();
                           // Toast.makeText(LoginActivity.this,"You have successfully logged in",Toast.LENGTH_LONG).show();



                        }
                        else if(server_response.getStatus().equalsIgnoreCase("FAILURE")) {
                            progressDialog.dismiss();
                            if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                                Toast.makeText(LoginActivity.this, getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                SharedPreferenceWriter.getInstance(LoginActivity.this).clearPreferenceValues();
                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            // Log.w(TAG, "getInstanceId failed", task.getException());
                                            return;
                                        }

                                        String auth_token = task.getResult().getToken();
                                        Log.w("firebaese", "token: " + auth_token);
                                        SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                    }
                                });
                            } else {

                                Toast.makeText(LoginActivity.this, "" + server_response.getResponseMessage(), Toast.LENGTH_LONG).show();
                            }
                        }




                    }
                }

                @Override
                public void onFailure(Call<SocialLogin> call, Throwable t) {

                }
            });



            Intent intent=new Intent(LoginActivity.this,CategorySelectionActivity.class);
            startActivity(intent);




//            Glide.with(this).load(img_url).into()


        }
        else
        {
            updateUI(false);

        }




    }
    private void updateUI(boolean isLogin)
    {
        if(isLogin)
        {




        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==req_code)
        {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }



    }
}
