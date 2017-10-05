package com.example.justi.todolistupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewItemActivity extends AppCompatActivity {

    EditText EditTodo;
    Button ButtonAdd;
    TodoItem todoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.justin.Adjiembaks_todolist.R.layout.activity_new_item);
        Toolbar toolbar = (Toolbar) findViewById(com.example.justin.Adjiembaks_todolist.R.id.toolbar);
        setSupportActionBar(toolbar);

        EditTodo = (EditText) findViewById(com.example.justin.Adjiembaks_todolist.R.id.etAddItem);
        ButtonAdd = (Button) findViewById(com.example.justin.Adjiembaks_todolist.R.id.btAddItem);
    }

    public void addItem(View view) {
        String todoItemString = EditTodo.getText().toString();
        if(todoItemString.equals("")) {
            Toast.makeText(this.getApplicationContext(), "Please insert at least one character",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            todoItem = new TodoItem(todoItemString);
            DBHelper helper = new DBHelper(this);
            helper.addRow(todoItem);
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this.getApplicationContext(), "Added",
                    Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}
