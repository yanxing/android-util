package com.dianmei.analyzelibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dianmei.analyzelibrary.util.TimeUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 上报配置
 *
 * @author 李双祥 on 2017/12/8.
 */
final class UploadConfig {

    private static SharedPreferences mPreferences;
    private static final String USER_ID = "USERID";
    private static final String TIME = "TIME";

    /**
     * 保存用户上报日活的时间,根据用户ID保存一个文件，为了支持多用户登录
     *
     * @param userId 用户ID
     * @param time   格式2017-12-08
     */
    public static boolean saveTodayActive(Context context, String userId, String time) {
        if (!isTime(time)) {
            return false;
        }
        mPreferences = context.getSharedPreferences(userId, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_ID, userId);
        editor.putString(TIME, time);
        return editor.commit();
    }

    /**
     * 用户userId 今天是否已经上报
     *
     * @param context
     * @param userId
     * @return
     */
    public static boolean isUploadToday(Context context, String userId) {
        mPreferences = context.getSharedPreferences(userId, Context.MODE_PRIVATE);
        String userID = mPreferences.getString(USER_ID, "-1");
        String timeOld = mPreferences.getString(TIME, "-1");
        if (userID.equals(userId)) {
            return timeOld.equals(TimeUtil.getToday());
        } else {
            return false;
        }
    }

    /**
     * 时间格式是否是2017-04-05格式
     */
    private static boolean isTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return false;
        }
        String regex = "^(\\d){4}(-(\\d){2}){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}
