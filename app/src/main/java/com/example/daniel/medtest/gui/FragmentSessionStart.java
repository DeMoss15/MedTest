package com.example.daniel.medtest.gui;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSessionStart extends FragmentSubSession implements View.OnClickListener{

    @BindView(R.id.button_cancel)
    Button mCancelTest;
    @BindView(R.id.tv_test_name)
    TextView mTextViewTestName;

    private Test mTest;

    public FragmentSessionStart() {
        // Required empty public constructor
    }

    public void startSession(Test test) {
        this.mTest = test;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_session_start, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewTestName.setText(mTest.getTestName());
        mCancelTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_cancel: {
                mCallback.cancelSession();
                break;
            }
        }
    }
}
