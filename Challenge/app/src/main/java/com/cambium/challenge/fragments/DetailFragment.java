package com.cambium.challenge.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cambium.challenge.R;
import com.cambium.challenge.models.ProjectDetails;
import com.cambium.challenge.utils.Utils;

/**
 * A detail fragment class for project detail.
 *
 * @author Ajay Kumar Maheshwari
 */

public class DetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Toolbar mToolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (null != ((AppCompatActivity) getActivity()).getSupportActionBar()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });
            ((AppCompatActivity) getActivity()).setTitle("");

            if (null != bundle.get("data")) {
                setData(rootView, bundle);
            }

        }

        return rootView;
    }

    /**
     * A method to set data in ui components.
     * @param rootView : Reference of fragment view.
     * @param bundle : Data of selected item.
     */
    private void setData(View rootView, Bundle bundle) {
        final ProjectDetails projectDetails = bundle.getParcelable("data");

        TextView title = rootView.findViewById(R.id.tv_title);
        TextView by = rootView.findViewById(R.id.tv_by);
        TextView blurb = rootView.findViewById(R.id.tv_blurb);
        TextView backers = rootView.findViewById(R.id.tv_backers);
        TextView fund = rootView.findViewById(R.id.tv_fund);
        TextView date = rootView.findViewById(R.id.tv_date);
        TextView location = rootView.findViewById(R.id.tv_location);
        title.setText(projectDetails.getTitle());
        blurb.setText(projectDetails.getBlurb());
        by.setText(getString(R.string.created_by)+" "+projectDetails.getBy());
        backers.setText(projectDetails.getNumBackers());
        fund.setText(String.valueOf(projectDetails.getPercentageFunded()));
        date.setText(Utils.getFormatedDate(projectDetails.getEndTime()));
        location.setText(projectDetails.getLocation());
        Button readMore = (Button)rootView.findViewById(R.id.btn_more);
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Utils.PROJECT_URl+projectDetails.getUrl()));
                getActivity().startActivity(i);

            }
        });

    }

}
