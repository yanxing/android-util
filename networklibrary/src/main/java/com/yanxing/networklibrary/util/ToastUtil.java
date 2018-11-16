package com.yanxing.networklibrary.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import android.text.TextUtils;

/**
 * @author 李双祥 on 2018/8/16.
 */
public class ToastUtil {

    public static void showToast(Context context, String tip) {
        if (context==null||TextUtils.isEmpty(tip)){
            return;
        }
        Toast toast;
        if (tip.length() <= 30) {
            toast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(context, tip, Toast.LENGTH_LONG);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
