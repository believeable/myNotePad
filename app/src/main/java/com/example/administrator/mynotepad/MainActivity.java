package com.example.administrator.mynotepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.mynotepad.Bean.NoteBean;

import java.util.ArrayList;
import java.util.List;

;

public class MainActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {
    private SQLiteDatabase database;
    private MyDatabaseHelper db;
    private Context mContext;
    private List<NoteBean> list;//数据源
    private List<NoteBean> filterData;//过滤数据
    private ListView listview;//数据显示listview
    //    private SimpleAdapter simp_adapter;//简易适配器
    private TextView tv_content;
    private Button addNote;
    //通用搜索框
    private CommonlySearchView<NoteBean> commonlySearchView;
    //通用适配器
    private NoteAdapter noteAdapter;
    private String input;
    private DBUtil dbutil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mContext = this;//创建容器
        list = new ArrayList<NoteBean>();

        filterData = new ArrayList<NoteBean>();
        dbutil = new DBUtil();
        tv_content = (TextView) findViewById(R.id.tv_content);
        listview = (ListView) findViewById(R.id.listview);
        addNote = (Button) findViewById(R.id.addNote);
        commonlySearchView = (CommonlySearchView<NoteBean>) findViewById(R.id.csv_show);
        //给添加记事本添加一个点击事件
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mcontext,"新建记事本",Toast.LENGTH_LONG).show();
                //使用隐式intent,传入对应xml的字符串
                //Intent intent = new Intent("com.example.administrator.mynotepad.EDITNOTE");
                EditNoteActivity.ENTER_STATE = 0;
                //使用显示传输
                Intent intent = new Intent(mContext, EditNoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("info", "");
                intent.putExtras(bundle);
                //传入请求标记1
                startActivityForResult(intent, 1);
            }
        });

        db = new MyDatabaseHelper(mContext);//创建助手
        database = db.getReadableDatabase();//创建可读sqlite数据库
        InitModule();


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == 2) {
////            list = getData();
////            if (null != input) {
////                for (int i = 0; i < list.size(); i++) {
////                    if (list.get(i).getContent().contains(input)) {
////                        filterData.add(list.get(i));
////                    }
////                }
////            }
////            //                commonlySearchView.choise(list,input);
////            noteAdapter = new NoteAdapter(mContext, list);
////            //设置数据源
////            commonlySearchView.setDatas(list);
////            //设置适配器
////            commonlySearchView.setAdapter(noteAdapter);
////            noteAdapter.notifyDataSetChanged();
////            listview.setAdapter(noteAdapter);
//            refreshAllList();
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshAllList();
    }

    public void refreshAllList() {
        if (null == input || input.equals("")) {
            int size = list.size();
            if (size > 0) {
                list.clear();
                noteAdapter.notifyDataSetChanged();
                listview.setAdapter(noteAdapter);
            }
            list=getData();
            noteAdapter.setmDatas(list);
            listview.setAdapter(noteAdapter);
        }
        else{
            list.clear();
            noteAdapter.notifyDataSetChanged();
            list=dbutil.getDataByInput(database, list, input);
            listview.setAdapter(noteAdapter);
            noteAdapter.setmDatas(list);
                            for (NoteBean i:list
                                 ) {
                                Log.d("xxxxxxxx",i.getContent());
                            }
            listview.setAdapter(noteAdapter);

        }
    }

    //初始化过滤控件的方法
    public void InitModule() {

        noteAdapter = new NoteAdapter(mContext, list);
        listview.setAdapter(noteAdapter);
        //设置数据源
        commonlySearchView.setDatas(list);
        //设置适配器
        commonlySearchView.setAdapter(noteAdapter);
        //设置筛选数据
        CommonlySearchView.SearchDatas<NoteBean> searchDatas = new CommonlySearchView.SearchDatas<NoteBean>() {
            @Override
            public List<NoteBean> filterDatas(List<NoteBean> datas, List<NoteBean> filterdatas, String inputstr) {
                filterdatas.clear();
                filterdatas.addAll(dbutil.getDataByInput(database,datas,inputstr));
                input = inputstr;
//                for (NoteBean i:filterdatas
//                     ) {
//                    Log.d("content",i.getContent());
//
//                }
                Log.d("input", input);
                return filterdatas;
            }
        };
        //回调函数,将筛选数据的方式传入
        commonlySearchView.setSearchDataListener(searchDatas);
//        commonlySearchView.refresh(input);
        listview.setOnItemClickListener(this);//设置列表点击
        listview.setOnItemLongClickListener(this);//设置长点击删除
    }


    //一个用于得到全表数据的方法
    public List<NoteBean> getData() {
        Cursor cursor = database.rawQuery("select * from NOTEPAD", null);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EditNoteActivity.ENTER_STATE = 1;
        //点击得到一个NoteBean
        NoteBean content = (NoteBean) listview.getItemAtPosition(position);
        String content1 = content.getContent();
        Cursor c = database.query("NOTEPAD", null,
                "content=" + "'" + content1 + "'", null, null, null, null);
        //根据内容得到id
        while (c.moveToNext()) {
            String No = c.getString(c.getColumnIndex("id"));
            Intent myIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("info", content1);
            EditNoteActivity.id = Integer.parseInt(No);
            myIntent.putExtras(bundle);
            myIntent.setClass(MainActivity.this, EditNoteActivity.class);
            startActivityForResult(myIntent, 1);
        }
        c.close();
    }


    //一个长点击删除的方法
    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        final int n = arg2;
        Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除该备忘录");
        builder.setMessage("确认删除吗?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteBean content = (NoteBean) listview.getItemAtPosition(n);
                String content1 = content.getContent();
                Cursor c = database.query("NOTEPAD", null, "content=" + "'"
                        + content1 + "'", null, null, null, null);
                while (c.moveToNext()) {
                    String id = c.getString(c.getColumnIndex("id"));
                    String sql_del = "delete from NOTEPAD where id="
                            + id;
                    database.execSQL(sql_del);
                    refreshAllList();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
        return true;
    }


}
