package com.yanxing.networklibrary.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author 李双祥 on 2018/8/16.
 */
public class ToastUtil {

    public static void showToast(Context context, String tip) {
        Toast toast;
        if (tip.length() <= 7) {
            toast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(context, tip, Toast.LENGTH_LONG);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
