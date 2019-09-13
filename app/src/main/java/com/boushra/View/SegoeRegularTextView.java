package com.boushra.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class SegoeRegularTextView extends AppCompatTextView {
    public SegoeRegularTextView(Context context) {
        super(context);
        createFont();
    }

    public SegoeRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public SegoeRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoe-ui-4-cufonfonts/Segoe UI.ttf");
        setTypeface(font);
    }

}