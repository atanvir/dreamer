package com.boushra.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.boushra.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class PaymentMethodActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_method);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        }
        @OnClick({R.id.backImageView})
    void OnClick(View view)
        {
            switch (view.getId())
            {
                case R.id.backImageView:
                    Intent intent=new Intent(PaymentMethodActivity.this,StoreActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }

}
