package com.example.daniel.medtest.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;

public class EditTestActivity extends AppCompatActivity implements FragmentSubEdit.OnHeadlineSelectedListener{

    FragmentEditTest mFragmentEditTest = new FragmentEditTest();
    FragmentEditQuestion mFragmentEditQuestion = new FragmentEditQuestion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);

        mFragmentEditTest.edit((Test)getIntent().getSerializableExtra("TEST_TO_EDIT"));
        callTestEditFragment();
    }

    @Override
    public void callQuestionEditFragment(Question question) {
        mFragmentEditQuestion.editQuestion(question);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_edit_container, mFragmentEditQuestion)
                .commit();
    }

    @Override
    public void callTestEditFragment() {
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
