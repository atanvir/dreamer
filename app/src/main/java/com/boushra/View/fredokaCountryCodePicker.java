package com.boushra.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.hbb20.CountryCodePicker;

public class fredokaCountryCodePicker extends CountryCodePicker {
    public fredokaCountryCodePicker(Context context) {
        super(context);
        createFont();
    }

    public fredokaCountryCodePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public fredokaCountryCodePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/fredoka/FredokaOne-Regular.otf");
        setTypeFace(font);
    }

}
