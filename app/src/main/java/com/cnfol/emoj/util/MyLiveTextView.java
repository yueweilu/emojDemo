package com.cnfol.emoj.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cnfol.emoj.bean.LiveColorBean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @date 2015年11月5日 下午2:50:29
 * @autor LXM
 * @detail 自定义TextView，识别表情，个股颜色，点击事件等
 */
public class MyLiveTextView extends TextView implements Runnable {
    public static boolean mRunning = true;
    private Vector<GifDrawalbe> drawables;
    private Hashtable<Integer, GifDrawalbe> cache;
    private final int SPEED = 300;
    private Context context = null;
    private String TAG = "MyLiveTextView";

    public MyLiveTextView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;

        drawables = new Vector<GifDrawalbe>();
        cache = new Hashtable<Integer, GifDrawalbe>();
        try {
            new Thread(this).start();
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
        }
    }

    public MyLiveTextView(Context context) {
        super(context);
        this.context = context;

        drawables = new Vector<GifDrawalbe>();
        cache = new Hashtable<Integer, GifDrawalbe>();

        new Thread(this).start();
    }

    /**
     * TODO 显示内容
     *
     * @param str      所有要显示的内容
     * @param list     个股集合，包含要显示的颜色
     * @param listener 点击事件接口监听
     */
    public void insertGif(String str, final ArrayList<LiveColorBean> list, final SetSpanListener listener) {
        if (drawables.size() > 0) {
            drawables.clear();
        }
        final SpannableString spannableString = ExpressionUtil2.getSpannableString(str,context);
        boolean flag = false;
        for (int j = 0; list != null && j < list.size(); j++) {
            flag = str.contains(list.get(j).getData());
            if (flag) {
                break;
            }
        }
        if (list != null && list.size() != 0 && flag) {
            String content = spannableString.toString();
            for (int i = 0; i < list.size(); i++) {
                final LiveColorBean liveColorBean = list.get(i);
                final String data = liveColorBean.getData();
                if (!TextUtils.isEmpty(data)) {
                    String temp = content;
                    int start = 0;
                    while (temp.contains(data)) {
                        spannableString.setSpan(new MyClickSpan(listener, liveColorBean), start + temp.indexOf(data),
                                start + temp.indexOf(data) + data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        setText(spannableString);
                        setMovementMethod(LinkMovementMethod.getInstance());
                        start = temp.indexOf(data) + data.length();
                        temp = temp.substring(start);
                    }
                }
            }
        } else {
            setText(spannableString);
        }
    }

    @Override
    public void run() {
        while (mRunning) {
            if (super.hasWindowFocus()) {
                for (int i = 0; i < drawables.size(); i++) {
                    drawables.get(i).run();
                }
                postInvalidate();
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(SPEED);
        } catch (Exception e) {
            PARAM.LOG(e.getMessage());
        }
    }

    public void destroy() {
        mRunning = false;
        drawables.clear();
        drawables = null;
    }

    /****
     * 给字体设置颜色并且设置点击事件
     ****/

    public interface SetSpanListener {
        void updateDrawState(TextPaint ds);

        void onClick(View view, LiveColorBean liveColorBean);
    }

}
