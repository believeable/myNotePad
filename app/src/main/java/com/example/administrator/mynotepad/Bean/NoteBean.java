package com.example.administrator.mynotepad.Bean;

/**
 * Created by Administrator on 2017/12/7.
 */

public class NoteBean {
    private String content;
    private String date;

    public NoteBean() {
    }

    public NoteBean(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
