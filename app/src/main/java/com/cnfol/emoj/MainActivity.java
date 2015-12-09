package com.cnfol.emoj;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Toast;

import com.cnfol.emoj.bean.LiveColorBean;
import com.cnfol.emoj.util.InitChatBottomUtils;
import com.cnfol.emoj.util.MyLiveTextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements InitChatBottomUtils.GetEdContent {

    private InitChatBottomUtils initChatBottomUtils;
    private View root;
    private MyLiveTextView textView;
    private ArrayList<LiveColorBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (MyLiveTextView) findViewById(R.id.mytext);
        list = new ArrayList<>();
        list.add(new LiveColorBean("000001", "#0000ff", "000001K"));
        textView.insertGif("000001大涨", list, new MyLiveTextView.SetSpanListener() {
            @Override
            public void updateDrawState(TextPaint ds) {

            }

            @Override
            public void onClick(View view, LiveColorBean liveColorBean) {
                Toast.makeText(MainActivity.this, "点击个股，查看详情!", Toast.LENGTH_SHORT).show();
            }
        });
        root = findViewById(R.id.root);
        initChatBottomUtils = new InitChatBottomUtils(MainActivity.this, root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, this);
    }

    @Override
    public void getEdContent(String content) {
        textView.insertGif("000001大涨" + content, list, new MyLiveTextView.SetSpanListener() {
            @Override
            public void updateDrawState(TextPaint ds) {

            }

            @Override
            public void onClick(View view, LiveColorBean liveColorBean) {
                Toast.makeText(MainActivity.this, "点击个股，查看详情!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
