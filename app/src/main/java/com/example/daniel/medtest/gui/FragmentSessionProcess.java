package com.example.daniel.medtest.gui;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Answer;
import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.logic.AnswersAdapter;
import com.example.daniel.medtest.logic.TestSession;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class FragmentSessionProcess extends FragmentSubSession implements View.OnClickListener{

    @BindView(R.id.lv_answers)
    ListView mListViewAnswers;
    @BindView(R.id.tv_test_name_proc)
    TextView mTextViewTestName;
    @BindView(R.id.tv_question_proc)
    TextView mTextViewQuestion;
    @BindView(R.id.text_view_timer)
    TextView mTextViewTime;
    @BindView(R.id.tv_questions_counter)
    TextView mTextViewQuestionsCounter;
    @BindView(R.id.button_commit)
    Button mButtonCommit;
    @BindView(R.id.button_finish_test)
    Button mButtonFinish;

    private Context mContext;

    private TestSession mSession;
    private Question mCurrentQuestion;
    private AnswersAdapter mAnswersAdapter;

    private boolean mIsAnswerCommitted;

    private int mNumOfRightAnswers = 0;
    private List<Answer> mShuffledAnswers;

    private boolean mIsTimerRunning = false;
    private CountDownTimer mTimer;
    private long mTimeLeft;

    private int mQuestionsCounter = 0;

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

        mTextViewQuestion.setMovementMethod(new ScrollingMovementMethod());
        mTextViewTestName.setText(mSession.getTestName());

        mListViewAnswers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListViewAnswers.setSelector(android.R.color.holo_green_light);
        mListViewAnswers.setSelected(true);
        mListViewAnswers.setStackFromBottom(true);
        mButtonCommit.setOnClickListener(this);
        mButtonFinish.setOnClickListener(this);

        mTimeLeft = mSession.getTimeInMilliseconds();
        if (mTimeLeft != 0){
            startStopTimer();
        } else {
            mTextViewTime.setText("");
        }

        nextQuestion();
    }

    private void startStopTimer() {
        if (!mIsTimerRunning) {
            startTimer();
        } else {
            stopTimer();
        }
        mIsTimerRunning = !mIsTimerRunning;
    }

    private void startTimer() {
        mTimer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long t) {
                mTimeLeft = t;
                updateTime();
            }

            @Override
            public void onFinish() {
                mTimeLeft = 0;
                endProcess();
            }
        }.start();
    }

    private void stopTimer() {
        mTimer.cancel();
    }

    private void updateTime() {
        int minutes = (int) mTimeLeft / 60000;
        int seconds = (int) mTimeLeft % 60000 / 1000;

        StringBuilder sbTime = new StringBuilder();
        if (minutes < 10){
            sbTime.append("0");
        }
        sbTime.append(minutes).append(":");
        if (seconds < 10){
            sbTime.append("0");
        }
        sbTime.append(seconds);

        mTextViewTime.setText(sbTime);
    }

    private void endProcess() {
        mSession.setTimeInMilliseconds(mSession.getTimeInMilliseconds() - mTimeLeft);
        mSession.setNumOfRightAnswers(mNumOfRightAnswers);
        mCallback.callSessionResult(mSession);
    }

    private void nextQuestion(){
        Question q = mSession.getNextQuestion();

        if (q != null) {
            mCurrentQuestion = q;

            mShuffledAnswers = new LinkedList<>();
            Set<Integer> numbers = new LinkedHashSet<>();
            int size = mCurrentQuestion.getAnswers().size();

            while (mShuffledAnswers.size() != size) {
                int newNum = Math.abs(new Random().nextInt() % size);
                if (!numbers.contains(newNum)) {
                    numbers.add(newNum);
                    mShuffledAnswers.add(mCurrentQuestion.getAnswers().get(newNum));
                }
            }

            mAnswersAdapter = new AnswersAdapter(
                    mContext,
                    android.R.layout.simple_list_item_1,
                    mShuffledAnswers);

            updateLayout();
        } else {
            endProcess();
        }
    }

    private void updateLayout() {
        StringBuilder sbQuestCounter = new StringBuilder();
        sbQuestCounter
                .append(++mQuestionsCounter)
                .append("/")
                .append(mSession.getNumOfQuestionsInSession());

        mListViewAnswers.setAdapter(mAnswersAdapter);
        mTextViewQuestionsCounter.setText(sbQuestCounter);
        mTextViewQuestion.setText(mCurrentQuestion.getQuestion());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_finish_test: {
                startStopTimer();
                endProcess();
                break;
            }
            case R.id.button_commit: {
                if (mIsAnswerCommitted) {
                    //switch to next question
                    nextQuestion();
                    mButtonCommit.setText(getString(R.string.commit));
                    mListViewAnswers.setSelector(android.R.color.holo_green_light);
                    mListViewAnswers.setEnabled(true);
                    mIsAnswerCommitted = !mIsAnswerCommitted;
                } else {
                    //committing answer
                    Answer usersAnswer = (Answer)mListViewAnswers.getItemAtPosition(
                            mListViewAnswers.getCheckedItemPosition());

                    if (usersAnswer != null) {
                        mButtonCommit.setText(getString(R.string.next));

                        if (usersAnswer.isIsRight()) {
                            mNumOfRightAnswers++;
                        } else {
                            mListViewAnswers.setSelector(android.R.color.holo_red_dark);

                            // highlighting right answer
                            for (int i = 0; i < mShuffledAnswers.size(); i++) {
                                if (((Answer)mListViewAnswers.getAdapter().getItem(i)).isIsRight()) {
                                    mListViewAnswers.getChildAt(i).setBackgroundColor(
                                            getResources().getColor(android.R.color.holo_green_light));
                                }
                            }
                        }

                        mListViewAnswers.setEnabled(false);
                        mIsAnswerCommitted = !mIsAnswerCommitted;
                    } else {
                        Toast.makeText(mContext, R.string.choose_answer, Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            }
        }
    }
}
