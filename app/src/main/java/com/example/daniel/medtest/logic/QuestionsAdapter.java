package com.example.daniel.medtest.logic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daniel.medtest.datatypes.Question;

import java.util.List;

/**
 * Created by Daniel on 21.12.2017.
 */

public final class QuestionsAdapter extends ArrayAdapter<Question> {

    public QuestionsAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView tv = convertView.findViewById(android.R.id.text1);

        Question question = getItem(position);

        if (question != null){
            tv.setText(question.getQuestion());
        }

        return convertView;
    }
}
