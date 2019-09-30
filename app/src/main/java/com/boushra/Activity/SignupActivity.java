package com.boushra.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.boushra.Model.Signup;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {
    Spinner genderSpinner,maritalSpinner;
    List<String> martailList;
    List<String> genderlist ;
    @BindView(R.id.phone_editText) EditText phone_editText;
    @BindView(R.id.password_editText) EditText password_editText;
    @BindView(R.id.cpasswd_editText) EditText cpasswd_editText;
    EditText opt_phone_ed;
    TextView maritalTextView,genderTextView;
    EditText name_editText,email_edittext,dob_editText,pob_editText;
    String password,confirm_password,phone_number,auth_token;
    Signup signup;
    FirebaseAuth auth;
    private String verificationCode;
    ProgressDialog progresBar;
    @BindView(R.id.country_txt) TextView country_txt;
    String countrycode="";
    long clickcount=0;
    long clickcount2=0;
    TextView resend_code_txt;
    private ProgressDailogHelper dailogHelper;
    private DatePickerDialog.OnDateSetListener datePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        auth_token=SharedPreferenceWriter.getInstance(SignupActivity.this).getString(GlobalVariables.firebase_token);
        auth=FirebaseAuth.getInstance();
        dailogHelper=new ProgressDailogHelper(this,"");
        signup=new Signup();

        TouchListner();
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Date date= null;
                try {
                    date = simpleDateFormat.parse(dayOfMonth+"/"+(month+1)+"/"+year);
                    StringBuilder builder=new StringBuilder();
                    builder.append(simpleDateFormat.format(date));
                    dob_editText.setText(builder);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };


    }

    private void TouchListner() {
        password_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password_editText.getRight() - password_editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount % 2 ==0)
                        {
                            clickcount=clickcount+1;
                            password_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            password_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount=clickcount+1;
                            password_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.un_view, 0);
                            return true;


                        }

                    }
                }


                return false;
            }
        });
        cpasswd_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (cpasswd_editText.getRight() - cpasswd_editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(clickcount2 % 2 ==0)
                        {
                            clickcount2=clickcount2+1;
                            cpasswd_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            cpasswd_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.view, 0);
                            return true;
                        }
                        else
                        {
                            clickcount2=clickcount2+1;
                            cpasswd_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            cpasswd_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.un_view, 0);
                            return true;


                        }

                    }
                }


                return false;
            }
        });
    }


    @OnClick({R.id.log_in,R.id.singupBtn,R.id.googleLL,R.id.facebookll,R.id.country_txt})
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.log_in:
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                break;

            case R.id.singupBtn:


                phone_number=phone_editText.getText().toString();
                confirm_password=cpasswd_editText.getText().toString();
                password= password_editText.getText().toString();
                if(checkValidation())
                {
                    checkUserPhoneNumberApi();


                }





                break;

            case R.id.googleLL:
                Intent intent1=new Intent(SignupActivity.this,CategorySelectionActivity.class);
                startActivity(intent1);
                break;

            case R.id.facebookll:
                Intent intent2=new Intent(SignupActivity.this,CategorySelectionActivity.class);
                startActivity(intent2);
                break;

            case R.id.country_txt:
                CountryPickerDialog countryPicker =
                        new CountryPickerDialog(SignupActivity.this, new CountryPickerCallbacks() {
                            @Override
                            public void onCountrySelected(Country country, int flagResId) {
                                country_txt.setText("+"+country.getDialingCode());
                                countrycode=country_txt.getText().toString();
                                // TODO handle callback
                            }
                        });
                countryPicker.show();


                break;
        }


    }

    private void checkUserPhoneNumberApi() {
        if(password.equalsIgnoreCase(confirm_password))
        {
            dailogHelper.showDailog();
            RetroInterface apiService =RetrofitInit.getConnect().createConnection();
            signup.setMobileNumber(phone_number);
            if(countrycode.equalsIgnoreCase(""))
            {
                countrycode="+91";
                signup.setCountryCode(countrycode);
            }
            else
            {
                signup.setCountryCode(countrycode);
            }
            // signup.setCountryCode(countrycode);
            signup.setType("Signup");
            signup.setLangCode("en");
            Call<Signup> call=apiService.checkUserPhoneNumber(signup);
            call.enqueue(new Callback<Signup>() {
                @Override
                public void onResponse(Call<Signup> call, Response<Signup> response) {
                    if(response.isSuccessful())
                    {
                        Signup server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase("SUCCESS")) {
                            sendVerificationCode(phone_number);
                            dailogHelper.dismissDailog();
                            otpVerifyPopup();
//                             termConditionPopup();

                        }
                        else if(server_response.getStatus().equalsIgnoreCase("FAILURE"))
                        {
                            dailogHelper.dismissDailog();
                            if(server_response.getResponse_message().equalsIgnoreCase(GlobalVariables.invalidoken))
                            {
                                Toast.makeText(SignupActivity.this,getString(R.string.other_device_logged_in),Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                SharedPreferenceWriter.getInstance(SignupActivity.this).clearPreferenceValues();
                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            // Log.w(TAG, "getInstanceId failed", task.getException());
                                            return;
                                        }

                                        String auth_token = task.getResult().getToken();
                                        Log.w("firebaese","token: "+auth_token);
                                        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.firebase_token,auth_token);
                                    }
                                });
                            }
                            else {


                                phone_editText.setError(server_response.getResponse_message());
                                phone_editText.setFocusable(true);
                                phone_editText.requestFocus();
                                password_editText.setText("");
                                cpasswd_editText.setText("");
                                //Toast.makeText(SignupActivity.this,""+response.body().getResponse_message(),Toast.LENGTH_LONG).show();
                            }
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
            cpasswd_editText.setError("Confirm password does not match");
            cpasswd_editText.setFocusable(true);

        }

    }

    private void otpVerifyPopup() {
        Dialog dialog = new Dialog(SignupActivity.this, android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_otp_verify);
        resend_code_txt = dialog.findViewById(R.id.resend_code_txt);

        startCountDown();
        LinearLayout closell = dialog.findViewById(R.id.closell);
        opt_phone_ed = dialog.findViewById(R.id.opt_phone_ed);


        Button verifyBtn = dialog.findViewById(R.id.verifyBtn);
        resend_code_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                                dialog.dismiss();
                sendVerificationCode(phone_number);
                startCountDown();





            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidationOtp()) {

                    dailogHelper.showDailog();
                    verifyVerificationCode(opt_phone_ed.getText().toString().trim(),dialog);
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

    private void startCountDown() {
        CountDownTimer  countDownTimer = null;
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

    private void sendVerificationCode(String phone_number) {
        if(countrycode.equalsIgnoreCase(""))
        {
            countrycode="+91";
        }
        else
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(countrycode+phone_number, 60, TimeUnit.SECONDS, SignupActivity.this, mCallback);
        }
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d("Completed", "onVerificationCompleted:" + credential);

//            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("Failed", "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(String verificationId,
                PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("Code Sent", "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            verificationCode = verificationId;
           // Toast.makeText(SignupActivity.this,getString(R.string.otp_send_successfully),Toast.LENGTH_LONG).show();
//            mResendToken = token;

            // ...
        }
    };


    private void verifyVerificationCode(String otp, Dialog dialog) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
        signInWithPhoneAuthCredential(credential,dialog);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, Dialog dialog) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            dailogHelper.dismissDailog();
                            signupApi();

