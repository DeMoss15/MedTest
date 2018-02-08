package com.example.daniel.medtest.gui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentEditQuestion extends FragmentSubEdit implements View.OnClickListener{

    @BindView(R.id.button_add_answer)
    Button mButtonAddAnswer;
    @BindView(R.id.button_cancel_edtitng_question)
    Button mButtonCancel;
    @BindView(R.id.button_save_question)
    Button mButtonSave;

    private Question mQuestionToEdit;
    private Question mTempleQuestion;

    public FragmentEditQuestion() {
        // Required empty public constructor
    }

    public void editQuestion(Question q) {
        if (q != null) {
            this.mQuestionToEdit = q;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_question, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonAddAnswer.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.button_add_answer: {
                break;
            }

            case R.id.button_cancel_edtitng_question: {
                mCallback.callTestEditFragment();
                break;
            }

            case R.id.button_save_question: {
                break;
            }
        }
    }
}
