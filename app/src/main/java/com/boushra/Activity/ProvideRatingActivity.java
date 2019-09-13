package com.boushra.Activity;

import android.os.Bundle;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProvideRatingActivity extends AppCompatActivity {
    @BindView(R.id.ratingBar2)
    RatingBar ratingBar2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_rating);
        ButterKnife.bind(this);
        ratingBar2.setNumStars(0);
    }

}
