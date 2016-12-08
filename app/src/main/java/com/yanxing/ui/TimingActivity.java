package com.yanxing.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 列表定时器一种实现方式
 * Created by lishuangxiang on 2016/12/7.
 */
public class TimingActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter<Integer> mRecyclerViewAdapter;
    private List<Integer> mList = new ArrayList<Integer>();
    private Timer mTimer = new Timer();
    private static final int UPDATE = 0;
    private static final int FINISH = 1;
    private int mMaxIndex = 0;

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<TimingActivity> mReference;

        MyHandler(TimingActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TimingActivity timingActivity = mReference.get();
            if (msg.what == UPDATE) {
                timingActivity.mRecyclerViewAdapter.update(timingActivity.mList);
            } else if (msg.what == FINISH) {
                timingActivity.mTimer.cancel();
                timingActivity.showToast(timingActivity.getString(R.string.ji_shi_quan_finish));
            }
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_timing;
    }

    @Override
    protected void afterInstanceView() {
        addTestData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter<Integer>(mList, R.layout.adapter_recycler_view) {
            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                TextView textView = (TextView) holder.findViewById(R.id.text);
                textView.setText(String.valueOf(mList.get(position)));
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mList.get(mMaxIndex) == 0) {
                    mHandler.sendEmptyMessage(FINISH);
                    return;
                }
                for (int i = 0; i < mList.size(); i++) {
                    int time = mList.get(i);
                    if (time == 0) {
                        continue;
                    }
                    time--;
                    mList.remove(i);
                    mList.add(i, time);
                }
                mHandler.sendEmptyMessage(UPDATE);
            }
        }, 0, 1000);
    }

    public void addTestData() {
        Random random = new Random(500L);
        int temp = 0;
        for (int i = 0; i < 50; i++) {
            int time = random.nextInt(100);
            if (temp < time) {
                temp = time;
                mMaxIndex = i;
            }
            mList.add(time);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
