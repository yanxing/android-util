package com.yanxing.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yanxing.ui.R;

/**
 * 进度条
 * Created by lishuangxiang on 2015/12/24.
 */
public class LoadingDialog extends Dialog {

    //提示
    private TextView mTip;

    public LoadingDialog(Context context) {
        this(context,null);
    }

    public LoadingDialog(Context context, String tip) {
        super(context, R.style.loading_dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);
        mTip = (TextView) findViewById(R.id.tip);
        if(tip!=null){
            mTip.setVisibility(View.VISIBLE);
            mTip.setText(tip);
        }else {
            mTip.setVisibility(View.GONE);
        }
    }
}