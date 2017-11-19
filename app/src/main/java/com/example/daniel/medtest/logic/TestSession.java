package com.example.daniel.medtest.logic;

import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Daniel on 16.11.2017.
 */

public final class TestSession {

    private Test mChoosedTest;
    private int mNumOfQuestionsInSession;
    private long mTimeInMilliseconds;
    private boolean mShuffling;
    private Set<Integer> mShowedQuestions;
    private int mNumOfRightAnswers;

    public TestSession(Test test, int numOfQuestionsInSession, long timeForTesting, boolean shuffling){
        this.mChoosedTest = test;
        this.mNumOfQuestionsInSession = numOfQuestionsInSession;
        this.mTimeInMilliseconds = timeForTesting;
        this.mShuffling = shuffling;
        this.mShowedQuestions = new LinkedHashSet<>();
    }

    public Question getNextQuestion(){
        if (mShowedQuestions.size() == mNumOfQuestionsInSession){
            return null;
        }

        int nextNum;

        if (mShuffling) {
            nextNum = new Random().nextInt(mChoosedTest.getQuestions().size());

            while (mShowedQuestions.contains(nextNum)) {
                nextNum = new Random().nextInt(mChoosedTest.getQuestions().size());
            }

            mShowedQuestions.add(nextNum);
        } else {
            // i++ in another way
            nextNum = mShowedQuestions.size();
        }

        mShowedQuestions.add(nextNum);

        return mChoosedTest.getQuestions().get(nextNum);
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public void setTimeInMilliseconds(long TimeInMilliseconds) {
        this.mTimeInMilliseconds = TimeInMilliseconds;
    }

    public String getTestName() {
        return mChoosedTest.getTestName();
    }

    public int getNumOfQuestions() {
        return mShowedQuestions.size();
    }

    public void setNumOfRightAnswers(int numOfRightAnswers) {
        this.mNumOfRightAnswers = numOfRightAnswers;
    }

    public int getNumOfRightAnswers() {
        return mNumOfRightAnswers;
    }
}
