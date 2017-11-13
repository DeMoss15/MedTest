package com.example.daniel.medtest.datatypes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class Question {

    private String mQuestion;
    private Map<String, Boolean> mAnswers;

    Question() {
        mQuestion = "Empty question";
        mAnswers = new HashMap<>();
    }

    Question(String Question, Map<String, Boolean> Answers){
        this.mQuestion =Question;
        this.mAnswers = Answers;
    }

    public void setQuestion(String Question) {
        this.mQuestion = Question;
    }

    public void setAnswers(Map<String, Boolean> Answers) {
        this.mAnswers = Answers;
    }
}
