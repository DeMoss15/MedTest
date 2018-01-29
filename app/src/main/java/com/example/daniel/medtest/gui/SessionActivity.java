
package com.example.daniel.medtest.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.logic.TestSession;

public class SessionActivity extends AppCompatActivity implements FragmentSubSession.OnHeadlineSelectedListener {

    private final int SESSION_EMPTY = 0;
    private final int SESSION_START = 1;
    private final int SESSION_PROCESS = 2;
    private final int SESSION_RESULT = 3;

    private FragmentSessionStart mFragmentSessionStart = new FragmentSessionStart();
    private FragmentSessionProcess mFragmentSessionProcess = new FragmentSessionProcess();
    private FragmentSessionResult mFragmentSessionResult = new FragmentSessionResult();
    private TestSession mSession;
    private boolean mIsActive;
    private int mQueue = SESSION_EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Test test = (Test)getIntent().getSerializableExtra("TEST_TO_START");

        if(test != null){
            mSession = new TestSession(test);
            callSessionStart();
        } else {
            cancelSession();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActive = true;

        if (mQueue == SESSION_START) {
            callSessionStart();
        } else if (mQueue == SESSION_PROCESS) {
            callSessionProcess();
        } else if (mQueue == SESSION_RESULT) {
            callSessionResult();
        }
        mQueue = SESSION_EMPTY;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsActive = false;
    }

    @Override
    public void callSessionStart() {
        mFragmentSessionStart.startSession(mSession);
        if(mIsActive){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.session_container, mFragmentSessionStart)
                    .commit();
        } else {
            mQueue = SESSION_START;
        }
    }

    @Override
    public void callSessionProcess() {
        mFragmentSessionProcess.setSession(mSession);
        if(mIsActive){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.session_container, mFragmentSessionProcess)
                    .commit();
        } else {
            mQueue = SESSION_PROCESS;
        }
    }

    @Override
    public void callSessionResult() {
        mFragmentSessionResult.setSession(mSession);
        if(mIsActive){
            getFragmentManager()
                .beginTransaction()
                .replace(R.id.session_container, mFragmentSessionResult)
                .commit();
        } else {
            mQueue = SESSION_RESULT;
        }
    }

    @Override
    public void cancelSession() {
        Intent intent = new Intent(SessionActivity.this, MainActivity.class);
        startActivity(intent);
    }
}