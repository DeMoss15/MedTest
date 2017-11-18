package com.example.daniel.medtest.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class Question implements Serializable{

    private String mQuestion;
    private List<Answer> mAnswers;

    public Question(String question){
        this.mQuestion = question;
    }

    public String getQuestion(){
        return mQuestion;
    }

    public void setAnswers(List<Answer> answers) {
        this.mAnswers = answers;
    }

    public List<Answer> getAnswers() {
        return mAnswers;
    }
}
