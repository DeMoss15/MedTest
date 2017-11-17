package com.example.daniel.medtest.datatypes;

import java.io.Serializable;

/**
 * Created by Daniel on 17.11.2017.
 */

public final class Answer implements Serializable{

    private boolean mIsRight;
    private String mName;

    public Answer(String name, boolean isRight) {
        this.mName = name;
        this.mIsRight = isRight;
    }

    public boolean isIsRight() {
        return mIsRight;
    }

    public String getName() {
        return mName;
    }
}
