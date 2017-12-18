package com.dianmei.analyzelibrary;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dianmei.analyzelibrary.util.DeviceUtil;
import com.dianmei.analyzelibrary.util.TimeUtil;
import com.dianmei.analyzelibrary.util.UploadConfig;

/**
 * 统计服务
 * 1.启动次数：应用完全退出后重新启动，记为一次使用；应用被切换至后台后，30秒后被切换至前台，记为一次使用，若未超过30秒切换至前台，则不算一次使用。
 * 2.日活跃用户：今日启动过应用的用户（去重），UI可见（位于前台）
 * 3.渠道来源：由启动传入渠道名称
 *
 * @author 李双祥 on 2017/12/6.
 */
public class AnalyzeService extends IntentService {

    private static final String TAG = "AnalyzeService";
    private static final String ANDROID="android";
    private boolean mLog = false;
    private long mLastTime;
    private boolean mBackground = false;
    /**
     * 渠道名称
     */
    private String mChannel;
    private String mUserId;

    public AnalyzeService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mChannel = intent.getStringExtra("channel");
        mUserId = intent.getStringExtra("userId");
        mLog = intent.getBooleanExtra("log", false);
        UploadData uploadData = new UploadData();
        uploadData.setChannel(mChannel);
        uploadData.setUserId(mUserId);
        uploadData.setDeviceBrand(DeviceUtil.getDeviceBrand());
        uploadData.setDeviceID(DeviceUtil.getDeviceId(getApplicationContext()));
        uploadData.setDeviceMode(DeviceUtil.getSystemModel());
        uploadData.setSystemVersion(ANDROID+" "+DeviceUtil.getSystemVersion());
        uploadData.setTime(TimeUtil.getTime());
        uploadData.setVersionName(DeviceUtil.getVersionName(getApplicationContext()));
        uploadDayActive(uploadData);
        uploadStartCount(uploadData);
        while (true) {
            uploadData.setTime(TimeUtil.getTime());
            //处在后台
            if (DeviceUtil.isBackground(getApplicationContext())) {
                mBackground = true;
            } else {
                //后台停留超过30s
                if (System.currentTimeMillis() - mLastTime > 30 * 1000 && mBackground) {
                    uploadStartCount(uploadData);
                }
                mLastTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * 上报启动次数，记为一次启动
     *
     * @param uploadData 渠道设备信息等数据
     */
    private void uploadStartCount(UploadData uploadData) {
        if (mLog) {
            Log.d(TAG, "上报启动次数一次"+uploadData.toString());
        }


    }

    /**
     * 上报日活跃,记为一个用户活跃
     * 今日应用启动（登录状态）记为一个用户日活，如果今日已经上报，则不再上报
     *
     * @param uploadData 渠道设备信息等数据
     */
    private void uploadDayActive(UploadData uploadData) {
        //今天该用户已经上报过
        if (UploadConfig.isUploadToday(getApplicationContext(), mUserId)) {
            return;
        }
        UploadConfig.saveTodayActive(getApplicationContext(), mUserId, TimeUtil.getToday());
        if (mLog) {
            Log.d(TAG, "上报用户使用一次（日活）"+uploadData.toString());
        }
    }
}
