package com.example.daniel.medtest.logic;

import android.app.Fragment;
import android.os.AsyncTask;

import com.example.daniel.medtest.datatypes.Test;
import com.example.daniel.medtest.gui.FragmentTestsOverview;

import java.io.File;


/**
 * Created by Daniel on 13.11.2017.
 */

public final class FileParser extends AsyncTask {

    private File mFile = null;
    private ListOfTests mTests = ListOfTests.getInstance();
    private FragmentTestsOverview fragment = null;

    @Override
    protected Object doInBackground(Object[] objects) {
        /*TODO Pars file here*/
        mTests.addTest(new Test("Test from parallel thread"));
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
