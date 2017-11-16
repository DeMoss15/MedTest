package com.example.daniel.medtest.logic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daniel.medtest.R;
import com.example.daniel.medtest.datatypes.Test;

import java.util.List;

/**
 * Created by Daniel on 16.11.2017.
 */

public final class TestsAdapter extends ArrayAdapter<Test> {

    public TestsAdapter(@NonNull Context context, int resource, @NonNull List<Test> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView tv = convertView.findViewById(android.R.id.text1);

        Test currentTest = getItem(position);

        if(currentTest != null) {
            tv.setText(currentTest.getTestName());
        }

        return convertView;
    }
}
