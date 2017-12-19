package com.example.daniel.medtest.datatypes;

import java.io.Serializable;

/**
 * Created by Daniel on 17.11.2017.
 */

public final class Answer implements Serializable{

    private boolean mIsRight;
    private String mName;
    private int mId;

    public Answer(String name, boolean isRight) {
        this.mName = name;
        this.mIsRight = isRight;
    }

    public boolean isRight() {
        return mIsRight;
    }

    public String getName() {
        return mName;
    }

    public boolean equals(Answer a){
        return this.getId() == a.getId();

    }

    public int getId() {
        return mId;
    }

    public void setId(int Id) {
        this.mId = Id;
    }
}
