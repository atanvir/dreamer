package com.boushra.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity extends AppCompatActivity {
    @BindView(R.id.webview) WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://18.188.1.136:4002/help");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
