package com.boushra.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.boushra.Fragment.NavigationChatFragment;
import com.boushra.Fragment.NavigationHomeFragment;
import com.boushra.Fragment.NavigationMoreFragment;
import com.boushra.Fragment.NavigationProfileFragment;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategorySelectionActivity extends AppCompatActivity{
    private ActionBar toolbar;
    @BindView(R.id.toolbar) Toolbar toolbarx;
    @BindView(R.id.home_iv) ImageView home_iv;
    @BindView(R.id.chat_im) ImageView chat_im;
    @BindView(R.id.profile_im) ImageView profile_im;
    @BindView(R.id.more_im) ImageView more_im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        toolbar = getSupportActionBar();
        home_iv.setBackground(getDrawable(R.drawable.home_gradient));
        home_iv.setImageDrawable(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();

        SharedPreferenceWriter.getInstance(CategorySelectionActivity.this).writeStringValue(GlobalVariables.islogin,"Yes");
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
//
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
                getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();

            }


    }
}