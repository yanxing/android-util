package com.yanxing.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 李双祥 on 2018/6/20.
 */
public class EventBusUtil {

    public static EventBusUtil getDefault() {
        return SingleHolder.mSingleHolder;
    }

    private static class SingleHolder {
        private final static EventBusUtil mSingleHolder
                = new EventBusUtil();
    }

    /**
     * 对EventBus register封装，避免重复反注册报错
     *
     * @param subscriber
     */
    public synchronized void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public synchronized void post(Object object) {
        EventBus.getDefault().post(object);
    }

    /**
     * 对EventBus register封装，避免重复注册报错
     *
     * @param subscriber
     */
    public synchronized void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }
}
