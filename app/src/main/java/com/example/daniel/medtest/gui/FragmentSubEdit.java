package com.example.daniel.medtest.gui;

import android.app.Activity;
import android.app.Fragment;

import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;

/**
 * Created by Daniel on 14.12.2017.
 */

public class FragmentSubEdit extends Fragment {

    interface OnHeadlineSelectedListener{
        void callQuestionEditFragment(Question question);
        void callTestEditFragment();
        void cancelEditing();
    }

    OnHeadlineSelectedListener mCallback;

    //check implementation of callback
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentSubEdit.OnHeadlineSelectedListener");
        }
    }
}
