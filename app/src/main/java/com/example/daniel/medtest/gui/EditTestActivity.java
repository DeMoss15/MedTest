package com.example.daniel.medtest.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;

public class EditTestActivity extends AppCompatActivity implements FragmentSubEdit.OnHeadlineSelectedListener{

    FragmentEditTest mFragmentEditTest = new FragmentEditTest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);

        callTestEditFragment((Test)getIntent().getSerializableExtra("TEST_TO_EDIT"));
    }

    @Override
    public void callQuestionEditFragment(Question question) {

    }

    @Override
    public void callTestEditFragment(Test test) {
        mFragmentEditTest.edit(test);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_edit_container, mFragmentEditTest)
                .commit();
    }

    @Override
    public void cancelEditing() {
        Intent intent = new Intent(EditTestActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
