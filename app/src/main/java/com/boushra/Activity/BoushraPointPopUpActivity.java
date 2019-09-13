package com.boushra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoushraPointPopUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boushra_point_popup);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.paymentmethodIm})
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.paymentmethodIm:
                Intent intent=new Intent(BoushraPointPopUpActivity.this,PaymentMethodActivity.class);
                startActivity(intent);
                finish();


                break;
        }
    }

}
