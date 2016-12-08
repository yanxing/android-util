package com.yanxing.ui;

import android.widget.TextView;

import com.yanxing.base.BaseActivity;
import com.yanxing.model.FirstEventBus;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * EventBus测试
 * http://blog.csdn.net/lyxtime/article/details/50601632
 * Created by lishuangxiang on 2016/1/28.
 */
public class EventBusExampleActivity extends BaseActivity {

    @BindView(R.id.eventbus)
    TextView mEventBus;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_eventbus_example;
    }

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
