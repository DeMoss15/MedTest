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
            // i++ in other way
            nextNum = mShowedQuestions.size();
        }

        mShowedQuestions.add(nextNum);

        return mChoosedTest.getQuestions().get(nextNum);
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getTestName() {
        return mChoosedTest.getTestName();
    }

    public int getNumOfQuestionsInTest() {
        return mChoosedTest.getQuestions().size();
    }

    public int getNumOfQuestions() {
        return mNumOfQuestionsInSession;
    }
}
