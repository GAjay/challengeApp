package com.cambium.challenge.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cambium.challenge.R;

import java.util.ArrayList;

/**
 * A dialog util class for alert dialogs
 *
 * @author Ajay Kumar Maheshwari
 */

public class DialogUtils extends AlertDialog.Builder {

    public interface successCallback {
        void onClick();
    }

    public interface choiceSelectedCallback {
        void onChoiceSelected(int pos);
    }

    public DialogUtils(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * A method for informative alert without callback
     *
     * @param context : Calling class context
     * @param title   : Alert title
     * @param text    : informative message
     */
    public static void getConfirmationDialog(Context context, String title, String text) {
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        View informativeDialogView =
                ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.dialog_informative, null);
        TextView textTitle = (TextView) informativeDialogView.findViewById(R.id.alert_dialog_title_text_view);
        textTitle.setText(title);
        TextView textContent = (TextView) informativeDialogView.findViewById(R.id.alert_dialog_content_text_view);
        Button positiveButton = (Button) informativeDialogView.findViewById(R.id.positive_button);
        textContent.setText(text);
        builder.setView(informativeDialogView);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(false);
        alertDialog.show();
        textContent.setText(text);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /**
     * A method for success callback and cancel button
     *
     * @param context            : Calling class context
     * @param title              : Title of alert
     * @param text               : Alert message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param successCallback
     */
    public static void getConfirmationCallBackWithCancel(Context context, String title,
                                                         String text, String positiveButtonText,
                                                         String negativeButtonText, final
                                                         successCallback successCallback) {
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        View informativeDialogView =
                ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.dialog_alert, null);
        TextView textTitle = (TextView) informativeDialogView.findViewById(R.id.alert_dialog_title_text_view);
        textTitle.setText(title);
        TextView textContent = (TextView) informativeDialogView.findViewById(R.id.alert_dialog_content_text_view);
        Button positiveButton = (Button) informativeDialogView.findViewById(R.id.positive_button);
        Button negativeButton = (Button) informativeDialogView.findViewById(R.id.negative_button);
        textContent.setText(text);
        builder.setView(informativeDialogView);
        builder.setCancelable(false);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (null != positiveButtonText) {
            positiveButton.setText(positiveButtonText);
        }
        if (null != negativeButtonText) {
            negativeButton.setText(negativeButtonText);
        }
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                successCallback.onClick();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static void getDropDown(Context context, ArrayList<String> itemList, int selectedId,
                                   final choiceSelectedCallback choiceSelectedCallback) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.single_choice_dialog, null);

        final RadioGroup choice_radio_group = (RadioGroup) dialogView.findViewById(R.id.choice_radio_group);
        // preparing category drop down and select the pre-select value
        for (int i = 0; i < itemList.size(); i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.partial_radio_button, null);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT));
            radioButton.setId(i);
            radioButton.setText(itemList.get(i));
            radioButton.setTextSize(16);
            choice_radio_group.addView(radioButton);
            if (selectedId == i) {
                radioButton.setChecked(true);
            }
        }
        builder.setView(dialogView);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
        choice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, final int checkedId) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        dialog.cancel();
                        choiceSelectedCallback.onChoiceSelected(checkedId);
                    }
                }, 250);
            }
        });
    }

}
