package com.cnfol.emoj.util;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cnfol.emoj.bean.LiveColorBean;


/**
 * @date 2015年11月6日 上午9:54:34
 * @autor LXM
 * @detail 自定义TextView中相应点击事件
 **/
public class MyClickSpan extends ClickableSpan {

    private MyLiveTextView.SetSpanListener listener;
    private LiveColorBean liveColorBean;

    public MyClickSpan(MyLiveTextView.SetSpanListener listener, LiveColorBean liveColorBean) {
        super();
        this.listener = listener;
        this.liveColorBean = liveColorBean;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        // TODO Auto-generated method stub
        super.updateDrawState(ds);
        listener.updateDrawState(ds);
        if (TextUtils.isEmpty(liveColorBean.getCode())){
            ds.setUnderlineText(false);
        }else {
            ds.setUnderlineText(true);
        }
        try {
            if (TextUtils.isEmpty(liveColorBean.getColor())) {
                ds.setColor(Color.parseColor("#E53333"));
            } else {
                ds.setColor(Color.parseColor(liveColorBean.getColor()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ds.setColor(Color.parseColor("#E53333"));
        }
    }

    @Override
    public void onClick(View widget) {
        listener.onClick(widget, liveColorBean);
    }
}
