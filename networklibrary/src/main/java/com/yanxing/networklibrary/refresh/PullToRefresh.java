package com.yanxing.networklibrary.refresh;

/**
 * @author 李双祥 on 2017/11/14.
 */
public interface PullToRefresh {

    /**
     * RXJava完成任务请求，停止刷新控件刷新状态
     */
    void refreshComplete();
}
