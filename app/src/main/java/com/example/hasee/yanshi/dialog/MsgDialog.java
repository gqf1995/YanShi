package com.example.hasee.yanshi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.yanshi.R;

/**
 * Created by Administrator on 2017/8/15.
 */

public class MsgDialog extends Dialog implements View.OnClickListener {
    private TextView okTxt;
    private TextView titleTxt;

    private Context mContext;
    private String content;
    OnCloseListener listener;

    public MsgDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public MsgDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public MsgDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected MsgDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        okTxt = (TextView) findViewById(R.id.ok_txt);
        titleTxt = (TextView) findViewById(R.id.title_txt);
        okTxt.setOnClickListener(this);
        titleTxt.setOnClickListener(this);
        titleTxt.setText(content);
    }

    public void showDialog() {
        this.getWindow().setGravity(Gravity.CENTER);
        this.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_txt:
                listener.ok();
                break;

        }
    }

    public interface OnCloseListener {
        void ok();
    }
}
