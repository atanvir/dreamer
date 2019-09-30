package com.boushra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    String fcm="";
    String splash_pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        splash_pass=getIntent().getStringExtra("pass_splash");


        if (SharedPreferenceWriter.getInstance(SplashActivity.this).getBoolean(GlobalVariables.touchid)) {
            if(splash_pass!=null) {
                if(splash_pass.equalsIgnoreCase("Yes"))
                {
                    startSplashMethods();
                }

            }
            else {
                Intent intent = new Intent(this, FingerprintActivity.class);
                finish();
                startActivity(intent);
            }
        } else {

            startSplashMethods();
        }







    }

    private void startSplashMethods() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    // Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                String auth_token = task.getResult().getToken();
                Log.w("firebaese","token: "+auth_token);
                SharedPreferenceWriter.getInstance(SplashActivity.this).writeStringValue(GlobalVariables.firebase_token,auth_token);
            }
        });

        fcm=getIntent().getStringExtra("body");
        if(fcm!=null)
        {
            Log.e("fcm",fcm);
            Intent intent=new Intent(this,CategorySelectionActivity.class);
            intent.putExtra("FCM","Yes");
            startActivity(intent);



        }
        else
        {
            startMethod();
        }

    }

    private void startMethod() {
            Glide.with(SplashActivity.this.getApplicationContext())
                    .load(R.drawable.logo_animate)
                    .asGif()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<Integer, GifDrawable>() {
                        @Override
                        public boolean onException(Exception e, Integer model, Target<GifDrawable> target, boolean isFirstResource) {
                            e.printStackTrace();
                            Log.e(SplashActivity.class.getSimpleName(), "\n** Exception in setting GIF correctly **\n");
                            Toast.makeText(SplashActivity.this, "Exception in setting GIF correctly", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Integer model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Handler handler = new Handler();
                            if (resource != null) {

                                GifDrawable gifDrawable;

                                gifDrawable = resource;

                                int duration = 0;
                                GifDecoder decoder = gifDrawable.getDecoder();
                                for (int i = 0; i < gifDrawable.getFrameCount(); i++) {
                                    duration += decoder.getDelay(i);
                                }

                                Log.w(SplashActivity.class.getSimpleName(), "\n****GIF frame count duration: " + duration + " ****\n");
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        if(SharedPreferenceWriter.getInstance(SplashActivity.this).getString(GlobalVariables.islogin).equalsIgnoreCase("Yes"))
                                        {
                                         Intent intent=new Intent(SplashActivity.this,CategorySelectionActivity.class);
                                         finish();
                                         startActivity(intent);
                                        }
                                        else {

                                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                            finish();
                                            startActivity(intent);
                                        }

                                    }
                                };
                                handler.removeCallbacks(runnable);
                                handler.postDelayed(runnable, duration);
                            }
                            return false;
                        }
                    })
                    .into(imageView);
        }









}
