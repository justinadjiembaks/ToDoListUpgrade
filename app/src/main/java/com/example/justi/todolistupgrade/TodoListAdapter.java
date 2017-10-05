package com.example.justi.todolistupgrade;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

class TodoListAdapter extends ArrayAdapter<TodoItem> {

    Context appContext;
    DBHelper helper;

    public TodoListAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, com.example.justin.Adjiembaks_todolist.R.layout.row, todoItems);
        appContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(com.example.justin.Adjiembaks_todolist.R.layout.row, parent, false);
        helper = new DBHelper(appContext);

        final TodoItem listItem = getItem(position);

        final TextView tvTitle = (TextView) row.findViewById(com.example.justin.Adjiembaks_todolist.R.id.tvRowTitle);
        final CheckBox checkBoxView = (CheckBox) row.findViewById(com.example.justin.Adjiembaks_todolist.R.id.checkBox);

        assert listItem != null;
        tvTitle.setText(listItem.getTitle());

        if(listItem.isCompleted() == 1) {
            tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            checkBoxView.setChecked(true);
        }

        checkBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(listItem.getTitle());
                if(checkBoxView.isChecked()) {
                    tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    listItem.setCompleted(1);
                    helper.update(listItem);
                } else {
                    tvTitle.setPaintFlags(tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    listItem.setCompleted(0);
                    helper.update(listItem);
                }
            }
        });
        return row;
    }
}
