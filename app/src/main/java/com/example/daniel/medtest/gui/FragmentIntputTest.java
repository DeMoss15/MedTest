package com.example.daniel.medtest.gui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.logic.ListOfTests;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class FragmentIntputTest extends ReplaceabelFragment {

    @BindView(R.id.lv_questions)
    ListView mListViewQuestions;
    @BindView(R.id.ti_test_name)
    TextInputEditText mTextInputTestName;
    @BindView(R.id.button_add_question)
    Button mButtonAddQuestion;
    @BindView(R.id.button_add_test)
    Button mButtonAddTest;

    ListOfTests mTests = ListOfTests.getInstance();

    public FragmentIntputTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intput_test, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Context context = getContext();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == null || !(view instanceof TextView)) {
                    return; //нет свойства text
                }
                switch (view.getId()){
                    case R.id.button_add_question: {
                        Toast.makeText(context, "This function is in dev", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.button_add_test: {
                        createTest();
                        break;
                    }
                }
            }
        };

        mButtonAddQuestion.setOnClickListener(listener);
        mButtonAddTest.setOnClickListener(listener);
    }

    private void createTest() {
        Test newTest = new Test(mTextInputTestName.getText().toString());

        /*parsing fragments*/

        mTests.addTest(newTest);
        mCallback.callOverviewFragment();
    }
}