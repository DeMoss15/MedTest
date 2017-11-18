package com.example.daniel.medtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daniel.medtest.datatypes.Answer;
import com.example.daniel.medtest.datatypes.Question;
import com.example.daniel.medtest.datatypes.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 18.11.2017.
 */

public final class DBHandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tests.db";

    private static final String TABLE_TESTS = "TESTS";
    private static final String KEY_TEST_ID = "ID";
    private static final String KEY_TEST_NAME = "TEST_NAME";
    private static final String KEY_TEST_OBJ_ID = "TEST_ID";

    private static final String TABLE_QUESTIONS = "QUESTIONS";
    private static final String KEY_QUESTION_ID = "ID";
    private static final String KEY_QUESTION = "QUESTION";
    private static final String KEY_QUESTION_OBJ_ID = "QUESTION_ID";
    private static final String KEY_QUESTION_PARENT_ID = "QUESTION_PARENT_ID";

    private static final String TABLE_ANSWERS = "ANSWERS";
    private static final String KEY_ANSWER_ID = "ID";
    private static final String KEY_ANSWER = "ANSWER";
    private static final String KEY_ANSWER_IS_RIGHT = "IS_RIGHT";
    private static final String KEY_ANSWER_PARENT_ID = "ANSWER_PARENT_ID";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TEST_TABLE = "CREATE TABLE "
                + TABLE_TESTS + "("
                + KEY_TEST_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEST_NAME + " TEXT,"
                + KEY_TEST_OBJ_ID + " INTEGER "
                + ")";

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE "
                + TABLE_QUESTIONS + "("
                + KEY_QUESTION_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION + " TEXT,"
                + KEY_QUESTION_OBJ_ID + " INTEGER,"
                + KEY_QUESTION_PARENT_ID + " INTEGER "
                + ")";

        String CREATE_ANSWERS_TABLE = "CREATE TABLE "
                + TABLE_ANSWERS + "("
                + KEY_ANSWER_ID + " INTEGER PRIMARY KEY,"
                + KEY_ANSWER + " TEXT,"
                + KEY_ANSWER_IS_RIGHT + " INTEGER,"
                + KEY_ANSWER_PARENT_ID + " INTEGER "
                + ")";

        sqLiteDatabase.execSQL(CREATE_TEST_TABLE);
        sqLiteDatabase.execSQL(CREATE_QUESTIONS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ANSWERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);

        onCreate(sqLiteDatabase);
    }

    public Test getTest(Test test){
        Test resTest = test;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQueryQuestions = "SELECT " + "*" + " FROM " + TABLE_QUESTIONS + " WHERE "
                + KEY_QUESTION_PARENT_ID + " LIKE " + test.getId();

        Cursor cursorQuestions = db.rawQuery(selectQueryQuestions, null);

        if (cursorQuestions.moveToFirst()) {
            do {
                Question question =  new Question(cursorQuestions.getString(cursorQuestions.getColumnIndex(KEY_QUESTION)));

                String selectQueryAnswers = "SELECT " + "*" + " FROM " + TABLE_ANSWERS + " WHERE "
                        + KEY_ANSWER_PARENT_ID + " LIKE "
                        + cursorQuestions.getInt(cursorQuestions.getColumnIndex(KEY_QUESTION_OBJ_ID));

                Cursor cursorAnswers = db.rawQuery(selectQueryAnswers, null);

                if (cursorAnswers.moveToFirst()) {
                    List<Answer> answers = new ArrayList<>();

                    do {
                        boolean isRight = false;

                        if (cursorAnswers.getInt(cursorAnswers.getColumnIndex(KEY_ANSWER_IS_RIGHT)) == 1){
                            isRight = true;
                        }

                        answers.add(
                                new Answer(
                                        cursorAnswers.getString(cursorAnswers.getColumnIndex(KEY_ANSWER)),
                                        isRight
                                )
                        );
                    } while (cursorAnswers.moveToNext());

                    question.setAnswers(answers);
                }

                cursorAnswers.close();

                resTest.addQuestion(question);

            } while (cursorQuestions.moveToNext());
        }
        cursorQuestions.close();

        return resTest;
    }

    public List<Test> getTestsList() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Test> result = new ArrayList<>();

        /*TODO return here list of tests with data*/
        String selectQuery = "SELECT " + "*" + " FROM " + TABLE_TESTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test(cursor.getString(cursor.getColumnIndex(KEY_TEST_NAME)));
                test.setId(cursor.getInt(cursor.getColumnIndex(KEY_TEST_OBJ_ID)));
                result.add(test);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public void putTest(Test test){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        /*TODO put here test in database*/

        values.put(KEY_TEST_NAME, test.getTestName());
        values.put(KEY_TEST_OBJ_ID, test.getId());

        db.insert(TABLE_TESTS, null, values);

        List<Question> questions = test.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            values = new ContentValues();
            int questionObjId =  test.getId()
                    * ((Double)Math.pow(10.0, (Integer.toString(i)).length())).intValue()
                    + i;
            /*TODO put current question in table*/

            values.put(KEY_QUESTION, questions.get(i).getQuestion());
            values.put(KEY_QUESTION_OBJ_ID, questionObjId);
            values.put(KEY_QUESTION_PARENT_ID, test.getId());

            db.insert(TABLE_QUESTIONS, null, values);

            List<Answer> answers = questions.get(i).getAnswers();

            for (int j = 0; j < answers.size(); j++) {
                values = new ContentValues();
                /*TODO put current question in table*/

                // parent id for answer is testId + questionId
                values.put(KEY_ANSWER, answers.get(j).getName());
                values.put(KEY_ANSWER_IS_RIGHT, answers.get(j).isIsRight());
                values.put(KEY_ANSWER_PARENT_ID, questionObjId);

                db.insert(TABLE_ANSWERS, null, values);
            }
        }
    }
}
