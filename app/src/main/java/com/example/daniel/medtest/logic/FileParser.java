package com.example.daniel.medtest.logic;

import android.os.AsyncTask;
import android.util.Log;

import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.gui.FragmentTestsOverview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Daniel on 13.11.2017.
 */

public final class FileParser extends AsyncTask {

    private final String NEW_TEST = "$";
    private final String NEW_QUESTION = "?";
    private final String RIGHT_ANSWER = "*";
    private final String LOG_PARSING = "PARSING";

    private File mFile = null;
    private ListOfTests mTests = ListOfTests.getInstance();
    private FragmentTestsOverview fragment = null;

    @Override
    protected Object doInBackground(Object[] objects) {
        /*TODO Pars file here*/
        //mTests.addTest(new Test("Test from parallel thread"));
        if (!mFile.canRead()){
            Log.d(LOG_PARSING, "File can't be read");
        }

        try (FileReader reader = new FileReader(mFile)) {

            Log.d(LOG_PARSING, "Parsing starts");
            String lineOfText;
            Test currentTest = null;
            Question currentQuestion = null;
            Map<String, Boolean> currentAnswersSet = null;
            BufferedReader bufferedReader = new BufferedReader(reader);

            while ((lineOfText = bufferedReader.readLine()) != null) {

                if (lineOfText.isEmpty()) {
                    // if empty line miss iteration
                    break;

                } else if (lineOfText.startsWith(NEW_TEST)) {

                    // if line contains new test name, add previous test in list and create new one
                    if(currentTest != null) {
                        mTests.addTest(currentTest);
                    }
                    Log.d(LOG_PARSING, "New test " + lineOfText.indexOf(NEW_TEST) + 1);
                    currentTest = new Test(lineOfText.substring(lineOfText.indexOf(NEW_TEST) + 1));

                } else if (lineOfText.startsWith(NEW_QUESTION)) {

                    // if line contains new question add previous to current test and create new one
                    if (currentTest != null
                            && currentQuestion != null
                            && currentAnswersSet != null) {
                        currentQuestion.setAnswers(currentAnswersSet);
                        currentTest.addQuestion(currentQuestion);
                    }
                    Log.d(LOG_PARSING, "New question " + lineOfText.indexOf(NEW_QUESTION) + 1);
                    currentQuestion = new Question(lineOfText.substring(lineOfText.indexOf(NEW_QUESTION) + 1));
                    currentAnswersSet = new HashMap<>();

                } else if (lineOfText.startsWith(RIGHT_ANSWER)) {

                    // add right answer
                    if (currentAnswersSet != null) {
                        currentAnswersSet.put(lineOfText.substring(lineOfText.indexOf(RIGHT_ANSWER) + 1),
                                true);
                    }

                } else {

                    // add answer
                    if (currentAnswersSet != null) {
                        currentAnswersSet.put(lineOfText, false);
                    }

                }
            }// end of while cycle

            bufferedReader.close();
            reader.close();

        } catch (IOException e) {
            // Catching exeption of wrong input file
            Log.d(LOG_PARSING, "Parsing failed " +  e.getMessage());
        }

        Log.d(LOG_PARSING, "Parsing ends");
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (fragment != null)
            fragment.updateList();
    }

    public void setFile(File file) {
        this.mFile = file;
    }

    public void setFragment(FragmentTestsOverview fragment) {
        this.fragment = fragment;
    }
}
