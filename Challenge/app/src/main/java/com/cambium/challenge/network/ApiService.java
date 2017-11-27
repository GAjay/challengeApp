package com.cambium.challenge.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.cambium.challenge.R;
import com.cambium.challenge.models.ProjectDetails;
import com.cambium.challenge.utils.DialogUtils;
import com.cambium.challenge.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A class for call api.
 *
 * @author Ajay Kumar Maheshwari
 */

public class ApiService {

    private  ArrayList<ProjectDetails> projectDetailses = new ArrayList<>();
    public interface ApiCallback{
        void onSuccess(ArrayList<ProjectDetails> result);
    }

    /**
     * A method for call project detail api.
     * @param context : application context
     * @return : list of projects
     */
    public void shareProjectDetails(final Context context,final ApiCallback callback) {

        final ProgressDialog progressDialog = Utils.showProgressDialog(context);

        RestClient.ApiInterface apiInterface = RestClient.getClient(context);
        Call<ArrayList<ProjectDetails>> projectListCall = apiInterface.getListOfProjects();
        projectListCall.enqueue(new Callback<ArrayList<ProjectDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectDetails>> call,
                                   Response<ArrayList<ProjectDetails>> response) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    callback.onSuccess(response.body());
                    Utils.parseErrorData(context, response.code(), response.errorBody());}
            }
            @Override
            public void onFailure(Call<ArrayList<ProjectDetails>> call, Throwable throwable) {
                if (null != progressDialog) {
                    progressDialog.dismiss();}
                if (throwable instanceof IOException) {
                    DialogUtils.getConfirmationCallBackWithCancel(context, context.getResources().getString
                                    (R.string.text_alert),
                            context.getResources().getString
                                    (R.string.alert_check_internet),
                            context.getResources().getString(R.string.label_retry),
                            context.getResources().getString(R.string.label_cancel), new DialogUtils.successCallback() {
                                @Override
                                public void onClick() {
                                    shareProjectDetails(context,callback);
                                }
                            });
                } else {
                    Utils.showAlert(context.getString(R.string.alert_something_went_wrong),context);
                }
            }
        });
    }
}
