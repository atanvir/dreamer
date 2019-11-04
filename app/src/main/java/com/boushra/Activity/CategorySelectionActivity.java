package com.boushra.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boushra.Fragment.NavigationChatFragment;
import com.boushra.Fragment.NavigationHomeFragment;
import com.boushra.Fragment.NavigationMoreFragment;
import com.boushra.Fragment.NavigationProfileFragment;
import com.boushra.Fragment.NotificationFragment;
import com.boushra.Model.User;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;
import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySelectionActivity extends AppCompatActivity{
    private ActionBar toolbar;
    @BindView(R.id.toolbar) Toolbar toolbarx;
    @BindView(R.id.home_iv) ImageView home_iv;
    @BindView(R.id.chat_im) ImageView chat_im;
    @BindView(R.id.profile_im) ImageView profile_im;
    @BindView(R.id.more_im) ImageView more_im;
    int clickcount=0;
    String fcm="",chat="";
    String activity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale=new Locale(SharedPreferenceWriter.getInstance(CategorySelectionActivity.this).getString(GlobalVariables.langCode));
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_category_selection);

        ButterKnife.bind(this);
        getSupportActionBar().hide();
        toolbar = getSupportActionBar();
        home_iv.setBackground(getDrawable(R.drawable.home_gradient));
        home_iv.setImageDrawable(null);

        fcm=getIntent().getStringExtra("FCM");
        chat=getIntent().getStringExtra("chat");
        if(fcm!=null)
        {
           if(fcm.equalsIgnoreCase("Yes")) {
               String type=getIntent().getStringExtra(GlobalVariables.type);
               if(type.equalsIgnoreCase("paymentVerify"))
               {
                   getUserDetailsApi();
               }
               getSupportFragmentManager().beginTransaction().replace(R.id.replace, new NotificationFragment()).addToBackStack(CategorySelectionActivity.class.getSimpleName()).commit();
           }

        }else if(chat!=null)
        {
           if(chat.equalsIgnoreCase("yes"))
           {
               getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationChatFragment()).addToBackStack(CategorySelectionActivity.class.getSimpleName()).commit();
           }
        }

        else {


           getSupportFragmentManager().beginTransaction().replace(R.id.replace, new NavigationHomeFragment()).addToBackStack(CategorySelectionActivity.class.getSimpleName()).commit();
       }
        SharedPreferenceWriter.getInstance(CategorySelectionActivity.this).writeStringValue(GlobalVariables.islogin,"Yes");
    }

    private void getUserDetailsApi() {
        User user=new User();
        user.setUserId(SharedPreferenceWriter.getInstance(this).getString(GlobalVariables._id));
        RetroInterface api_service=RetrofitInit.getConnect().createConnection();

        Call<User> call=api_service.getUserDetails(user,SharedPreferenceWriter.getInstance(CategorySelectionActivity.this).getString(GlobalVariables.jwtToken));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful())
                {
                   User server_response= response.body();
                   if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                   {
                       SharedPreferenceWriter.getInstance(CategorySelectionActivity.this).writeStringValue(GlobalVariables.totalPoints, String.valueOf(server_response.getData().getTotalPoints()));


                   }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                   {
                       Toast.makeText(CategorySelectionActivity.this, server_response.getResponseMessage(), Toast.LENGTH_SHORT).show();

                   }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CategorySelectionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @OnClick({R.id.homeLL,R.id.chatLL,R.id.profileLL,R.id.moreLL})
    void OnClick(View view)
    {
    switch (view.getId())
    {
        case R.id.homeLL:
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).addToBackStack(null).commit();
            home_iv.setBackground(getDrawable(R.drawable.home_gradient));
            home_iv.setImageDrawable(null);
            chat_im.setImageDrawable(getDrawable(R.drawable.chat_un));
            more_im.setImageDrawable(getDrawable(R.drawable.menu_un));
            profile_im.setImageDrawable(getDrawable(R.drawable.profile_un));
            profile_im.setBackground(null);
            chat_im.setBackground(null);
            more_im.setBackground(null);

            break;
        case R.id.chatLL:
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationChatFragment()).addToBackStack(null).commit();
            chat_im.setBackground(getDrawable(R.drawable.chat_gradient));
            chat_im.setImageDrawable(null);
            home_iv.setImageDrawable(getDrawable(R.drawable.home_un));
            more_im.setImageDrawable(getDrawable(R.drawable.menu_un));
            profile_im.setImageDrawable(getDrawable(R.drawable.profile_un));
            profile_im.setBackground(null);
            home_iv.setBackground(null);
            more_im.setBackground(null);


            break;
        case R.id.moreLL:
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationMoreFragment()).addToBackStack(null).commit();
            more_im.setBackground(getDrawable(R.drawable.menu_gradient));
            more_im.setImageDrawable(null);
            home_iv.setImageDrawable(getDrawable(R.drawable.home_un));
            chat_im.setImageDrawable(getDrawable(R.drawable.chat_un));
            profile_im.setImageDrawable(getDrawable(R.drawable.profile_un));
            profile_im.setBackground(null);
            home_iv.setBackground(null);
            chat_im.setBackground(null);



            break;

        case R.id.profileLL:
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationProfileFragment()).addToBackStack(null).commit();
            home_iv.setImageDrawable(getDrawable(R.drawable.home_un));
            chat_im.setImageDrawable(getDrawable(R.drawable.chat_un));
            more_im.setImageDrawable(getDrawable(R.drawable.menu_un));
            profile_im.setImageDrawable(null);
            profile_im.setBackground(getDrawable(R.drawable.profile_gradient));
            home_iv.setBackground(null);
            chat_im.setBackground(null);
            more_im.setBackground(null);

            break;
    }
}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
            int count =getSupportFragmentManager().getBackStackEntryCount();
            String entryName  = getSupportFragmentManager().getBackStackEntryAt(count-1).getName();
            if(entryName!=null) {
                Log.e("class_name",entryName);
                if (entryName.equalsIgnoreCase("NavigationMoreFragment")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.replace, new NavigationMoreFragment()).addToBackStack(null).commit();
                }
                else if(entryName.equalsIgnoreCase("ForecasterListFragment"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
                }
                else if(entryName.equalsIgnoreCase("PsychologicalListFragment"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.replace,new PsychologicalListFragment()).addToBackStack(NavigationHomeFragment.class.getSimpleName()).commit();
                }
                else if(entryName.equalsIgnoreCase("NavigationHomeFragment"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
                }
                else if(entryName.equalsIgnoreCase("CategorySelectionActivity"))
                {
                    final Dialog dialog=new Dialog(CategorySelectionActivity.this,android.R.style.Theme_Black);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.exit_popup);
                    TextView yesText=dialog.findViewById(R.id.yesText);
                    TextView noText=dialog.findViewById(R.id.noText);

                    yesText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finishAffinity();
                        }
                    });
                    noText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }

            else
            {
                home_iv.setBackground(getDrawable(R.drawable.home_gradient));
                home_iv.setImageDrawable(null);
                chat_im.setImageDrawable(getDrawable(R.drawable.chat_un));
                more_im.setImageDrawable(getDrawable(R.drawable.menu_un));
                profile_im.setImageDrawable(getDrawable(R.drawable.profile_un));
                profile_im.setBackground(null);
                chat_im.setBackground(null);
                more_im.setBackground(null);
                getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).addToBackStack(CategorySelectionActivity.class.getSimpleName()).commit();

            }


    }
}