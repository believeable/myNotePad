package com.example.administrator.mynotepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.mynotepad.Bean.NoteBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class NoteAdapter extends BaseAdapter {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据源
     */
    private List<NoteBean> mDatas;


    /**
     * 构造函数
     *
     * @param context
     * @param datas
     */
    public NoteAdapter(Context context, List<NoteBean> datas) {
        mContext = context;
        mDatas = datas;
    }
    public void setmDatas(List<NoteBean> datas){
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (null == view) {
            vh = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.item, null);
            vh.content = (TextView) view.findViewById(R.id.tv_content);
            vh.date = (TextView) view.findViewById(R.id.tv_date);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        NoteBean bean = (NoteBean) getItem(position);
        if (null != bean) {
            vh.content.setText(bean.getContent());
            vh.date.setText(bean.getDate());
        }
        return view;
    }


    /**
     * vh
     */
    class ViewHolder {
        /**
         * 内容
         */
        TextView content;
        /**
         * 日期
         */
        TextView date;

    }
}
