package com.boushra.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.boushra.Model.UpdateUserProfile;
import com.boushra.Model.User;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.AddServiceBody;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.Validation;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ForcasterForPriceActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.continuetxt) TextView continuetxt;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.name_ed) EditText name_ed;
    @BindView(R.id.pob_ed) EditText pob_ed;
    @BindView(R.id.dob_ed) EditText dob_ed;
    @BindView(R.id.gender_txt) TextView gender_txt;
    @BindView(R.id.recording_im) LottieAnimationView recording_im;
    @BindView(R.id.maritalstatus_txt) TextView maritalstatus_txt;
    @BindView(R.id.seekBar) SeekBar seekBar;
    @BindView(R.id.dream_ed) EditText dream_ed;
    @BindView(R.id.play_iv) ImageView play_iv;
    @BindView(R.id.timer_txt) TextView timer_txt;
    @BindView(R.id.genderSpinner) Spinner genderSpinner;
    @BindView(R.id.maritalSpinner) Spinner maritalSpinner;
    @BindView(R.id.pause_iv) ImageView pause_iv;

    private MediaRecorder mRecorder;
    private Handler handler=new Handler();
    File audio;
    int lastProgress;
    private MediaPlayer mPlayer;
    CountDownTimer timer;
    int lastposition;
    private Handler mHandler = new Handler();
    boolean recording=false;
    final int PERMISSION_REQUEST_CODE2 = 400;
    String forcasterId="",categoryName="";
    String points="";
    String fileName;
    int clickcount=0;
    boolean playing;
    List<String> genderlist;
    List<String> martailList;
    boolean updateprofileApi;
    int lastplayposition=0;
    boolean pause=false;
    private DatePickerDialog.OnDateSetListener datePickerListener;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcaster_for_price_yes);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        getUserDeatilsApi();
        spinnerClick();
        datePickerClick();



    }
    private void datePickerClick() {

        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                Date date= null;
                try {
                    date = simpleDateFormat.parse(dayOfMonth+"-"+(month+1)+"-"+year);

                    dob_ed.setText(simpleDateFormat.format(date));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
    }


    private void spinnerClick() {
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_txt.setText(genderlist.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        maritalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maritalstatus_txt.setText(martailList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void getUserDeatilsApi() {
        if(new InternetCheck(this).isConnect())
        {
            ProgressDailogHelper dailogHelper=new ProgressDailogHelper(this,"Searching Details");
            dailogHelper.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            User user=new User();
            user.setUserId(SharedPreferenceWriter.getInstance(ForcasterForPriceActivity.this).getString(GlobalVariables._id));
            user.setLangCode("en");
            Call<User> call=api_service.getUserDetails(user,SharedPreferenceWriter.getInstance(ForcasterForPriceActivity.this).getString(GlobalVariables.jwtToken));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful())
                    {
                        dailogHelper.dismissDailog();
                        User server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                            if(server_response.getData().getName()==null || server_response.getData().getName().equalsIgnoreCase(""))
                            {
                                updateprofileApi=true;
                            }
                            else {
                                name_ed.setText(server_response.getData().getName());
                                pob_ed.setText(server_response.getData().getBirthPlace());
                                dob_ed.setText(server_response.getData().getDob());
                                gender_txt.setText(server_response.getData().getGender());
                                maritalstatus_txt.setText(server_response.getData().getMaritalStatus());
                                name_ed.setFocusable(false);
                                pob_ed.setFocusable(false);
                                dob_ed.setOnClickListener(null);
                                gender_txt.setEnabled(false);
                                maritalstatus_txt.setEnabled(false);
                            }

                        }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            Toast.makeText(ForcasterForPriceActivity.this,server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });




        }
        else
        {
            Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_LONG).show();
        }

    }

    private void init() {
        continuetxt.setOnClickListener(this::onClick);
        backLL.setOnClickListener(this::onClick);
        recording_im.setOnClickListener(this::onClick);
        forcasterId=getIntent().getStringExtra(GlobalVariables.forecasterId);
        points=getIntent().getStringExtra(GlobalVariables.points);
        categoryName=getIntent().getStringExtra(GlobalVariables.categoryName);
        play_iv.setOnClickListener(this::onClick);
        seekBar.setOnSeekBarChangeListener(this);
        gender_txt.setOnClickListener(this::onClick);
        maritalstatus_txt.setOnClickListener(this::onClick);
        dob_ed.setOnClickListener(this::onClick);
        pause_iv.setOnClickListener(this::onClick);
    }

    @OnClick()
    void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.continuetxt:
                if(checkValidation())
                {
                    if(updateprofileApi)
                    {
                       updateProfileAPi();
                    }
                    else {

                      intent();
                    }
                }


                break;

            case R.id.backLL:
                String activity=getIntent().getStringExtra("ForecasterDetails");
                if(activity!=null) {
                    if (activity.equalsIgnoreCase("Yes")) {
                        Intent intent1 = new Intent(ForcasterForPriceActivity.this, ForecasterDetailsActivity.class);
                        finish();
                        startActivity(intent1);
                    }
                }else
                {
                    super.onBackPressed();
                }
                break;


            case R.id.recording_im:
                if(checkingPermissionAudio()) {
                    if(clickcount%2==0)
                    {
                        clickcount=clickcount+1;
                        startRecording();
                    }
                    else
                    {
                        clickcount=clickcount+1;
                        stopRecording();
                    }

                }

                break;

            case R.id.play_iv:
                playingAudio();
                break;

            case R.id.gender_txt:
                GenderSpinner();
                break;

            case R.id.maritalstatus_txt:
                MaritalStatusSpinner();
                break;

            case R.id.dob_ed:
                Date date= Calendar.getInstance().getTime();
                new DatePickerDialog(ForcasterForPriceActivity.this, AlertDialog.THEME_HOLO_LIGHT,datePickerListener,Integer.parseInt(new SimpleDateFormat("yyyy").format(date)),
                        Integer.parseInt(new SimpleDateFormat("MM").format(date))-1,Integer.parseInt(new SimpleDateFormat("dd").format(date))).show();

                break;

            case R.id.pause_iv:
                pauseAudio();
                break;


        }
    }

    private void pauseAudio() {
        pause=true;
        play_iv.setVisibility(View.VISIBLE);
        pause_iv.setVisibility(View.GONE);
        if (mPlayer.isPlaying())
        {
            mPlayer.pause();
            lastplayposition=mPlayer.getCurrentPosition();

        }

    }

    private void updateProfileAPi() {
        if(new InternetCheck(this).isConnect())
        {
            RetroInterface api_service=RetrofitInit.getConnect().createConnection();
            UpdateUserProfile userProfile=new UpdateUserProfile();
            userProfile.setUserId(SharedPreferenceWriter.getInstance(this).getString(GlobalVariables._id));
            userProfile.setName(name_ed.getText().toString().trim());
            userProfile.setBirthPlace(pob_ed.getText().toString().trim());
            userProfile.setDob(dob_ed.getText().toString().trim());
            userProfile.setGender(gender_txt.getText().toString());
            userProfile.setMaritalStatus(maritalstatus_txt.getText().toString());
            AddServiceBody body=new AddServiceBody(userProfile);
            Call<UpdateUserProfile> call=api_service.updateUserProfile(null,body.getBody());
            call.enqueue(new Callback<UpdateUserProfile>() {
                @Override
                public void onResponse(Call<UpdateUserProfile> call, Response<UpdateUserProfile> response) {
                    if(response.isSuccessful())
                    {
                        UpdateUserProfile server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                            intent();
                        }
                        else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            Toast.makeText(ForcasterForPriceActivity.this,server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<UpdateUserProfile> call, Throwable t) {

                }
            });



        }
        else
        {
            Toast toast=Toast.makeText(this,getString(R.string.check_internet),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    private void intent() {
        Intent intent = new Intent(ForcasterForPriceActivity.this, PaymentActivity.class);
        intent.putExtra(GlobalVariables.forecasterId, forcasterId);
        intent.putExtra(GlobalVariables.userId, SharedPreferenceWriter.getInstance(ForcasterForPriceActivity.this).getString(GlobalVariables._id));
        intent.putExtra(GlobalVariables.points, points);
        intent.putExtra(GlobalVariables.categoryName, categoryName);
        intent.putExtra(GlobalVariables.dream, dream_ed.getText().toString());
        intent.putExtra(GlobalVariables.voice, fileName);
        finish();
        startActivity(intent);
    }

    private void MaritalStatusSpinner() {
        martailList = new ArrayList<>();
        martailList.add("Please select");
        martailList.add("Single");
        martailList.add("Married");

        ArrayAdapter genderArrayAdapter = new ArrayAdapter(ForcasterForPriceActivity.this, android.R.layout.simple_spinner_dropdown_item, martailList){
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

        ArrayAdapter genderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderlist) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
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

    private void playingAudio() {
        try {
            play_iv.setVisibility(View.GONE);
            pause_iv.setVisibility(View.VISIBLE);

            if(timer!=null) {
                timer.cancel();

            }
            recording=false;
            playing=true;
            mPlayer = new MediaPlayer();
            if(fileName!=null) {
                mPlayer.setDataSource(fileName);
                mPlayer.prepare();
                if(pause) {
                    mPlayer.seekTo(lastplayposition);
                }
                mPlayer.start();
                seekBar.setMax(mPlayer.getDuration());
            }
            else
            {
                Toast toast=Toast.makeText(ForcasterForPriceActivity.this,"Please record your audio first",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                seekBar.setMax(0);
            }

            seekBar.setProgress(0);
            seekBar.setClickable(false);
            seekUpdation();

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    play_iv.setVisibility(View.VISIBLE);
                    pause_iv.setVisibility(View.GONE);
                    pause=false;

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };


    private void seekUpdation() {
        if(playing) {
            if (mPlayer != null) {
                int mCurrentPosition = mPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);
            }
            handler.postDelayed(runnable, 100);
        }

    }


    private void stopRecording() {
        try {
            playing=false;
            recording=false;
            mRecorder.stop();
            mRecorder.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;

        seekBar.setMax(300);
        if(timer!=null) {
            timer.cancel();
        }
        recording_im.pauseAnimation();
        recording_im.cancelAnimation();
        recording_im.setImageDrawable(getDrawable(R.drawable.mike_ic));
        seekBar.setClickable(false);
    }



    private boolean checkValidation() {
        boolean ret=true;
        if(!Validation.hasText(name_ed)) ret=false;
        if(!Validation.hasText(pob_ed)) ret=false;
        if(!Validation.hasText(dob_ed)) ret=false;
        if(gender_txt.getText().toString().equalsIgnoreCase(getString(R.string.gender)))
        {
         ret=false;
         gender_txt.setError("Please select gender");
         gender_txt.setFocusable(true);
         gender_txt.requestFocus();
        }
        if(maritalstatus_txt.getText().toString().equalsIgnoreCase(getString(R.string.marital_status)))
        {
            ret=false;
            maritalstatus_txt.setError("Please select marital status");
            maritalstatus_txt.setFocusable(true);
            maritalstatus_txt.requestFocus();

        }


        if(!Validation.hasText(dream_ed)) ret=false;
        if(audio==null)
        {
            ret=false;
            Toast.makeText(ForcasterForPriceActivity.this,"Please record your voice note",Toast.LENGTH_LONG).show();
        }


        return ret;
    }

    private boolean checkingPermissionAudio() {
        boolean ret = true;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ret = false;


                requestPermissions(new String[]
                        {
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSION_REQUEST_CODE2);

            } else {

                ret = true;
            }
        }
        return ret;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE2:
                if(grantResults.length>0)
                {
                    boolean audioaccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted=grantResults[2]==PackageManager.PERMISSION_GRANTED;

                    if(audioaccepted && readAccepted && writeAccepted)
                    {
                        Toast.makeText(ForcasterForPriceActivity.this,"Recording Started",Toast.LENGTH_LONG).show();
                        clickcount=clickcount+1;
                        startRecording();
                    }
                    else
                    {
                        Toast.makeText(ForcasterForPriceActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }


                break;
        }
    }

    private void startRecording() {
        recording=true;
        playing=false;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
         * and the audios are being stored in the Audios folder **/
        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
        if (!file.exists()) {
            file.mkdirs();
        }

        fileName =  root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" +
                String.valueOf(System.currentTimeMillis() + ".mp3");
        Log.d("filename",fileName);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        audio=new File(fileName);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stopPlaying();
        lastProgress = 0;
        seekBar.setProgress(lastProgress);
        seekBar.setMax(300);
        seekBar.setClickable(false);

        lastposition=0;
        seekUpdation2();
        try {
            recording_im.setAnimation("recording.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        recording_im.setProgress(0);
        recording_im.playAnimation();




        timer=new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                if(recording) {
                    recording_im.cancelAnimation();
                    recording_im.setImageDrawable(getDrawable(R.drawable.mike_ic));
                    stopPlaying();
                    stoppingAudio();
                    Toast.makeText(ForcasterForPriceActivity.this, "You can record upto 30 secounds", Toast.LENGTH_LONG).show();
                }
            }
        }.start();




    }

    private void stoppingAudio() {
        try
        {
        mRecorder.stop();
        mRecorder.release();
        //chromometer.stop();

    } catch (Exception e) {
        e.printStackTrace();
    }
    mRecorder = null;

}

    Runnable newthread=new Runnable() {
        @Override
        public void run() {
            if(recording) {
                seekUpdation2();
            }
        }
    };

    private void seekUpdation2() {
        lastposition++;
        seekBar.setProgress(lastposition);
        mHandler.postDelayed(newthread,100);

    }

    private void stopPlaying() {

        try{
            mPlayer.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mPlayer = null;
        //showing the play button

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(playing) {
            if (progress / 1000 < 10) {
                timer_txt.setText("0:0" + progress / 1000);
            } else {
                timer_txt.setText("0:" + progress / 1000);
            }
        }
        else
        {
            if (progress / 10 < 10) {
                timer_txt.setText("0:0" + progress / 10);
            } else {
                timer_txt.setText("0:" + progress / 10);
            }



        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
