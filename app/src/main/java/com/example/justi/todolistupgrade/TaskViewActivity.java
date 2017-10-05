package com.example.justi.todolistupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskViewActivity extends AppCompatActivity {

    EditText EdittextTaskTitle;
    ArrayList<TodoItem> todoList;
    CheckBox checkBox;
    DBHelper helper;
    TodoItem todoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.justin.Adjiembaks_todolist.R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(com.example.justin.Adjiembaks_todolist.R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        int index = intent.getIntExtra("todoIndex", 0);
        helper = new DBHelper(this);
        todoList = helper.read();
        todoItem = todoList.get(index);

        EdittextTaskTitle = (EditText) findViewById(com.example.justin.Adjiembaks_todolist.R.id.etTaskTitle);
        EdittextTaskTitle.setText(todoItem.getTitle());
        EdittextTaskTitle.setSelection(todoItem.getTitle().length());
        checkBox = (CheckBox) findViewById(com.example.justin.Adjiembaks_todolist.R.id.cbCompleted);
        if(todoItem.isCompleted() == 1) {
            checkBox.setChecked(true);
        }

    }

    public void clickedDone(View view) {
        if(checkBox.isChecked()) {
            todoItem.setCompleted(1);
        } else {
            todoItem.setCompleted(0);
        }
        todoItem.setTitle(EdittextTaskTitle.getText().toString());
        helper.update(todoItem);

        Intent intent = new Intent(this, MainActivity.class);
        Toast.makeText(this.getApplicationContext(), "Edited",
                Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public void clickedDelete(View view) {
        helper.deleteRow(todoItem);
        Intent intent = new Intent(this, MainActivity.class);

        Toast.makeText(this.getApplicationContext(), "Deleted",
                Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}

