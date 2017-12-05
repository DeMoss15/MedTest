package com.example.daniel.medtest.logic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daniel.medtest.datatypes.Answer;

import java.util.List;

/**
 * Created by Daniel on 17.11.2017.
 */

public final class AnswersAdapter extends ArrayAdapter<Answer> {

    private boolean mIsChecked = false;

    public AnswersAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects) {
        super(context, resource, objects);
    }

    public void setChecked(boolean b){
        this.mIsChecked = b;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView tv = convertView.findViewById(android.R.id.text1);

        Answer currentAnswer = getItem(position);

        if(currentAnswer != null) {
            tv.setText(currentAnswer.getName());
        }

        return convertView;
    }
}
