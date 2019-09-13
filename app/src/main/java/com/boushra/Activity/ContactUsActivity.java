package com.boushra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {

    @BindView(R.id.backLL) LinearLayout backLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
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
                Intent intent=new Intent(ContactUsActivity.this,SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
