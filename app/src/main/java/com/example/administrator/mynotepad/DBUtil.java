package com.example.administrator.mynotepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.mynotepad.Bean.NoteBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */


public class DBUtil {

    //一个用于得到全表数据的方法
    public List<NoteBean> getData(SQLiteDatabase database,List<NoteBean> list) {
        Cursor cursor = database.rawQuery("SELECT * FROM NOTEPAD", null);
        list.clear();
        while (cursor.moveToNext()) {
            NoteBean noteBean = new NoteBean();
            String name = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            noteBean.setContent(name);
            noteBean.setDate(date);
            list.add(noteBean);
        }
        cursor.close();
        return list;
    }
    //通过input得到数据的方法
    public List<NoteBean> getDataByInput(SQLiteDatabase database,List<NoteBean> list,String input){
        Cursor cursor = database.rawQuery("SELECT * FROM NOTEPAD WHERE CONTENT LIKE ?", new String[]{"%"+input+"%"});
        list.clear();
        while (cursor.moveToNext()) {
            NoteBean noteBean = new NoteBean();
            String name = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            noteBean.setContent(name);
            noteBean.setDate(date);
            list.add(noteBean);
        }
        cursor.close();
        return list;
    }

}
