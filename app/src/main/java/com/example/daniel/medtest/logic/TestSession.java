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

    private Test mCoosedTest;
    private int mNumOfQuestionsInSession;
    private long mTimeInMilliseconds;
    private boolean mShuffling;
    private Set<Integer> mShowedQuestions;

    public TestSession(Test test, int numOfQuestionsInSession, long timeForTesting, boolean shuffling){
        this.mCoosedTest = test;
        this.mNumOfQuestionsInSession = numOfQuestionsInSession;
        this.mTimeInMilliseconds = timeForTesting;
        this.mShuffling = shuffling;
        this.mShowedQuestions = new LinkedHashSet<>();
    }

    public Question getNextQuestion(){
        if (mShowedQuestions.size() == mNumOfQuestionsInSession){
            return null;
        }

        int nextNum = new Random().nextInt(mCoosedTest.getQuestions().size());

        if (mShuffling) {
            nextNum = new Random().nextInt(mCoosedTest.getQuestions().size());

            while (mShowedQuestions.contains(nextNum)) {
                nextNum = new Random().nextInt(mCoosedTest.getQuestions().size());
            }

            mShowedQuestions.add(nextNum);
        } else {
            // i++ in other way
            mShowedQuestions.add(mShowedQuestions.size());
        }

        return mCoosedTest.getQuestions().get(nextNum);
    }
}
