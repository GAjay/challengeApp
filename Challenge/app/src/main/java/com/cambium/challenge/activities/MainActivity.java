package com.cambium.challenge.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cambium.challenge.R;
import com.cambium.challenge.fragments.ListingFragment;
import com.cambium.challenge.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("data","dsafhsdjkhfkjsd");
        setContentView(R.layout.activity_main);
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
                Utils.replaceFragmentWithTag(MainActivity.this,fragment,bundle);

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (Utils.getCurrentFragment(MainActivity.this).getTag().equals(Utils.LISTING_FRAGMENT)) {
            finish();
        }  else {
            super.onBackPressed();
        }
    }
}
