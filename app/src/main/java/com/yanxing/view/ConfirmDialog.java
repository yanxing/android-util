package com.yanxing.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanxing.ui.R;

/**
 * 自定义对话框
 * Created by lishuangxiang on 2016/1/6.
 */
public final class ConfirmDialog {

    private AlertDialog myDialog;
    private TextView mCancel;
    private TextView mConfirm;

    /**
     * @param activity
     * @param content 显示内容
     */
    public ConfirmDialog(Activity activity, String content) {
        myDialog = new AlertDialog.Builder(activity).create();
        myDialog.show();
        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     //屏幕宽度（像素）
        //动态设置对话框的大小
        WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
        params.width = width*4/5;
        myDialog.getWindow().setAttributes(params);
        myDialog.setContentView(R.layout.confirm_dialog);
        TextView msg= (TextView) myDialog.findViewById(R.id.content);
        msg.setText(content);
        mConfirm= (TextView) myDialog.findViewById(R.id.confirm);
        mCancel= (TextView) myDialog.findViewById(R.id.cancel);
        LinearLayout root= (LinearLayout) myDialog.findViewById(R.id.root);
        root.setBackgroundDrawable(getShape());
        //取消按钮事件
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 点击确定按钮
     */
    public void setConfirmButton(View.OnClickListener onClickListener) {
        mConfirm.setOnClickListener(onClickListener);
    }

    /**
     * 隐藏对话框
     */
    public void dismiss(){
        myDialog.dismiss();
    }

    /**
     * 圆角边框
     * @return
     */
    public GradientDrawable getShape(){
        int strokeWidth = 1; //边框宽度
        int roundRadius = 30; //圆角半径
        int strokeColor = Color.parseColor("#eeeeee");//边框颜色
        int fillColor = Color.parseColor("#f5f5f5");//内部填充颜色
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }
}
