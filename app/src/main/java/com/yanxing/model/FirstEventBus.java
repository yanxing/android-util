package com.yanxing.model;

/**
 * Created by lishuangxiang on 2016/1/28.
 */
public class FirstEventBus {

    private String mMsg;

    public FirstEventBus() {
    }

    public FirstEventBus(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }
}
