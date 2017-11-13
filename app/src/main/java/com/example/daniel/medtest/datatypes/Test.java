package com.example.daniel.medtest.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class Test {

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

    public void addQuestion(String question, Map<String, Boolean> answers) {
        mQuestions.add(new Question(question, answers));
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
