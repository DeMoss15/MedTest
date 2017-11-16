package com.example.daniel.medtest.gui;

import android.app.Activity;
import android.app.Fragment;

import com.example.daniel.medtest.datatypes.Test;

/**
 * Created by Daniel on 16.11.2017.
 */

public class FragmentSubSession extends Fragment {

    OnHeadlineSelectedListener mCallback;

    interface OnHeadlineSelectedListener{

        void callSessionStart(Test test);
        void callSessionProcess(Test test);
        void callSessionEnd(Test test);
        void cancelSession();
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
