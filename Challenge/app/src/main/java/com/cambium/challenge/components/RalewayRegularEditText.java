package com.cambium.challenge.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * A text view components class for light raleway font.
 *
 * @author Ajay Kumar Maheshwari
 */

public class RalewayRegularEditText extends android.support.v7.widget.AppCompatEditText {

    public RalewayRegularEditText(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_regular.ttf");
        setTypeface(tf);
    }

    public RalewayRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_regular.ttf");
        setTypeface(tf);
    }

    public RalewayRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_regular.ttf");
        setTypeface(tf);
    }
}
