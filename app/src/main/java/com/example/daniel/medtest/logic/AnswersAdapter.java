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

    private boolean mIsCommitted = false;
    private Answer mUsersAnswer = null;
    private int mColorRight;
    private int mColorFalse;
    private int mColorBg;


    public AnswersAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects) {
        super(context, resource, objects);
        setColors();
    }

    public AnswersAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects, Answer a) {
        super(context, resource, objects);
        this.mIsCommitted = true;
        this.mUsersAnswer = a;
        setColors();
    }

    private void setColors(){
        mColorRight = getContext().getResources().getColor(android.R.color.holo_green_light);
        mColorFalse = getContext().getResources().getColor(android.R.color.holo_red_dark);
        mColorBg = getContext().getResources().getColor(android.R.color.transparent);
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
            if (mIsCommitted && mUsersAnswer != null) {
                if(currentAnswer.isRight()){
                    tv.setBackgroundColor(mColorRight);
                } else {

                    if(currentAnswer.equals(mUsersAnswer)){
                        tv.setBackgroundColor(mColorFalse);
                    } else {
                        tv.setBackgroundColor(mColorBg);
                    }
                }
            }
        }

        return convertView;
    }
}
