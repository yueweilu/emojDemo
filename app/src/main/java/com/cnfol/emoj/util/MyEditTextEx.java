package com.cnfol.emoj.util;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.Hashtable;
import java.util.Vector;


public class MyEditTextEx extends EditText implements Runnable {
    private Vector<GifDrawalbe> drawables = new Vector<GifDrawalbe>();
    private Hashtable<Integer, GifDrawalbe> cache = new Hashtable<Integer, GifDrawalbe>();
    ;

    private boolean mRunning = true;
    private Context context = null;
    private static final int SPEED = 100;

    public MyEditTextEx(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        new Thread(this).start();
    }

    public MyEditTextEx(Context context) {
        super(context);
        this.context = context;
        new Thread(this).start();
    }

    public void insertGif(String str) {
        if (drawables.size() > 0)
            drawables.clear();
        int index = getSelectionStart();
        Editable editable = getText();
        editable.insert(index, str);
        SpannableString spannableString = ExpressionUtil2.getExpressionString(context, getText().toString(), cache, drawables);
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
