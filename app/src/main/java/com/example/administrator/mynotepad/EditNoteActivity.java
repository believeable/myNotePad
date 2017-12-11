package com.example.administrator.mynotepad;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2017/12/7.
 */

public class EditNoteActivity extends Activity {
    private Button btn_ok;//保存按钮
    private Button btn_back;//返回按钮
    private SQLiteDatabase database;
    private MyDatabaseHelper db;
    private EditNoteActivity editContext;
    private TextView tv_date;
    private EditText et_content;
    public static int id;
    public static int ENTER_STATE = 0;//设置点击进入的状态,分为新建(0)和编辑(1)
    public static String last_content;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit);
        tv_date= (TextView) findViewById(R.id.tv_date);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        tv_date.setText(format);
        et_content= (EditText) findViewById(R.id.et_content);
        //设置软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        db = new MyDatabaseHelper(this);
        database = db.getReadableDatabase();

        Bundle bundle = this.getIntent().getExtras();
        last_content = bundle.getString("info");
        //设置内容
        et_content.setText(last_content);
        //将光标设置到文本最后
        et_content.setSelection(last_content.length());
        //btn_ok的设置
        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击保存
            public void onClick(View v) {
                String content = et_content.getText().toString();


                //得到备忘录保存时的时间
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateNum = sdf.format(date);
                String sql;

                //当点击新建时
                if (ENTER_STATE == 0) {
                    if (!content.equals("")) {
                        sql = "INSERT INTO NOTEPAD (content,date)"
                                + " values("+ "'" + content
                                + "'" + "," + "'" + dateNum + "')";
                        database.execSQL(sql);
                    }
                }
                // 当点击编辑时
                else {
                    String updatesql;
                    //如果content为空,直接销毁
                    if(content==null||content.equals("")){
                        updatesql="delete from NOTEPAD where id=" + id;
                    }
                    else{
                        updatesql = "update NOTEPAD set content='"
                                + content + "' where id=" + id;
                    }


                    database.execSQL(updatesql);
                }
                Intent data = new Intent();
                //设置返回标记
                setResult(2, data);
                finish();
            }
        });
        //btn_back的设置
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击销毁`
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
