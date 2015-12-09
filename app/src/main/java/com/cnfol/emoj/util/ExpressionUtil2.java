package com.cnfol.emoj.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.cnfol.emoj.R;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtil2 {

    private static String TAG = "ExpressionUtil2";
    public static String zz = "\\[[^\\]]+\\]";
    private static Context mContext;

    /**
     * 获得包含了图片的可以直接给edittext,textview赋值的对象,主要是png图片
     *
     * @param str
     * @param context
     * @return
     */
    public static SpannableString getSpannableString(String str, Context context) {
        SpannableString spannableString = new SpannableString(str);
        String string = "\\[(.+?)\\]";
        Pattern patten = Pattern.compile(string, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            int id = Expressions.getIdAsName(key);
            if (id != 0) {
                Drawable drawable = context.getResources().getDrawable(id);
                drawable.setBounds(0, 0, PARAM.Dp2Px(context, 30),
                        PARAM.Dp2Px(context, 30));
                ImageSpan imgSpan = new ImageSpan(drawable);
                spannableString.setSpan(imgSpan, matcher.start(),
                        matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableString;
    }

    /**
     * 主要是gif图片
     *
     * @param context
     * @param str
     * @param cache
     * @param drawables
     * @return
     */
    public static SpannableString getExpressionString(Context context,
                                                      String str, Hashtable<Integer, GifDrawalbe> cache,
                                                      Vector<GifDrawalbe> drawables) {
        mContext = context;
        SpannableString spannableString = new SpannableString(str);
        Pattern sinaPatten = Pattern.compile(zz, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
        try {
            dealExpression(context, spannableString, sinaPatten, 0, cache,
                    drawables);
        } catch (Exception e) {
            PARAM.LOG(e.getMessage());
        }
        return spannableString;
    }

    /**
     * 处理图片显示，主要是gif图片
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @param cache
     * @param drawables
     * @throws Exception
     */
    public static void dealExpression(Context context,
                                      SpannableString spannableString, Pattern patten, int start,
                                      Hashtable<Integer, GifDrawalbe> cache, Vector<GifDrawalbe> drawables)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            if (matcher.start() < start) {
                continue;
            }
            String key2 = Expressions.changeKeys(key);
            Field field = R.drawable.class.getDeclaredField(key2);
            int id = Integer.parseInt(field.get(null).toString());
            if (id != 0) {
                if (!key2.contains("emoji2_")) {
                    int mstart = matcher.start();
                    int end = mstart + key.length();
                    for (int i = 0; i < Expressions.emojName.length; i++) {
                        if (Expressions.emojName[i].toString()
                                .equals(key)) {
                            Bitmap bitmap = BitmapFactory.decodeResource(
                                    context.getResources(),
                                    Expressions.emojID[i]);
                            bitmap = big2(bitmap);
                            ImageSpan imageSpan = new ImageSpan(bitmap);
                            spannableString.setSpan(imageSpan, matcher.start(),
                                    end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                            break;
                        }
                    }
                } else {

                    GifDrawalbe mSmile = null;
                    if (cache.containsKey(id)) {
                        mSmile = cache.get(id);
                    } else {
                        mSmile = new GifDrawalbe(context, id);

                        cache.put(id, mSmile);
                    }

                    ImageSpan span = new ImageSpan(mSmile,
                            ImageSpan.ALIGN_BASELINE);
                    int mstart = matcher.start();
                    int end = mstart + key.length();
                    spannableString.setSpan(span, mstart, end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if (!drawables.contains(mSmile))
                        drawables.add(mSmile);
                }
            }
        }
    }


    /**
     * qq表情放大更大倍数
     *
     * @param bitmap
     * @return
     */
    private static Bitmap big2(Bitmap bitmap) {
        try {
            float level = 1f;
            //计算公式:1dp*像素密度/160 = 实际像素数
            float scaleWidth = 1;
            int bmpWidth = bitmap.getWidth();
            int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
            if (densityDpi == 240) {
                level = 3f;
            } else if (densityDpi == 320) {
                level = 4f;
            } else if (densityDpi == 480) {
                level = 5f;
            } else {
                level = 6f;
            }
            int fontPX = (int) (30 * densityDpi / 160 / 2 * level);
            /* 放大变量 */
            double scale = 1.1;
            if ((double) bmpWidth < (double) fontPX) {
                scale = fontPX / bmpWidth;
                scale += 0.5;
            }
            /* 放大以后的宽高，一定要强制转换为float型的 */
            scaleWidth = (float) (scaleWidth * scale);
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth); // 长和宽放大缩小的比例
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizeBmp;
        } catch (OutOfMemoryError e) {
            PARAM.LOG_E("OutOfMemoryError===" + e.getMessage());
            System.gc();
            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception
            PARAM.LOG_E("Exception===" + e.getMessage());
            return bitmap;
        }
    }
}
