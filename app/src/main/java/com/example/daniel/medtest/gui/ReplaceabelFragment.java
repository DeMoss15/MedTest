package com.example.daniel.medtest.gui;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Daniel on 12.11.2017.
 */

public class ReplaceabelFragment extends Fragment {

    OnHeadlineSelectedListener mCallback;

    interface OnHeadlineSelectedListener{
        void callInputFragment();
        void callSessionFragment();
        void callOverviewFragment();
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
