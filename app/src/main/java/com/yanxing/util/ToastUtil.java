package com.yanxing.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 李双祥 on 2017/5/23.
 */
public class ToastUtil {

    /**
     * 显示toast消息
     */
    public static void showToast(Context context,String toast){
        Toast.makeText(context,toast,Toast.LENGTH_LONG).show();
    }
}
