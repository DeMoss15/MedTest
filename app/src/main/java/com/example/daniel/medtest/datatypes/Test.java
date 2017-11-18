package com.example.daniel.medtest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class Test implements Serializable {

    private String mTestName;
    private List<Question> mQuestions;
    private int mId;

    public Test() {
        mTestName = "";
        mQuestions = new LinkedList<>();
    }

    public Test(String testName) {
        this.mTestName = testName;
        mQuestions = new LinkedList<>();
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

    public void setId(int Id) {
        this.mId = Id;
    }

    public int getId() {
        return mId;
    }
}
