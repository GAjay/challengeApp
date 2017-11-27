package com.cambium.challenge.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * A text view components class for regular raleway font.
 *
 * @author Ajay Kumar Maheshwari
 */

public class RalewayRegularRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    public RalewayRegularRadioButton(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_regular.ttf");
        setTypeface(tf);
    }

    public RalewayRegularRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_regular.ttf");
        setTypeface(tf);
    }

    public RalewayRegularRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_regular.ttf");
        setTypeface(tf);
    }
}
