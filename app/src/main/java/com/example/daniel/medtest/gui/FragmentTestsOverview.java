package com.example.daniel.medtest.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.logic.FileParser;
import com.example.daniel.medtest.logic.ListOfTests;
import com.example.daniel.medtest.logic.TestsAdapter;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public final class FragmentTestsOverview extends Fragment {

    private final int BROWSE_FILE_REQUEST_CODE = 1;
    private final int DIALOG_ID_TEST_MENU = 11;
    private final int DIALOG_ID_ADD_TEST = 12;

    @BindView(R.id.lv_tests)
    ListView mListViewTests;
    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    //callback description
    interface OnHeadlineSelectedListener{
        void callEditActivity(Test test);
        void callSessionActivity(Test test);
        void callOverviewFragment();
    };

    OnHeadlineSelectedListener mCallback;
    private ListOfTests mListOfTests = ListOfTests.getInstance();
    private Context mContext;

    public FragmentTestsOverview() {
        //empty constructor
    }

    //check implementation of callback
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentTestsOverview.OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests_overview, container, false);
        ButterKnife.bind(this, view);
        mContext = view.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // listener for floating action button "ADD"
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(DIALOG_ID_ADD_TEST, null).show();
            }
        });

        // filling list view
        updateList();

        //test session call by click on list item
        mListViewTests.setClickable(true);

        mListViewTests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Test test = (Test)adapterView.getItemAtPosition(i);
                test = mListOfTests.getTestFromDB(test);
                mCallback.callSessionActivity(test);
            }
        });

        mListViewTests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                createDialog(DIALOG_ID_TEST_MENU, (Test)adapterView.getItemAtPosition(i)).show();
                return false;
            }
        });
    }

    //setting dialog
    private Dialog createDialog(int dialogId, final Test targetTest) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        switch (dialogId) {
            case DIALOG_ID_TEST_MENU: {
                final String[] testMenu = {getString(R.string.edit), getString(R.string.delete)};

                builder
                        .setTitle(R.string.test_menu)
                        .setItems(testMenu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    //edit test
                                    Toast.makeText(mContext,
                                            R.string.func_in_dev,
                                            Toast.LENGTH_SHORT).show();
                                    /*TODO: send test to edit*/
                                    mCallback.callEditActivity(targetTest);
                                } else if (item == 1) {
                                    //delete test
                                    mListOfTests.deleteTest(targetTest);
                                    updateList();
                                }
                            }
                        })
                        .setCancelable(true);
                break;
            }
            case DIALOG_ID_ADD_TEST: {
                builder
                        .setTitle(R.string.adding_test)
                        .setMessage(R.string.way_to_add_test)
                        .setPositiveButton(R.string.add_by_typing, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                /*TODO: send test to edit*/
                                mCallback.callEditActivity(null);
                            }
                        })
                        .setNegativeButton(R.string.add_from_file, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                // selecting file
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("text/plain");

                                startActivityForResult(intent,
                                        BROWSE_FILE_REQUEST_CODE);
                            }
                        })
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                if (getView() != null)
                                    Snackbar.make(getView(), R.string.canceled, Snackbar.LENGTH_LONG).show();
                            }
                        });
                break;
            }
        }

        return builder.create();
    }

    //checking results of called activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        InputStream fileStream = null;
                
        switch(requestCode){
            case BROWSE_FILE_REQUEST_CODE:
                if(resultCode == RESULT_OK && data.getData() != null){
                    Uri uri = data.getData();

                    try {
                        fileStream = mContext.getContentResolver().openInputStream(uri);
                    } catch (IOException e) {
                        Log.d("PARSING", "Input Stream error" + e.getMessage());
                    }
                }
                break;
        }

        if (fileStream != null){
            FileParser parser = new FileParser();
            parser.setFileStream(fileStream);
            parser.setFragment(this);
            parser.execute();
        }
    }

    //update list of tests UI
    public void updateList() {
       TestsAdapter testsAdapter = new TestsAdapter(
               mContext,
               android.R.layout.simple_list_item_1,
               mListOfTests.getTests());

       mListViewTests.setAdapter(testsAdapter);
    }
}