package com.boushra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boushra.Fragment.NavigationChatFragment;
import com.boushra.Fragment.NavigationHomeFragment;
import com.boushra.Fragment.NavigationMoreFragment;
import com.boushra.Fragment.NavigationProfileFragment;
import com.boushra.Model.User;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.BottomNavigationViewHelper;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySelectionActivity extends AppCompatActivity{
    private ActionBar toolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbarx;

    @BindView(R.id.home_iv)
    ImageView home_iv;

    @BindView(R.id.chat_im)
    ImageView chat_im;

    @BindView(R.id.profile_im)
    ImageView profile_im;

    @BindView(R.id.more_im)
    ImageView more_im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        toolbar = getSupportActionBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
        home_iv.setBackground(getDrawable(R.drawable.home_gradient));
        home_iv.setImageDrawable(null);
        SharedPreferenceWriter.getInstance(CategorySelectionActivity.this).writeStringValue(GlobalVariables.islogin,"Yes");
    }



    @OnClick({R.id.homeLL,R.id.chatLL,R.id.profileLL,R.id.moreLL})
    void OnClick(View view)
{
    switch (view.getId())
    {
        case R.id.homeLL:
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationChatFragment()).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationMoreFragment()).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationProfileFragment()).commit();

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


}