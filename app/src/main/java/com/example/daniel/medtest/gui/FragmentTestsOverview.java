package com.example.daniel.medtest.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.logic.FileParser;
import com.example.daniel.medtest.logic.ListOfTests;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public final class FragmentTestsOverview extends ReplaceabelFragment {

    private final int BROWSE_FILE_REQUEST_CODE = 1;
    @BindView(R.id.lv_tests)
    ListView mListViewTests;
    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    AlertDialog.Builder mAlertDialog;
    ListOfTests mTests = ListOfTests.getInstance();
    FileParser mParser = new FileParser();

    public FragmentTestsOverview() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests_overview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Context context = getContext();

        // alert dialog settings
        mAlertDialog = new AlertDialog.Builder(context);
        mAlertDialog.setTitle("Adding test");
        mAlertDialog.setMessage("Choose way to add test");
        mAlertDialog.setPositiveButton("Add by typing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if (getView() != null)
                    Snackbar.make(getView(), "Here will be another fragment", Snackbar.LENGTH_LONG).show();
                mCallback.callInputFragment();
            }
        });
        mAlertDialog.setNegativeButton("Add from file", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                // selecting file
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");

                startActivityForResult(intent,
                        BROWSE_FILE_REQUEST_CODE);
            }
        });
        mAlertDialog.setCancelable(true);
        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (getView() != null)
                    Snackbar.make(getView(), "Canceled", Snackbar.LENGTH_LONG).show();
            }
        });

        // listener for floating action button "ADD"
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialog.show();
            }
        });

        // filling list view
        updateList();

        /*TODO: define here test session call by click on list item*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        File file = null;
                
        switch(requestCode){
            case BROWSE_FILE_REQUEST_CODE:
                if(resultCode == RESULT_OK && data.getData() != null){
                    file = new File(data.getData().toString());
                }
                break;
        }

        if (file != null){
            mParser = new FileParser();
            mParser.setFile(file);
            mParser.setFragment(this);
            mParser.execute();
        }
    }

    public void updateList() {
        String[] testsNames = mTests.getTestsNames();

        if(testsNames.length == 1 && testsNames[0].isEmpty())
            testsNames[0] = "Database is empty\nYou may add test via button below";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                testsNames);

        mListViewTests.setAdapter(adapter);
    }
}
