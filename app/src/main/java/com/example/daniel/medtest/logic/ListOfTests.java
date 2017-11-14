package com.example.daniel.medtest.logic;

import com.example.daniel.medtest.datatypes.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class ListOfTests {

    private List<Test> mTests;

    private static final ListOfTests ourInstance = new ListOfTests();

    public static ListOfTests getInstance() {
        return ourInstance;
    }

    private ListOfTests() {
        mTests = new ArrayList<>();
    }

    public void addTest(Test test) {
        mTests.add(test);
    }

    public String[] getTestsNames() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < mTests.size(); i++) {
            result.append(mTests.get(i).getTestName());
            if (i < mTests.size() - 1){
                result.append("/S/");
            }
        }
        return result.toString().split("/S/");
    }
}
