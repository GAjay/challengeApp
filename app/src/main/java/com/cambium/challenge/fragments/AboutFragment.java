package com.cambium.challenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cambium.challenge.R;
import com.cambium.challenge.activities.MainActivity;

/**
 * A fragment class for about me ui
 *
 * @author Ajay Kumar Maheshwari
 */

public class AboutFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Select Profile tab
        ((MainActivity) getActivity()).selectLayout(
                R.id.tv_about);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ((TextView)rootView.findViewById(R.id.tv_data)).setText(getString(R.string.about_data));

        return rootView;

    }
}
