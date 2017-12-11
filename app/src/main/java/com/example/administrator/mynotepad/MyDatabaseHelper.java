package com.example.administrator.mynotepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/12/5.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME_NOTES = "NOTEPAD";
    public static final String CREATE_NOTEPAD = "CREATE TABLE NOTEPAD("
            +"id integer primary key autoincrement,"
            +"content text not null,"
            +"date text not null"
            +")";
    public MyDatabaseHelper(Context context) {
        super(context, "NOTEPAD", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_NOTEPAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
