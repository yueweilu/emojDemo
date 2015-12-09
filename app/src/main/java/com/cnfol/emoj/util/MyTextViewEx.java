package com.cnfol.emoj.util;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;


import java.util.Hashtable;
import java.util.Vector;

/**
 * @date 2015年11月5日 下午2:50:07
 * @autor LXM
 * @detail 自定义TextView，识别表情
 */
public class MyTextViewEx extends TextView implements Runnable {
    public static boolean mRunning = true;
    private Vector<GifDrawalbe> drawables;
    private Hashtable<Integer, GifDrawalbe> cache;
    private final int SPEED = 300;
    private Context context = null;

    public MyTextViewEx(Context context, AttributeSet attr) {
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

    public MyTextViewEx(Context context) {
        super(context);
        this.context = context;

        drawables = new Vector<GifDrawalbe>();
        cache = new Hashtable<Integer, GifDrawalbe>();

        new Thread(this).start();
    }

    /**
     * 设置gif图片
     * @param str
     */
    public void insertGif(String str) {
        if (drawables.size() > 0) {
            drawables.clear();
        }
        SpannableString spannableString = ExpressionUtil2.getExpressionString(context, str, cache, drawables);
        setText(spannableString);
    }

    /**
     * 设置png图片
     * @param str
     */
    public void insertPng(String str) {
        if (drawables.size() > 0) {
            drawables.clear();
        }
        SpannableString spannableString = ExpressionUtil2.getSpannableString(str,context);
        setText(spannableString);
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

}
