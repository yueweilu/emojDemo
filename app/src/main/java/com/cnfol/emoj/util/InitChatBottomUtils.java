package com.cnfol.emoj.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cnfol.emoj.R;
import com.cnfol.emoj.adapter.EmotionGridViewAdapter;

import java.util.ArrayList;

/**
 * @author : LXM
 * @date :2015-6-10 下午1:45:45
 * @detail :聊天底部
 */
public class InitChatBottomUtils {
    private Context context;
    private OnClickListener listener;
    public boolean isOpen = false;
    private GetEdContent getEdContent;

    private View root;
    private Button sendBtn;
    private EditText sendEd;
    private Button imageBtn;
    private Button keyBoard;
    private LinearLayout emoView;
    private Button emotionImage;
    private ViewPager emotionVp;
    private ArrayList<GridView> gridViews;

    private InputMethodManager manager;

    public InitChatBottomUtils(Context context, View root,
                               OnClickListener btnSendClickListener, GetEdContent getEdContent) {
        super();
        this.context = context;
        this.root = root;
        this.listener = btnSendClickListener;
        this.getEdContent = getEdContent;
        manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mInitEmotion();
    }

    private void mInitEmotion() {
        sendBtn = (Button) root.findViewById(R.id.live_sendBtn);
        sendBtn.setOnClickListener(listener);

        sendEd = (EditText) root.findViewById(R.id.live_edit);
        sendEd.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (isOpen) {
                    openKeyBoard();
                    emoView.setVisibility(View.GONE);
                    emotionImage.setVisibility(View.VISIBLE);
                    keyBoard.setVisibility(View.GONE);
                    isOpen = false;
                }
                return false;
            }
        });
        // 输入 文字的时候
        sendEd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String msg = sendEd.getText().toString().trim();
                getEdContent.getEdContent(msg);
                if (TextUtils.isEmpty(msg)) {
                    sendEd.setFocusable(true);
                    sendBtn.setBackgroundResource(R.mipmap.send01_circle);
                } else {
                    sendBtn.setBackgroundResource(R.mipmap.send02_circle);
                    sendBtn.setTextSize(14);
                }
            }
        });

        // 选择图片
        imageBtn = (Button) root.findViewById(R.id.tribal_message_img_btn);
        imageBtn.setOnClickListener(listener);
        keyBoard = (Button) root.findViewById(R.id.tribal_message_bnt_less);
        emoView = (LinearLayout) root.findViewById(R.id.live_express_view);
        emoView.setVisibility(View.GONE);
        emotionImage = (Button) root.findViewById(R.id.live_expre);
        emotionImage.setOnClickListener(new OnClickListener() {// 打开表情

            @Override
            public void onClick(View v) {
                // openKeyBoard();
                hidenKeyBoard();
                isOpen = true;
                emoView.setVisibility(View.VISIBLE);
                keyBoard.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }

        });
        keyBoard.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                openKeyBoard();
                isOpen = false;
                emoView.setVisibility(View.GONE);
                emotionImage.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);

            }

        });

        emotionVp = (ViewPager) root.findViewById(R.id.live_viewpager);

        initGradViews();// 初始化表情布局；
        initEmotionVp();

    }

    /**
     * 初始化GradView
     */
    private void initGradViews() {
        gridViews = new ArrayList<GridView>();
        LayoutInflater inflater = LayoutInflater.from(context);
        gridViews.clear();
        for (int i = 0; i < 6; i++) {
            final int j = i;
            GridView gridView1 = (GridView) inflater.inflate(
                    R.layout.fragment_news_detail_emotin_gridview, null, false);
            gridView1.setAdapter(new EmotionGridViewAdapter(context, i));
            gridViews.add(gridView1);
            gridView1.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    if (arg2 == 20 || arg2 == 40 || arg2 == 60 || arg2 == 80 || arg2 == 100 || (j == 5 && arg2 == 5)) {
                        int selectionStart = sendEd.getSelectionStart();
                        String text = sendEd.getText().toString();

                        if (!TextUtils.isEmpty(text)) {
                            String body = sendEd.getText().toString();
                            String tempStr = body.substring(0, selectionStart);
                            int i = tempStr.lastIndexOf("]");// 获取最后一个表情的位置
                            if (i == tempStr.length() - 1) {// 说明光标刚好停在这个表情之后
                                int j = tempStr.lastIndexOf("[");// 将这两个之间的字符删掉
                                sendEd.getEditableText().delete(j, selectionStart);
                            } else {
                                sendEd.getEditableText().delete(
                                        tempStr.length() - 1, selectionStart);
                            }
                        }

                    } else {
                        String emotionString = Expressions.emojName[arg2+j*20];
                        String text = sendEd.getText() + emotionString;
                        SpannableString spannableString = ExpressionUtil2
                                .getSpannableString(text, context);
                        sendEd.setText(spannableString);
                        Editable b = sendEd.getText();
                        sendEd.setSelection(b.length());
                    }
                }
            });
        }
    }

    private class EmoitonPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return gridViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(gridViews.get(position));
            return gridViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(gridViews.get(position));
        }

    }

    private void initEmotionVp() {
        emotionVp.setAdapter(new EmoitonPagerAdapter());
        final RadioGroup rg = (RadioGroup) root.findViewById(R.id.radioGroup1);
        emotionVp.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                RadioButton rb = (RadioButton) rg.getChildAt(arg0);
                rb.setChecked(true);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    public void openKeyBoard() {

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hidenKeyBoard() {
        if (((Activity) context).getCurrentFocus() != null) {
            ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(((Activity) context)
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    /**
     * 普通用户向圈主提问时，隐藏掉图片按钮
     *
     * @param flag
     */
    public void setImgGone(boolean flag) {
        if (flag) {
            imageBtn.setVisibility(View.GONE);
        } else {
            imageBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发送内容异常时，设置图片默认背景
     *
     * @param id
     */
    public void setImgBtnBg(int id) {
        imageBtn.setBackgroundResource(id);
    }

    /**
     * 消息发送时，隐藏表情框
     */
    public void hideESLayout() {
        if (isOpen) {
            isOpen = false;
            emoView.setVisibility(View.GONE);
            emotionImage.setVisibility(View.VISIBLE);
        }
        keyBoard.setVisibility(View.GONE);
        emotionImage.setBackgroundResource(R.mipmap.footface01_circle);
    }

    /**
     * 消息发送成功，清空输入框
     */
    public void clearED() {
        sendEd.setText("");
    }



    /**
     * @author LXM
     * @date 2015-6-10
     * @detail :获取EditText中的内容
     */
    public interface GetEdContent {
        public void getEdContent(String content);
    }
}
