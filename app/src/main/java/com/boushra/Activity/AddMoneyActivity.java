package com.boushra.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMoneyActivity extends AppCompatActivity {
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.submittxt) TextView submittxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        submittxt.setOnClickListener(this::OnClick);



    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.submittxt:
                final Dialog dialog=new Dialog(AddMoneyActivity.this,android.R.style.Theme_Black);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.activity_bank_transfer_payment_popup);
                LinearLayout closell=dialog.findViewById(R.id.closell);
                dialog.setCancelable(true);
                closell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent=new Intent(AddMoneyActivity.this,CategorySelectionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
                break;

            case R.id.backLL:
                Intent intent=new Intent(AddMoneyActivity.this,StoreActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
