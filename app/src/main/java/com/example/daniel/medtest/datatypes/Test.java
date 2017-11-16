package com.example.daniel.medtest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class Test implements Serializable {

    private String mTestName;
    private List<Question> mQuestions;

    public Test() {
        mTestName = "";
        mQuestions = new ArrayList<>();
    }

    public Test(String testName) {
        this.mTestName = testName;
        mQuestions = new ArrayList<>();
    }

    public void setTestName(String testName) {
        this.mTestName = testName;
    }

    public String getTestName() {
        return mTestName;
    }

    public void addQuestion(Question question) {
        mQuestions.add(question);
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
