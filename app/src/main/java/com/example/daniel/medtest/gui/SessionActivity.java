
package com.example.daniel.medtest.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.logic.TestSession;

public class SessionActivity extends AppCompatActivity implements FragmentSubSession.OnHeadlineSelectedListener {

    FragmentSessionStart mFragmentSessionStart = new FragmentSessionStart();
    FragmentSessionProcess mFragmentSessionProcess = new FragmentSessionProcess();
    FragmentSessionResult mFragmentSessionResult = new FragmentSessionResult();
    private TestSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Test test = (Test)getIntent().getSerializableExtra("TEST_TO_START");
        callSessionStart(test);
    }

    @Override
    public void callSessionStart(Test test) {
        mFragmentSessionStart.startSession(test, mSession);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.session_container, mFragmentSessionStart)
                .commit();
    }

    @Override
    public void callSessionProcess(TestSession session) {
        mSession = session;
        mFragmentSessionProcess.setSession(mSession);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.session_container, mFragmentSessionProcess)
                .commit();
    }

    @Override
    public void callSessionResult(TestSession session) {
        mFragmentSessionResult.setSession(session);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.session_container, mFragmentSessionResult)
                .commit();
    }

    @Override
    public void cancelSession() {
        Intent intent = new Intent(SessionActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
