package com.cambium.challenge.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import com.cambium.challenge.R;

/**
 *
 *
 * @author Ajay Kumar Maheshwari
 */

class CustomProgressDialog extends ProgressDialog {
    private CustomProgressDialog(Context context) {
        super(context);
    }

    public static ProgressDialog progressDialog(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partial_progress_bar);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        if (null != progressBar) {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(),
                    R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }
}
