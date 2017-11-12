package com.example.daniel.medtest.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.daniel.medtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTestsOverview extends ReplaceabelFragment {

    @BindView(R.id.lv_tests)
    ListView ListViewTests;

    AlertDialog.Builder mAlertDialog;

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
                /*TODO: call here file input menu*/
                if (getView() != null)
                    Snackbar.make(getView(), "Here will be explorer", Snackbar.LENGTH_LONG).show();
            }
        });
        mAlertDialog.setCancelable(true);
        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (getView() != null)
                    Snackbar.make(getView(), "Canceled", Snackbar.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialog.show();
            }
        });

        /*TODO: define here test session call by click on list item*/
    }
}
