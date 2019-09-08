package com.codex.eduapp.school.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyEdittext extends android.support.v7.widget.AppCompatEditText {

    public MyEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEdittext(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf =  Typeface.createFromAsset(getContext().getAssets(), "fonts/DroidKufi-Regular.ttf");
        setTypeface(tf );

    }
}