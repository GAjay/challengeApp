package com.cambium.challenge.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * A text view components class for regular raleway font.
 *
 * @author Ajay Kumar Maheshwari
 */

public class RalewaySemiBoldButton extends android.support.v7.widget.AppCompatButton {

    public RalewaySemiBoldButton(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_semiBold.ttf");
        setTypeface(tf);
    }

    public RalewaySemiBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_semiBold.ttf");
        setTypeface(tf);
    }

    public RalewaySemiBoldButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/raleway_semiBold.ttf");
        setTypeface(tf);
    }
}
