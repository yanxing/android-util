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
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * Created by lishuangxiang on 2016/2/19.
 */
public class CommonUtil {
    /**
     * 获取版本名称
     *
     * @return 当前应用的版本名称
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
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
     * 判断字符串是否都是数字，包括负数、小数点，true都是数字
     * @param digit
     * @return
     */
    public static boolean isDigit(String digit){
        String regex="^-?[0-9]*(\\.)?[0-9]*$";
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
            } catch (Exception e) {
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
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, b1.getWidth()<width?b1.getWidth():width
                ,(b1.getHeight()<height?b1.getHeight():height)
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
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName()
                    ,PackageManager.GET_SIGNATURES);
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

    /**
     * 获取字符串的MD5编码.
     */
    public static String getStringByMD5(String string) {
        String md5String = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            byte messageDigestByteArray[] = messageDigest.digest();
            if (messageDigestByteArray == null || messageDigestByteArray.length == 0) {
                return md5String;
            }
            StringBuilder hexadecimalStringBuffer = new StringBuilder();
            int length = messageDigestByteArray.length;
            for (int i = 0; i < length; i++) {
                hexadecimalStringBuffer.append(Integer.toHexString(0xFF & messageDigestByteArray[i]));
            }
            md5String = hexadecimalStringBuffer.toString();
            return md5String;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5String;
    }

    /**
     * 转换dp为px
     */
    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 转换px为dp
     */
    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 转换sp为px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 转换px为sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕参数，不包括虚拟按键
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenMetrics(Context context) {
        try {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取屏幕参数，包括虚拟按键
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getRealScreenMetrics(Context context) {
        try {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getRealMetrics(dm);
            return dm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 过滤html标签
     * @param content
     * @return
     */
    public static String removeHtml(String content){
        String regExScript="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regExStyle="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regExHtml="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern pScript=Pattern.compile(regExScript,Pattern.CASE_INSENSITIVE);
        Matcher mScript=pScript.matcher(content);
        content=mScript.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regExStyle,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(content);
        content=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regExHtml,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(content);
        content=m_html.replaceAll(""); //过滤html标签

        return content.trim(); //返回文本字符串
    }

    /**
     * 获取视频缩略图
     * @param path
     * @return
     */
    public static Bitmap getFrameAtTime(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path, new HashMap<String, String>());
        return retriever.getFrameAtTime();
    }

    /**
     * 获取视频的缩略图,格式有限制
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * @param videoPath 视频的路径
     * @param width 指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图 格式不正确，返回null
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,int kind) {
        Bitmap bitmap;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}
