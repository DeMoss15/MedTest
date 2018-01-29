package com.example.daniel.medtest.gui;

import android.app.Activity;
import android.app.Fragment;

import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.logic.TestSession;

/**
 * Created by Daniel on 16.11.2017.
 */

public class FragmentSubSession extends Fragment {

    OnHeadlineSelectedListener mCallback;

    interface OnHeadlineSelectedListener{

        void callSessionStart();
        void callSessionProcess();
        void callSessionResult();
        void cancelSession();
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentSubSession.OnHeadlineSelectedListener");
        }
    }
}
