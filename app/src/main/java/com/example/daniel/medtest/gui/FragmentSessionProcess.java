package com.example.daniel.medtest.gui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Answer;
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
    @BindView(R.id.text_view_timer)
    TextView mTextViewTime;
    @BindView(R.id.button_commit)
    Button mButtonCommit;
    @BindView(R.id.button_finish_test)
    Button mButtonFinish;

    private TestSession mSession;
    private Question mCurrentQuestion;
    private boolean mIsAnswerCommited;
    private Context mContext;
    private int mNumOfRightAnswers = 0;

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

            if (mSession.getTimeInMilliseconds() == 0) {
                mTextViewTime.setText("");
            }
            mListViewAnswers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            mListViewAnswers.setSelector(android.R.color.holo_green_light);
            mListViewAnswers.setSelected(true);
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
            endProcess();
        }

        updateLayout();
    }

    private void updateLayout() {
        mTextViewQuestion.setText(mCurrentQuestion.getQuestion());

        AnswersAdapter adapter = new AnswersAdapter(
                mContext,
                android.R.layout.simple_list_item_1,
                mCurrentQuestion.getAnswers());

        mListViewAnswers.setAdapter(adapter);
    }

    private void endProcess() {
        mSession.setNumOfRightAnswers(mNumOfRightAnswers);
        mCallback.callSessionResult(mSession);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_finish_test: {
                endProcess();
                break;
            }
            case R.id.button_commit: {
                if (mIsAnswerCommited) {
                    //switch to next question
                    nextQuestion();
                    mButtonCommit.setText(BTN_COMMIT);
                    mListViewAnswers.setEnabled(true);
                    mListViewAnswers.setSelector(android.R.color.holo_green_light);
                    mIsAnswerCommited = !mIsAnswerCommited;
                } else {
                    //committing answer
                    mButtonCommit.setText(BTN_NEXT);

                    Answer usersAnswer = (Answer)mListViewAnswers.getItemAtPosition(
                            mListViewAnswers.getCheckedItemPosition());

                    if (usersAnswer != null) {
                        if (usersAnswer.isIsRight()) {
                            mNumOfRightAnswers++;
                        } else {
                            mListViewAnswers.setSelector(android.R.color.holo_red_dark);
                            /*TODO Highlight here right answer*/
                        }

                        mListViewAnswers.setEnabled(false);
                        mIsAnswerCommited = !mIsAnswerCommited;
                    } else {
                        Toast.makeText(mContext, "Choose your answer!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            }
        }
    }
}
