package com.example.daniel.medtest.logic;

import com.example.daniel.medtest.database.DBHandler;
import com.example.daniel.medtest.datatypes.Test;

import java.util.List;

/**
 * Created by Daniel on 13.11.2017.
 */

public final class ListOfTests {

    private static List<Test> sTests;
    private static DBHandler sDB;

    private static final ListOfTests ourInstance = new ListOfTests();

    public static ListOfTests getInstance() {
        return ourInstance;
    }

    private ListOfTests() {
    }

    public static void setDBHandler(DBHandler dbHandler) {
        sDB = dbHandler;
        sTests = sDB.getTestsList();
    }

    public void addTest(Test test) {
        test.setId(sTests.size() + 1);
        sTests.add(test);
        /*TODO push test to database*/
        sDB.putTest(test);
    }

    public List<Test> getTests(){
        return sTests;
    }

    public Test getTestFromDB(Test test) {
        return sDB.getTest(test);
    }
}
