package com.boushra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.boushra.Model.Chat;
import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileSetupActivity extends AppCompatActivity {
    @BindView(R.id.backLL) LinearLayout backLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_constraint);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                Intent intent=new Intent(ProfileSetupActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
