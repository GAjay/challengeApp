package com.cambium.challenge.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cambium.challenge.R;
import com.cambium.challenge.fragments.AboutFragment;
import com.cambium.challenge.fragments.ListingFragment;
import com.cambium.challenge.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView homeButton = (TextView) findViewById(R.id.tv_home);
        homeButton.setOnClickListener(this);
        TextView aboutButton = (TextView) findViewById(R.id.tv_about);
        aboutButton.setOnClickListener(this);
        Fragment fragment = new ListingFragment();
        String tag = Utils.LISTING_FRAGMENT;
        replaceFragment(fragment, tag, new Bundle());

    }

    /**
     * Replaces fragment
     *
     * @param fragment Fragment object
     * @param tag      Fragment tag
     */
    public void replaceFragment(Fragment fragment, String tag, Bundle bundle) {
        if (fragment != null) {
            Fragment visibleFragment = getSupportFragmentManager().findFragmentByTag(tag);
            if ((null == visibleFragment) || (!(visibleFragment.isVisible()))) {
                Utils.clearFragmentStack(MainActivity.this);
                bundle.putString("fragmentTag", tag);
                bundle.putInt("resourceId", R.id.frame_container);
                Utils.replaceFragmentWithTag(MainActivity.this, fragment, bundle);

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (Utils.getCurrentFragment(MainActivity.this).getTag().equals(Utils.LISTING_FRAGMENT)
                || Utils.getCurrentFragment(MainActivity.this).getTag().equals(Utils.ABOUT_FRAGMENT)) {
            finish();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        String tag = null;
        switch (view.getId()) {
            case R.id.tv_home:
                fragment = new ListingFragment();
                tag = Utils.LISTING_FRAGMENT;
                break;
            case R.id.tv_about:
                fragment = new AboutFragment();
                tag = Utils.ABOUT_FRAGMENT;
                break;
        }

        replaceFragment(fragment, tag, new Bundle());

    }

    /**
     * selects layout
     *
     * @param textId Text resource id
     */
    public void selectLayout(@IdRes int textId) {
        try {
            if (textId == R.id.tv_about) {
                TextView bottomText = (TextView) findViewById(R.id.tv_home);
                bottomText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
            } else {
                TextView bottomText = (TextView) findViewById(R.id.tv_about);
                bottomText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
            }

            TextView bottomText = (TextView) findViewById(textId);
            bottomText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
