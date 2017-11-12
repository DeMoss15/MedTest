package com.example.daniel.medtest.gui;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.daniel.medtest.R;

public class MainActivity extends AppCompatActivity implements ReplaceabelFragment.OnHeadlineSelectedListener{

    FragmentIntputTest mFragmentInput = new FragmentIntputTest();
    FragmentSession mFragmentSession = new FragmentSession();
    FragmentTestsOverview mFragmentOverview = new FragmentTestsOverview();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callOverviewFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callInputFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, mFragmentInput)
                .commit();
    }

    @Override
    public void callSessionFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, mFragmentSession)
                .commit();
    }

    @Override
    public void callOverviewFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, mFragmentOverview)
                .commit();
    }
}
