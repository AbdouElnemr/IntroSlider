package com.codex.eduapp.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyButton extends android.support.v7.widget.AppCompatButton {

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf =  Typeface.createFromAsset(getContext().getAssets(), "fonts/DroidKufi-Regular.ttf");
        setTypeface(tf );

    }
}