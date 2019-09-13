package com.boushra.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by mahesh on 27/1/17.
 * SanFranciscoDisplay-Regular font TextView
 */

public class SegoeRegularEditText extends AppCompatEditText{
    public SegoeRegularEditText(Context context) {
        super(context);
        createFont();
    }

    public SegoeRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public SegoeRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoe-ui-4-cufonfonts/Segoe UI.ttf");
        setTypeface(font);
    }

}
