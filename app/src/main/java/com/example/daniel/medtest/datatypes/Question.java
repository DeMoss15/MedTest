package com.example.daniel.medtest.datatypes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class Question implements Serializable{

    private String mQuestion;
    private Map<String, Boolean> mAnswers;

    public Question() {
        mQuestion = "Empty question";
        mAnswers = new HashMap<>();
    }

    public Question(String question){
        this.mQuestion = question;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }

    public void setAnswers(Map<String, Boolean> answers) {
        this.mAnswers = answers;
    }
}
