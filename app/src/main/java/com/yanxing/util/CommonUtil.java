package com.yanxing.util;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lishuangxiang on 2016/2/19.
 */
public class CommonUtil {
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param mobile 手机号
     * @return 手机号格式正确返回true，错误返回false
     */
    public static boolean isMobile(String mobile) {
//        String regex = "^((\\+86)|(86))?\\d{11}$";//匹配手机号前带86或是+86的情况
        String regex = "^((1[3,5,8][0-9])|(14[0-9])|(17[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email 邮箱
     * @return 邮箱格式输入正确返回true，错误返回false
     */
    public static boolean isEmail(String email) {
        // 匹配邮箱的正则表达时，在java需要进行转义^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$
        // ^ ：匹配输入的开始位置
        // \：将下一个字符标记为特殊字符或字面值
        // * ：匹配前一个字符零次或几次
        // + ：匹配前一个字符一次或多次
        // (pattern) 与模式匹配并记住匹配
        // \w ：与任何单词字符匹配，包括下划线
        // {n,m} 最少匹配 n 次且最多匹配 m 次
        // [a-z] ：表示某个范围内的字符。与指定区间内的任何字符匹配
        // ?：匹配前面的子表达式零次或一次
        // $ ：匹配输入的结尾
        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 判断字符串是否都是数字，true都是数字
     * @param digit
     * @return
     */
    public static boolean isDigit(String digit){
        String regex="^[0-9]*$";
        Pattern pattern=Pattern.compile(regex);
        return pattern.matcher(digit).matches();
    }

    /**
     * 判断身份证是否合法，此为粗略判断
     * @param IDCard
     * @return
     */
    public static boolean isIDCard(String IDCard){
        String regex="^(\\d{14}[0-9])|(\\d{17}([0-9]|x|X))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(IDCard);
        return matcher.matches();
    }

    /**
     * 打电话
     * @param context
     * @param mobile
     * @return
     */
    public static boolean callPhone(Context context,String mobile){
        if (!isMobile(mobile)){
            return false;
        }
        Uri uri = Uri.parse("tel:" + mobile);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    /**
     * 关闭输入法
     * @param context
     * @param windowToken
     */
    public static void hideInputMethod(Context context, IBinder windowToken){
        try {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        } catch (Exception e) {
        }
    }

    /*
	 * 判断字串是否为空
     * @param String
     * @return boolean
     */
    public static boolean isNull(String str) {
        return TextUtils.isEmpty(str) ? true : false;
    }

    /**
     * 获取通知栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName(
                        "com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Fragment.InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 截图
     * @param path 截图保存的路径
     */
    public static String  takeScreenShot(Activity activity,String path) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, b1.getWidth()<width?b1.getWidth():width, (b1.getHeight()<height?b1.getHeight():height)
                - statusBarHeight);
        view.destroyDrawingCache();
        if(b != null){
            try{
                FileOutputStream out = new FileOutputStream(path);
                b.compress(Bitmap.CompressFormat.JPEG,80,out);//压缩图片
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 判断当前应用程序是否处于后台,true后台，false前台
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    LogUtil.d("AppUtil",String.format("Background App:", appProcess.processName));
                    return true;
                } else {
                    LogUtil.d("AppUtil",String.format("Foreground App:",appProcess.processName));
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获取设备号，IMEI
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 显示默认键盘
     * @param context
     */
    public static void showInputKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm. toggleSoftInput (InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏默认键盘
     * @param context
     */
    public static void hideInputKeyBoard(Context context,EditText editText){
        InputMethodManager imm =((InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE));
        imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
    }

    /**
     * 校验应用签名
     *
     * @param context
     */
    public static boolean checkSignInfo(Context context,String MD5) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sign.toByteArray());
            byte[] digest = md.digest();
            String res = toHexString(digest);
            if (MD5.equals(res)){
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 转成十六进制
     * @param b
     * @param buf
     */
    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /**
     * 格式化MD5值
     * @param block
     * @return
     */
    private static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }
}