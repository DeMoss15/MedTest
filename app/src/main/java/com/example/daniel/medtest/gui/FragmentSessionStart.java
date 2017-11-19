package com.example.daniel.medtest.gui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.logic.TestSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class FragmentSessionStart extends FragmentSubSession implements View.OnClickListener{

    @BindView(R.id.tv_test_name)
    TextView mTextViewTestName;
    @BindView(R.id.tv_num_of_questions)
    TextView mTextViewNumOfQuest;
    @BindView(R.id.button_cancel_session)
    Button mButtonCancelTest;
    @BindView(R.id.button_start_test)
    Button mButtonStartTest;
    @BindView(R.id.switch_test_shuffle)
    Switch mSwitchShuffle;
    @BindView(R.id.switch_timer)
    Switch mSwitchTimer;
    @BindView(R.id.seekBar_num_of_quesrions_for_session)
    SeekBar mSeekBarNumOfQuestions;
    @BindView(R.id.time_field)
    TextInputLayout mTextInputLayputTimer;
    @BindView(R.id.ti_time_limit)
    TextInputEditText mTextInputTimeLimit;

    private Test mTest;
    private TestSession mTestSession;

    public FragmentSessionStart() {
        // Required empty public constructor
    }

    public void startSession(Test test, TestSession session) {
        this.mTest = test;
        this.mTestSession = session;
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

        mSeekBarNumOfQuestions.setMax(mTest.getQuestions().size());
        mSeekBarNumOfQuestions.setProgress(mSeekBarNumOfQuestions.getMax());
        mSeekBarNumOfQuestions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTextViewNumOfQuest.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSwitchTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mTextInputLayputTimer.setVisibility(View.VISIBLE);
                } else {
                    mTextInputLayputTimer.setVisibility(View.INVISIBLE);
                }
            }
        });
        mTextViewTestName.setText(mTest.getTestName());
        mTextViewNumOfQuest.setText("" + mSeekBarNumOfQuestions.getProgress());
        mButtonCancelTest.setOnClickListener(this);
        mButtonStartTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_cancel_session: {
                mCallback.cancelSession();
                break;
            }
            case R.id.button_start_test: {

                if (mSeekBarNumOfQuestions.getProgress() == 0) {
                    Toast.makeText(view.getContext(), "Good boy. You did not make mistakes!" +
                            " Now choose a few more questions!", Toast.LENGTH_LONG).show();
                    break;
                }

                long timer = 0;

                if (mSwitchTimer.isChecked() && !mTextInputTimeLimit.getText().toString().isEmpty()){
                    timer = 60000 * Integer.parseInt(mTextInputTimeLimit.getText().toString());
                }

                mTestSession = new TestSession(
                        mTest,
                        mSeekBarNumOfQuestions.getProgress(),
                        timer,
                        mSwitchShuffle.isChecked());
                mCallback.callSessionProcess(mTestSession);
                break;
            }
        }
    }
}
