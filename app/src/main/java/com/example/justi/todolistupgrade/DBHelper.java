package com.example.justi.todolistupgrade;

/**
 * Created by justi on 5-10-2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "todoList";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_COMPLETED = "completed";


    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT NOT NULL,"
                + COLUMN_COMPLETED + " BOOL NOT NULL" + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // Add new row to database
    public void addRow(TodoItem todoItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todoItem.getTitle());
        values.put(COLUMN_COMPLETED, todoItem.isCompleted());

        db.insert(TABLE, null, values);
        db.close();
    }

    public ArrayList<TodoItem> read() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<TodoItem> todoItems = new ArrayList<>();

        String query = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TITLE + ", "
                + COLUMN_COMPLETED
                + " FROM " + TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                int completed = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPLETED));
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

                TodoItem todoItem = new TodoItem(title, completed, id);
                todoItems.add(todoItem);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoItems;
    }

    public int update(TodoItem todoItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todoItem.getTitle());
        values.put(COLUMN_COMPLETED, todoItem.isCompleted());

        return db.update(TABLE, values, COLUMN_ID +
                " = ? ", new String[] { String.valueOf(todoItem.getId()) });
    }

    // Delete row from database
    public void deleteRow(TodoItem todoItem) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE, " " + COLUMN_ID +
                " = ? ", new String[] { String.valueOf(todoItem.getId()) });

        db.close();
    }

}

