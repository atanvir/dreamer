package com.boushra.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Adapter.ForecasterDetailsAdapter;
import com.boushra.Model.*;
import com.boushra.Model.ForcasterDetails;
import com.boushra.Model.ForecasterRating.ForecasterRating;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecasterDetailsActivity extends AppCompatActivity {

    LinearLayoutManager manager;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.recView) RecyclerView recView;
    @BindView(R.id.ask_question_txt) TextView ask_question_txt;
    @BindView(R.id.profilepic_iv) CircleImageView profilepic_iv;
    @BindView(R.id.name_txt) TextView name_txt;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.video_iv) ImageView video_iv;
    @BindView(R.id.aboutus_txt) TextView aboutus_txt;
    @BindView(R.id.videoview) VideoView videoview;
    @BindView(R.id.play_iv) ImageView play_iv;
    @BindView(R.id.playaudio_iv) ImageView playaudio_iv;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    @BindView(R.id.seekBar) SeekBar seekBar;
    @BindView(R.id.timer_txt) TextView timer_txt;
    @BindView(R.id.stop_iv) ImageView stop_iv;
    @BindView(R.id.chronometer) Chronometer chronometer;
    @BindView(R.id.pause_iv) ImageView pause_iv;
    Uri video_uri,audio_uri;
    MediaPlayer mediaPlayer;
    boolean playing=false;
    int progress=0;
    private Handler handler=new Handler();
    int lastplay_position=0;
    boolean pause=false;
    int mCurrentPosition=0;
    ProgressDailogHelper dailog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecaster_details);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();

        getForecasterDetailsApi();


    }

    private void getForecasterDetailsApi() {
        if(new InternetCheck(this).isConnect())
        {
            dailog.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            ForcasterDetails details=new ForcasterDetails();
            details.setForecasterId(getIntent().getStringExtra(GlobalVariables.forecasterId));
            details.setUserId(SharedPreferenceWriter.getInstance(this).getString(GlobalVariables._id));
            details.setLangCode("en");
            Call<ForcasterDetails> call= api_service.getForecasterDetails(details,SharedPreferenceWriter.getInstance(this).getString(GlobalVariables.jwtToken));
       //     Call<ForcasterDetails> call=api_service.getForecasterRating(details);

            call.enqueue(new Callback<ForcasterDetails>() {
                @Override
                public void onResponse(Call<ForcasterDetails> call, Response<ForcasterDetails> response) {
                    if(response.isSuccessful())
                    {

                        ForcasterDetails server_resposne=response.body();
                        if(server_resposne.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {



                            audio_uri=Uri.parse(server_resposne.getData().getVoiceRecording());
                            settingSeekbar();
                            Glide.with(ForecasterDetailsActivity.this).load(server_resposne.getData().getProfilePic()).into(profilepic_iv);
                            name_txt.setText(server_resposne.getData().getName());
                            ratingBar.setRating(server_resposne.getData().getAvgRating());
                            aboutus_txt.setText(server_resposne.getData().getAboutUs());
                            try {
                                Bitmap bitmap=retriveVideoFrameFromVideo(server_resposne.getData().getUploadedVideo());
                                video_iv.setImageBitmap(bitmap);
                                video_uri=Uri.parse(server_resposne.getData().getUploadedVideo());

                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                            dailog.dismissDailog();
                            getForcasterRatingApi();



                        }else if(server_resposne.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            dailog.dismissDailog();
                            Toast.makeText(ForecasterDetailsActivity.this,server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<ForcasterDetails> call, Throwable t) {

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

    private void getForcasterRatingApi() {
        dailog.showDailog();
        RetroInterface api_serive=RetrofitInit.getConnect().createConnection();
        ForcasterDetails details=new ForcasterDetails();
        details.setForecasterId(getIntent().getStringExtra(GlobalVariables.forecasterId));
        Call<ForecasterRating> call=api_serive.getForecasterRating(details);
        call.enqueue(new Callback<ForecasterRating>() {
            @Override
            public void onResponse(Call<ForecasterRating> call, Response<ForecasterRating> response) {
            if(response.isSuccessful())
            {
               dailog.dismissDailog();
               ForecasterRating server_response=response.body();
               if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
               {

                   settingAdapter(server_response.getData());


               }
               else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
               {
                   Toast.makeText(ForecasterDetailsActivity.this,server_response.getResponseMessage(),Toast.LENGTH_LONG).show();
               }

            }
            }

            @Override
            public void onFailure(Call<ForecasterRating> call, Throwable t) {
                Log.e("error", String.valueOf(t.getMessage()));

            }
        });

    }

    private void settingAdapter(List<com.boushra.Model.ForecasterRating.Data> data) {
        manager=new LinearLayoutManager(this);
        ForecasterDetailsAdapter adapter=new ForecasterDetailsAdapter(this,data);
        recView.setLayoutManager(manager);
        recView.setAdapter(adapter);
    }

    private void settingSeekbar() {
        try
        {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(this,audio_uri);
            mediaPlayer.prepare();
            chronometer.setBase(mediaPlayer.getDuration());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        seekBar.setMax(300);
        seekBar.setProgress(mediaPlayer.getDuration()/100);
        if(mediaPlayer.getDuration()/1000<10) {
            timer_txt.setText("0:0"+mediaPlayer.getDuration() / 1000);
        }
        else
        {
            timer_txt.setText("0:"+mediaPlayer.getDuration()/1000);
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress/1000<10) {
                    timer_txt.setText("0:0" + progress/1000);
                }
                else
                {
                    timer_txt.setText("0:" + progress/1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        ask_question_txt.setOnClickListener(this::OnClick);
        videoview.setOnClickListener(this::OnClick);
        video_iv.setOnClickListener(this::OnClick);
        playaudio_iv.setOnClickListener(this::OnClick);
        stop_iv.setOnClickListener(this::OnClick);
        pause_iv.setOnClickListener(this::OnClick);
        dailog=new ProgressDailogHelper(ForecasterDetailsActivity.this,"");

    }

    public Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ask_question_txt:
                Intent intent=new Intent(ForecasterDetailsActivity.this,ForcasterForPriceActivity.class);
                intent.putExtra("ForecasterDetails","Yes");
                intent.putExtra(GlobalVariables.forecasterId,getIntent().getStringExtra(GlobalVariables.forecasterId));
                intent.putExtra(GlobalVariables.categoryName,getIntent().getStringExtra(GlobalVariables.categoryName));
                intent.putExtra(GlobalVariables.points,getIntent().getStringExtra(GlobalVariables.points));
                startActivity(intent);
                finish();
                break;

            case R.id.backLL:
                super.onBackPressed();
                break;


            case R.id.video_iv:
                video_iv.setVisibility(View.GONE);
                play_iv.setVisibility(View.GONE);
                videoview.performClick();

                break;

            case R.id.videoview:
                play_iv.setVisibility(View.GONE);
                videoview.setVisibility(View.VISIBLE);
                settingThumbnail();
                break;

            case R.id.playaudio_iv:
                playingAudio();
                break;

            case R.id.stop_iv:
                stoppingAudio();
                break;

            case R.id.pause_iv:
                pauseAudio();
                break;
        }

    }

    private void pauseAudio() {
        pause=true;
        try
        {
            playaudio_iv.setVisibility(View.VISIBLE);
            pause_iv.setVisibility(View.GONE);
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.pause();
                lastplay_position=mediaPlayer.getCurrentPosition();
            }

        }catch (Exception e)
        {
            e.printStackTrace();

        }


    }

    private void stoppingAudio() {
        try
        {
            if(mediaPlayer==null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(this, audio_uri);
                mediaPlayer.prepare();
                if (mediaPlayer.getDuration() / 1000 < 10) {
                    timer_txt.setText("0:0" + mediaPlayer.getDuration() / 1000);
                } else {
                    timer_txt.setText("0:" + mediaPlayer.getDuration() / 1000);
                }
            }



            seekBar.getProgress();


            playing=false;
            if(mediaPlayer!=null) {
                mediaPlayer.release();
            }
            seekBar.setProgress(0);
            seekBar.setMax(300);


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void playingAudio() {
            try {
                pause_iv.setVisibility(View.VISIBLE);
                playaudio_iv.setVisibility(View.GONE);
                timer_txt.setVisibility(View.VISIBLE);
                playing=true;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(this, audio_uri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                            if(pause) {
                                mediaPlayer.seekTo(lastplay_position);
                                mediaPlayer.start();
                        }
                            else
                            {
                                mediaPlayer.seekTo(0);
                                mediaPlayer.start();
                            }

                    }
                });
//                mediaPlayer.start();
                seekBar.setProgress(0);
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setClickable(false);
                seekUpdation();



                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        chronometer.stop();
                        playaudio_iv.setVisibility(View.VISIBLE);
                        pause_iv.setVisibility(View.GONE);
                        pause=false;
                        playing=false;
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
            if (mediaPlayer != null) {
                int mCurrentPosition=mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);
            }
            handler.postDelayed(runnable, 100);
        }
    }

    private void settingThumbnail() {
        progress_bar.setVisibility(View.VISIBLE);
        try {
            MediaController mediacontroller = new MediaController(ForecasterDetailsActivity.this);
            mediacontroller.setAnchorView(videoview);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video_uri);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                videoview.start();
                play_iv.setVisibility(View.GONE);
                progress_bar.setVisibility(View.GONE);



            }
        });

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                play_iv.setVisibility(View.VISIBLE);

            }
        });

    }
}
