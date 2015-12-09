package com.cnfol.emoj.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.cnfol.emoj.R;
import com.cnfol.emoj.bean.EmotionBean;
import com.cnfol.emoj.util.Expressions;

import java.util.ArrayList;
import java.util.List;


public class EmotionGridViewAdapter extends BaseAdapter {
    private List<EmotionBean> list;
    private Context context;
    private String tag;

    public EmotionGridViewAdapter(Context context, int page) {
        this.context = context;
        this.list = new ArrayList<EmotionBean>();
        setData1(page);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    /**
     * 设置第一页表情数据
     *
     * @param page
     */
    private  void setData1(int page) {
        switch (page) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                int len = 20;//每页显示20个表情+一个删除键
                for (int i = 0 + (len * page); i < len + (len * page); i++) {//(i=0;i<20;i++)  (i=20;i<40;i++)
                    EmotionBean bean = new EmotionBean();
                    bean.setId(Expressions.emojID[i]);//0 1 2 3 ...19 20 21
                    bean.setName(Expressions.emojName[i]);
                    list.add(bean);
                }
                EmotionBean bean = new EmotionBean();
                bean.setId(R.mipmap.emoji_del);
                list.add(bean);
                break;
            case 5:
                //最后一页，显示5个表情和一个删除键
                for (int i = 100; i < 105; i++) {
                    EmotionBean bean1 = new EmotionBean();
                    bean1.setId(Expressions.emojID[i]);
                    bean1.setName(Expressions.emojName[i]);
                    list.add(bean1);
                }
                EmotionBean bean1 = new EmotionBean();
                bean1.setId(R.mipmap.emoji_del);
                list.add(bean1);
                break;
        }


    }

    public void setData(List<EmotionBean> list) {
        this.list = list;

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final EmotionBean bean = list.get(position);
        int itemWidth = getWindowPX();
        int padding = itemWidth / 6;
        ImageView iv = new ImageView(context);
        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        iv.setLayoutParams(params);
        iv.setScaleType(ScaleType.FIT_CENTER);
        iv.setPadding(padding, 0, padding, 0);
        iv.setImageResource(bean.getId());
        return iv;

    }

    private int getWindowPX() {
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            return width / 8;
        } else {
            return 200;
        }
    }

}
