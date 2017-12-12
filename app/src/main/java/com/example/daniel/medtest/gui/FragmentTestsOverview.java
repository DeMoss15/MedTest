package com.example.daniel.medtest.gui;

import android.app.AlertDialog;
import android.app.Dialog;
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

public final class FragmentTestsOverview extends ReplaceabelFragment {

    private final int BROWSE_FILE_REQUEST_CODE = 1;
    private final int DIALOG_ID_TEST_MENU = 11;
    private final int DIALOG_ID_ADD_TEST = 12;

    @BindView(R.id.lv_tests)
    ListView mListViewTests;
    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    private ListOfTests mListOfTests = ListOfTests.getInstance();
    private FileParser mParser = new FileParser();
    private Context mContext;

    public FragmentTestsOverview() {
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

    private Dialog createDialog(int dialogId, final Test targetTest) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        switch (dialogId) {
            case DIALOG_ID_TEST_MENU: {
                final String[] testMenu = {"Edit", "Delete"};

                builder
                        .setTitle("Test menu")
                        .setItems(testMenu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    //edit test
                                    Toast.makeText(mContext,
                                            "This feature still in dev!",
                                            Toast.LENGTH_SHORT).show();
                                } else if (item == 1) {
                                    //delete test
                                    mListOfTests.deleteTest(targetTest);
                                    updateList();
                                }                           }
                        })
                        .setCancelable(true);
                break;
            }
            case DIALOG_ID_ADD_TEST: {
                builder
                        .setTitle("Adding test")
                        .setMessage("Choose way to add test")
                        .setPositiveButton("Add by typing", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                if (getView() != null)
                                    Snackbar.make(getView(), "Here will be another fragment", Snackbar.LENGTH_LONG).show();
                                mCallback.callInputFragment();
                            }
                        })
                        .setNegativeButton("Add from file", new DialogInterface.OnClickListener() {
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
                                    Snackbar.make(getView(), "Canceled", Snackbar.LENGTH_LONG).show();
                            }
                        });
                break;
            }
        }

        return builder.create();
    }

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
            mParser = new FileParser();
            mParser.setFileStream(fileStream);
            mParser.setFragment(this);
            mParser.execute();
        }
    }

    public void updateList() {
       TestsAdapter testsAdapter = new TestsAdapter(
               mContext,
               android.R.layout.simple_list_item_1,
               mListOfTests.getTests());

       mListViewTests.setAdapter(testsAdapter);
    }
}
