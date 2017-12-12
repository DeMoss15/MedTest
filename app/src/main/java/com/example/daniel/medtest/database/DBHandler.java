package com.example.daniel.medtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daniel.medtest.datatypes.Answer;
import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 18.11.2017.
 */

public final class DBHandler extends SQLiteOpenHelper{

    private static final String DB_NAME = "medTestDB.db";
    private static String DB_PATH ;

    private static final String TABLE_TESTS = "TESTS";
    private static final String KEY_TEST_ID = "_ID";
    private static final String KEY_TEST_NAME = "TEST_NAME";

    private static final String TABLE_QUESTIONS = "QUESTIONS";
    private static final String KEY_QUESTION_ID = "_ID";
    private static final String KEY_QUESTION = "QUESTION";
    private static final String KEY_QUESTION_TEST_ID = "TEST_ID";

    private static final String TABLE_ANSWERS = "ANSWERS";
    private static final String KEY_ANSWER_ID = "_ID";
    private static final String KEY_ANSWER = "ANSWER";
    private static final String KEY_ANSWER_IS_RIGHT = "IS_RIGHT";
    private static final String KEY_ANSWER_QUESTION_ID = "QUESTION_ID";

    private final Context mContext;
    private SQLiteDatabase mDB;

    public DBHandler(Context context) {

        super(context, DB_NAME, null, 1);
        this.mContext = context;
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(!dbExist){

            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        mDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(mDB != null)
            mDB.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion) {
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Test getTest(Test test){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQueryQuestions = "SELECT " + "*" + " FROM " + TABLE_QUESTIONS + " WHERE "
                + KEY_QUESTION_TEST_ID + " LIKE " + test.getId();

        Cursor cursorQuestions = db.rawQuery(selectQueryQuestions, null);

        if (cursorQuestions.moveToFirst()) {
            do {
                Question question = new Question(cursorQuestions.getString(cursorQuestions.getColumnIndex(KEY_QUESTION)));
                question.setId(cursorQuestions.getInt(cursorQuestions.getColumnIndex(KEY_QUESTION_ID)));

                String selectQueryAnswers = "SELECT " + "*" + " FROM " + TABLE_ANSWERS + " WHERE "
                        + KEY_ANSWER_QUESTION_ID + " LIKE " + question.getId();

                Cursor cursorAnswers = db.rawQuery(selectQueryAnswers, null);

                if (cursorAnswers.moveToFirst()) {
                    List<Answer> answers = new ArrayList<>();

                    do {
                        answers.add(
                                new Answer(
                                        cursorAnswers.getString(cursorAnswers.getColumnIndex(KEY_ANSWER)),
                                        cursorAnswers.getInt(cursorAnswers.getColumnIndex(KEY_ANSWER_IS_RIGHT))
                                                == 1
                                )
                        );
                    } while (cursorAnswers.moveToNext());

                    question.setAnswers(answers);
                }

                cursorAnswers.close();

                test.addQuestion(question);

            } while (cursorQuestions.moveToNext());
        }
        cursorQuestions.close();

        return test;
    }

    public List<Test> getTestsList() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Test> result = new ArrayList<>();

        String selectQuery = "SELECT " + "*" + " FROM " + TABLE_TESTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test(cursor.getString(cursor.getColumnIndex(KEY_TEST_NAME)));
                test.setId(cursor.getInt(cursor.getColumnIndex(KEY_TEST_ID)));
                result.add(test);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public void putTest(Test test){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TEST_NAME, test.getTestName());
        db.insert(TABLE_TESTS, null, values);

        Cursor cursor = db.rawQuery("SELECT max(_id) FROM "+ TABLE_TESTS, null);
        cursor.moveToFirst();
        test.setId(cursor.getInt(0));

        List<Question> questions = test.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            values = new ContentValues();

            values.put(KEY_QUESTION, questions.get(i).getQuestion());
            values.put(KEY_QUESTION_TEST_ID, test.getId());

            db.insert(TABLE_QUESTIONS, null, values);

            List<Answer> answers = questions.get(i).getAnswers();

            for (int j = 0; j < answers.size(); j++) {
                cursor = db.rawQuery("SELECT max(_id) FROM "+ TABLE_QUESTIONS, null);
                cursor.moveToFirst();

                values = new ContentValues();

                values.put(KEY_ANSWER, answers.get(j).getName());
                values.put(KEY_ANSWER_IS_RIGHT, answers.get(j).isIsRight());
                values.put(KEY_ANSWER_QUESTION_ID, cursor.getInt(0));

                db.insert(TABLE_ANSWERS, null, values);
            }
        }

        cursor.close();
    }

    public void deleteTest(Test test) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_TESTS + " WHERE " + KEY_TEST_ID + " = " + test.getId());
    }
}
