package com.boushra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.boushra.R;
import com.boushra.Utility.GlobalVariables;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {

    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.contact_messsage_cl) ConstraintLayout contact_messsage_cl;
    @BindView(R.id.contact_email_cl) ConstraintLayout contact_email_cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalVariables.setStatusBarGradiant(this);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        contact_email_cl.setOnClickListener(this::OnClick);
        contact_messsage_cl.setOnClickListener(this::OnClick);

    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                super.onBackPressed();
                break;
        }
    }
}
