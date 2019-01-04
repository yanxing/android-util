package com.yanxing.view;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 自定义线性布局管理器，添加禁止上下滑动
 * Created by lishuangxiang on 2016/11/14.
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {

    private boolean mCanScrollEnable=true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    /**
     * 禁止上下滑动
     * @param enable false禁止滑动
     */
    public void setScrollEnable(boolean enable){
        this.mCanScrollEnable=enable;
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically()&&mCanScrollEnable;
    }
}
