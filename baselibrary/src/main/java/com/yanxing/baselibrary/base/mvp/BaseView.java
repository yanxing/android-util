package com.yanxing.baselibrary.base.mvp;

/**
 * @author 李双祥 on 2017/11/20.
 */
public interface BaseView<T> {

    /**
     * 请求的数据
     * @param data
     */
    void setData(T data);
}
