package com.cnfol.emoj.util;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;

public class PARAM {


    /**
     * 开关标示
     */
    public static boolean LogSwitch = true;

    public static void LOG(String msg) {
        if (LogSwitch) {
            Log.i("QuanZi", msg);
        }
    }

    public static void LOG(String msg, String tag) {
        if (LogSwitch) {
            Log.i(tag, msg);
        }
    }

    public static void LOG_E(String msg) {
        if (LogSwitch) {
            Log.e("QuanZi", msg);
        }
    }

    public static void LOG_E(String TAG, String msg) {
        if (LogSwitch) {
            Log.e(TAG, msg);
        }
    }

    public static void LOG_MAP(Map<String, String> paramsMap) {
        if (LogSwitch) {
            if (paramsMap != null) {
                Iterator iter = paramsMap.entrySet().iterator();
                StringBuffer sBuffer = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    sBuffer.append(key + "=" + val + " ,");
                }
                if (sBuffer.length() > 1) {
                    PARAM.LOG(sBuffer.substring(0, sBuffer.length() - 1));
                } else {
                    PARAM.LOG(sBuffer.toString());
                }
            } else {
                PARAM.LOG("map为空");
            }
        }
    }


    /**
     * dp转换成像素
     *
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 像素转成dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


}
