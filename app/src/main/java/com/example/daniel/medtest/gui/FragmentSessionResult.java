package com.example.daniel.medtest.gui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.logic.TestSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSessionResult extends FragmentSubSession implements View.OnClickListener {

    @BindView(R.id.tv_test_name_res)
    TextView mTextViewTestName;
    @BindView(R.id.tv_time_res)
    TextView mTextViewTime;
    @BindView(R.id.tv_answers_res)
    TextView mTextViewAnswers;
    @BindView(R.id.button_exit_session)
    Button mButtonExit;

    private TestSession mSession;

    public FragmentSessionResult() {
        // Required empty public constructor
    }

    public void setSession(TestSession session) {
        this.mSession = session;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_session_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewTestName.setText(mSession.getTestName());
        mTextViewAnswers.setText("" + mSession.getNumOfQuestionsInTest()
                + "/" + mSession.getNumOfQuestions() + "/0");
        mButtonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_exit_session: {
                mCallback.cancelSession();
                break;
            }
        }
    }
}
