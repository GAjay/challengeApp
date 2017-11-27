package com.cambium.challenge.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.cambium.challenge.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;

/**
 * A util class for common methods
 *
 * @author Ajay Kumar Maheshwari
 */

public class Utils {

    public static final String LISTING_FRAGMENT = "listing";
    public static final String DETAIL_FRAGMENT = "detail";
    public static final String PROJECT_URl = "https://www.kickstarter.com";
    public static final String ABOUT_FRAGMENT = "About";

    public interface successCallback {
        void onClick();
    }

    /**
     * A method to check network availability.
     *
     * @param context : Class context
     * @return : true/false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.
                CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    /**
     * Converts date from yyyy-MM-dd HH:mm:ss to dd MMM
     *
     * @param inputDate date format yyyy-MM-dd HH:mm:ss
     * @return dd MMM, yy
     */
    public static String getDateWithFormat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.US);
        Date input = null;
        try {
            input = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputFormat.format(input);
    }

    /**
     * Converts date from yyyy-MM-dd HH:mm:ss to dd MMM,yy
     *
     * @param inputDate date format yyyy-MM-dd HH:mm:ss
     * @return dd MMM, yy
     */
    public static String getFormatedDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.US);
        Date input = null;
        try {
            input = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputFormat.format(input);
    }

    /**
     * Clears the back stack
     *
     * @param activity Activity
     */
    public static void clearFragmentStack(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }


    /**
     * Currently visible fragment
     *
     * @param activity Activity
     * @return fragment
     */
    public static Fragment getCurrentFragment(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return activity.getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
    }

    /**
     * Replaces fragment with TAG
     *
     * @param activity Activity
     * @param fragment Fragment
     * @param args     Bundle
     */
    public static void replaceFragmentWithTag(FragmentActivity activity, Fragment fragment, Bundle args) {
        if (args != null) {
            fragment.setArguments(args);
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (!args.getString("fragmentTag").equalsIgnoreCase(Utils.ABOUT_FRAGMENT)) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_left, R.anim.enter_right, R.anim.exit_right);
            }
            fragmentTransaction.replace(args.getInt("resourceId"), fragment, args.getString("fragmentTag"))
                    .addToBackStack(args.getString("fragmentTag"))
                    .commit();
        }
    }


    /**
     * Common Progress dialog
     *
     * @param context Context
     * @return ProgressDialog
     */
    public static ProgressDialog showProgressDialog(Context context) {
        final ProgressDialog progressDialog = CustomProgressDialog.progressDialog(context);
        Window window = progressDialog.getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * Parses error data
     *
     * @param context   : Context
     * @param code      : error code
     * @param errorBody : Error data
     */
    public static void parseErrorData(Context context, int code, ResponseBody errorBody) {
        try {
            if (code == 400 || code == 401) {
                JSONObject jObjError = new JSONObject(errorBody.string());
                DialogUtils.getConfirmationDialog(context, context.getResources().
                        getString(R.string.text_alert), context.getResources().
                        getString(R.string.alert_something_went_wrong));
            } else if (code == 500) {
                showAlert(context.getResources().getString(R.string.alert_something_went_wrong), context);
            } else {
                JSONObject jObjError = new JSONObject(errorBody.string());
                DialogUtils.getConfirmationDialog(context, context.getResources().
                        getString(R.string.text_alert), context.getResources().
                        getString(R.string.alert_something_went_wrong));
            }
        } catch (Exception e) {
            showAlert(context.getResources().getString(R.string.alert_something_went_wrong), context);
            e.printStackTrace();
        }
    }

    /**
     * Show alert messages for all messages/
     *
     * @param message Message to be displayed on Alert
     */
    public static void showAlert(@NonNull String message, Context context) {
        DialogUtils.getConfirmationDialog(context, context.getResources().getString(R.string.text_alert), message);
    }

}

