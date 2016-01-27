package com.baidu.map.event;

import android.graphics.Bitmap;

/**
 * 百度地图截图
 * Created by lishuangxiang on 2016/1/27.
 */
public interface SnapshotListener {

    /**
     * 截图
     * @param snapshot
     */
    void onSnapshotReady(Bitmap snapshot);
}
