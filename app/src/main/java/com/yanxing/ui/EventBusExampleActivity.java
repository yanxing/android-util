package com.yanxing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.yanxing.base.BaseActivity;
import com.yanxing.model.FirstEventBus;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

/**
 * EventBus测试
 * Created by lishuangxiang on 2016/1/28.
 */
@EActivity(R.layout.activity_eventbus_example)
public class EventBusExampleActivity extends BaseActivity {

    @ViewById(R.id.eventbus)
    TextView mEventBus;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        EventBus.getDefault().register(this);
    }

    public void onEvent(FirstEventBus firstEventBus){
        mEventBus.setText(firstEventBus.getMsg());
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
