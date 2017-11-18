package com.example.daniel.medtest.gui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.logic.AnswersAdapter;
import com.example.daniel.medtest.logic.TestSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSessionProcess extends FragmentSubSession implements View.OnClickListener{

    private final String BTN_NEXT = "Next";
    private final String BTN_COMMIT = "Commit";

    @BindView(R.id.lv_answers)
    ListView mListViewAnswers;
    @BindView(R.id.tv_test_name_proc)
    TextView mTextViewTestName;
    @BindView(R.id.tv_question_proc)
    TextView mTextViewQuestion;
    @BindView(R.id.button_commit)
    Button mButtonCommit;
    @BindView(R.id.button_finish_test)
    Button mButtonFinish;

    private TestSession mSession;
    private Question mCurrentQuestion;
    private boolean mIsAnswerCommited;
    private Context mContext;

    public FragmentSessionProcess() {
        // Required empty public constructor
    }

    public void setSession(TestSession session) {
        this.mSession = session;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_session_process, container, false);
        ButterKnife.bind(this, view);
        mContext = view.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mSession == null){
            Log.d("Session", "Session is null");
            mCallback.cancelSession();
        } else {
            mCurrentQuestion = mSession.getNextQuestion();

            mTextViewTestName.setText(mSession.getTestName());
            updateLayout();

            mListViewAnswers.setStackFromBottom(true);
            mButtonCommit.setOnClickListener(this);
            mButtonFinish.setOnClickListener(this);
        }
    }

    private void nextQuestion(){
        Question q = mSession.getNextQuestion();

        if (q != null) {
            mCurrentQuestion = q;
        } else {
            mCallback.callSessionResult(mSession);
        }

        updateLayout();
    }

    private void commitAnswer() {
        /*TODO check selected answer and add it to rate of session*/
    }

    private void updateLayout() {
        mTextViewQuestion.setText(mCurrentQuestion.getQuestion());

        AnswersAdapter adapter = new AnswersAdapter(
                mContext,
                android.R.layout.simple_list_item_1,
                mCurrentQuestion.getAnswers());

        mListViewAnswers.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_finish_test: {
                mCallback.callSessionResult(mSession);
                break;
            }
            case R.id.button_commit: {
                if (mIsAnswerCommited) {
                    /*TODO next question*/
                    nextQuestion();
                    mButtonCommit.setText(BTN_COMMIT);
                } else {
                    /*TODO commit answer*/
                    mButtonCommit.setText(BTN_NEXT);
                    commitAnswer();
                }

                mIsAnswerCommited = !mIsAnswerCommited;
                break;
            }
        }
    }
}
