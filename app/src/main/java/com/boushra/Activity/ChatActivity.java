package com.boushra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.profile_image_im) ImageView profile_image_im;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        profile_image_im.setOnClickListener(this::OnClick);

    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.profile_image_im:
                Intent intent=new Intent(ChatActivity.this,ProfileSetupActivity.class);
                 startActivity(intent);
                finish();
                break;

            case R.id.backLL:
                super.onBackPressed();
                break;

        }
    }
}
