package com.dianmei.analyzelibrary;

import java.util.List;

/**
 * 上报的数据
 *
 * @author 李双祥 on 2017/12/6.
 */
public class UploadData {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 设备号
     */
    private String deviceID;
    /**
     * 手机厂商
     */
    private String deviceBrand;
    /**
     * 手机型号
     */
    private String deviceMode;
    /**
     * 系统版本
     */
    private String systemVersion;
    /**
     * 上报时间
     */
    private String time;
    private String versionName;

    private List<PageMap.Page> mPageList;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceMode() {
        return deviceMode;
    }

    public void setDeviceMode(String deviceMode) {
        this.deviceMode = deviceMode;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<PageMap.Page> getPageList() {
        return mPageList;
    }

    public void setPageList(List<PageMap.Page> pageList) {
        mPageList = pageList;
    }

    @Override
    public String toString() {
        return "UploadData{" +
                "userId='" + userId + '\'' +
                ", channel='" + channel + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", deviceMode='" + deviceMode + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                ", time='" + time + '\'' +
                ", versionName='" + versionName + '\'' +
                ", mPageList=" + mPageList +
                '}';
    }
}
