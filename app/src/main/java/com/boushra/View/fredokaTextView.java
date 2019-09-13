package com.boushra.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class fredokaTextView extends AppCompatTextView {
    public fredokaTextView(Context context) {
        super(context);
        createFont();
    }

    public fredokaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public fredokaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/fredoka/FredokaOne-Regular.otf");
        setTypeface(font);
    }

}