//                            termConditionPopup();
                        }
                else    {

                            dailogHelper.dismissDailog();
                            opt_phone_ed.setError(getString(R.string.otp_not_match));
                            opt_phone_ed.requestFocus();
                            opt_phone_ed.setFocusable(true);
                        }
                    }
                });
    }

    private void signupApi() {
        dailogHelper.showDailog();
        if(countrycode.equalsIgnoreCase(""))
        {
            signup.setCountryCode("+91");
        }
        else
        {
            signup.setCountryCode(countrycode);
        }
        signup.setMobileNumber(phone_editText.getText().toString().trim());
        signup.setPassword(password_editText.getText().toString().trim());

        signup.setDeviceToken(auth_token);
        signup.setDeviceType("Android");
        RetroInterface apiService =RetrofitInit.getConnect().createConnection();
        Call<Signup> call=apiService.sendSingupData(signup);
        call.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                if(response.isSuccessful())
                {

                    Signup server_response=response.body();
                    if(server_response.getStatus().equalsIgnoreCase("SUCCESS"))
                    {
                        dailogHelper.dismissDailog();
                        Toast.makeText(SignupActivity.this,server_response.getResponse_message(),Toast.LENGTH_LONG).show();
                        Intent log_in = new Intent(SignupActivity.this, CategorySelectionActivity.class);
                        setPreferences(server_response);
                        finish();
                        startActivity(log_in);

                    }
                    else if(server_response.getStatus().equalsIgnoreCase("FAILURE"))
                    {
//                                                        dialog1.dismiss();
                        dailogHelper.dismissDailog();
                        if(server_response.getResponse_message().equalsIgnoreCase(GlobalVariables.invalidoken))
                        {
                            Toast.makeText(SignupActivity.this,getString(R.string.other_device_logged_in),Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            SharedPreferenceWriter.getInstance(SignupActivity.this).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese","token: "+auth_token);
                                    SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.firebase_token,auth_token);
                                }
                            });
                        }
                        else{
                            Toast.makeText(SignupActivity.this,server_response.getResponse_message(),Toast.LENGTH_LONG).show();
                    }
                    }
                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {

            }
        });



    }

    private void termConditionPopup() {
        Dialog dialog1 = new Dialog(SignupActivity.this, android.R.style.Theme_Black);
        dialog1.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.activity_terms_condition);
        genderSpinner = dialog1.findViewById(R.id.genderSpinner);
        maritalSpinner = dialog1.findViewById(R.id.maritalSpinner);
        name_editText = dialog1.findViewById(R.id.name_editText);
        email_edittext = dialog1.findViewById(R.id.email_edittext);
        pob_editText = dialog1.findViewById(R.id.pob_editText);
        maritalTextView = dialog1.findViewById(R.id.maritalTextView);
        genderTextView = dialog1.findViewById(R.id.genderTextView);
        dob_editText=dialog1.findViewById(R.id.dob_editText);
        dob_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date= Calendar.getInstance().getTime();
                new DatePickerDialog(SignupActivity.this, android.app.AlertDialog.THEME_HOLO_LIGHT,datePickerListener,Integer.parseInt(new SimpleDateFormat("yyyy").format(date)),
                        Integer.parseInt(new SimpleDateFormat("MM").format(date))-1,Integer.parseInt(new SimpleDateFormat("dd").format(date))).show();



            }
        });
        maritalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MartialStatusSpinner();
            }
        });
        genderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderSpinner();
            }
        });

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderTextView.setText(genderlist.get(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        maritalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maritalTextView.setText(martailList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ImageView checkbox_im = dialog1.findViewById(R.id.checkbox_im);
        checkbox_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getDrawable(R.drawable.checked));

            }
        });


        Button saveBtn = dialog1.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation2()) {
                }
            }
        });
        dialog1.show();
        //

    }

    private void setPreferences(Signup server_response) {
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.profilePic,server_response.getData().getProfilePic());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.status,server_response.getData().getStatus());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeBooleanValue(GlobalVariables.notificationStatus,server_response.getData().getNotificationStatus());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.totalPoints, String.valueOf(server_response.getData().getTotalPoints()));
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables._id,server_response.getData().getId());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.name,server_response.getData().getName());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.birthPlace,server_response.getData().getBirthPlace());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.email,server_response.getData().getEmail());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.countryCode,server_response.getData().getCountryCode());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.mobileNumber,server_response.getData().getMobileNumber());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.password,server_response.getData().getPassword());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.dob,server_response.getData().getDob());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.gender,server_response.getData().getGender());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.maritalStatus,server_response.getData().getMaritalStatus());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.deviceToken,server_response.getData().getDeviceToken());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.device_type,server_response.getData().getDeviceType());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.jwtToken,server_response.getData().getJwtToken());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.createdAt,server_response.getData().getCreatedAt());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeStringValue(GlobalVariables.updatedAt,server_response.getData().getUpdatedAt());
        SharedPreferenceWriter.getInstance(SignupActivity.this).writeIntValue(GlobalVariables.__v,server_response.getData().getV());



    }

    private boolean checkValidation2() {

        boolean ret=true;
        if(name_editText.getText().toString().trim().isEmpty())
        {
                name_editText.setError("Please enter name");
                name_editText.requestFocus();
                name_editText.setFocusable(true);
                ret = false;
        }
        if(email_edittext.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email_edittext.getText().toString().trim()).matches())
        {
            if(email_edittext.getText().toString().trim().isEmpty())
            {
                email_edittext.setError("Please enter valid email");
                email_edittext.requestFocus();
                email_edittext.setFocusable(true);
                ret=false;
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email_edittext.getText().toString().trim()).matches())
            {
                email_edittext.setError("Please enter valid email");
                email_edittext.requestFocus();
                email_edittext.setFocusable(true);
                ret=false;
            }

        }

        if(dob_editText.getText().toString().trim().isEmpty())
        {
            dob_editText.setError("Please enter date of birth");
            dob_editText.setFocusable(true);
            dob_editText.requestFocus();
            ret=false;
        }
        if(pob_editText.getText().toString().trim().isEmpty())
        {
            pob_editText.setError("Please enter place of birth");
            pob_editText.setFocusable(true);
            pob_editText.requestFocus();
            ret=false;
        }

        if(genderTextView.getText().toString().equalsIgnoreCase("Gender"))
        {
            genderTextView.setError("Please select gender");
            genderTextView.setFocusable(true);
            genderTextView.requestFocus();
            ret=false;
        }

        if(maritalTextView.getText().toString().equalsIgnoreCase("Marital Status"))
        {
            maritalTextView.setError("Please select Marital Status");
            maritalTextView.requestFocus();
            maritalTextView.setFocusable(true);
            ret=false;
        }


        return ret;
    }

    private boolean checkValidation() {
        boolean ret=true;

        if(!Validation.hasText(phone_editText,getString(R.string.enter_mobile_number))
        || !Validation.isPhoneNumber(phone_editText,true)
        || !Validation.hasText(password_editText,getString(R.string.please_enter_password))
        || !Validation.hasText(cpasswd_editText,getString(R.string.please_enter_confirm_password))
        || !cpasswd_editText.getText().toString().trim().equalsIgnoreCase(password_editText.getText().toString().trim())
        )
        {
           if(!Validation.hasText(phone_editText,getString(R.string.enter_mobile_number)))
            {
                ret=false;
                phone_editText.requestFocus();
            }
            else if(!Validation.isPhoneNumber(phone_editText,true))
           {
               ret=false;
               phone_editText.requestFocus();
           }
           else if(!Validation.hasText(password_editText,getString(R.string.please_enter_password)))
            {
                ret=false;
                password_editText.requestFocus();

            }
           else if(!Validation.hasText(cpasswd_editText,getString(R.string.please_enter_confirm_password)))
            {
                ret=false;
                cpasswd_editText.requestFocus();

            }
           else if(!cpasswd_editText.getText().toString().trim().equalsIgnoreCase(password_editText.getText().toString().trim()))
           {
               ret=false;
               cpasswd_editText.setError(getString(R.string.confirm_password_not_match));
               cpasswd_editText.requestFocus();
               cpasswd_editText.setFocusable(true);
           }
        }


        return ret;
    }

    private void MartialStatusSpinner() {
        martailList = new ArrayList<>();
        martailList.add("Please select");
        martailList.add("Single");
        martailList.add("Married");

        ArrayAdapter genderArrayAdapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_spinner_dropdown_item, martailList){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.TRANSPARENT);

                // Return the view
                return tv;
            }
        };

        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalSpinner.setAdapter(genderArrayAdapter);
        maritalSpinner.performClick();
    }

    private void GenderSpinner() {

        genderlist = new ArrayList<>();
        genderlist.add("Please select");
        genderlist.add("Male");
        genderlist.add("Female");

        ArrayAdapter genderArrayAdapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_spinner_dropdown_item, genderlist){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.TRANSPARENT);

                // Return the view
                return tv;
            }
        };

        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderArrayAdapter);
        genderSpinner.performClick();
    }

}
