package com.yanxing.ui.time;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import com.yanxing.commonlibrary.adapter.RecyclerViewAdapter;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 李双祥 on 2018/9/5.
 */
public class OrderCountDown {

    private ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();
    private SparseArray mFinishMap=new SparseArray<>();
    private RecyclerViewAdapter<Order> mRecyclerViewAdapter;
    private static final int UPDATE = 0;
    private static final int FINISH = 1;

    private OrderCountDown() {

    }

    public static OrderCountDown getInstance() {
        return SingletonHolder.orderCountDown;
    }

    private static class SingletonHolder {
        private static final OrderCountDown orderCountDown = new OrderCountDown();
    }

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<OrderCountDown> mReference;

        MyHandler(OrderCountDown orderCountDown) {
            mReference = new WeakReference<>(orderCountDown);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE) {
                mReference.get().mRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                mReference.get().mExecutorService.shutdown();
            }
        }
    }

    public void start(final List<Order> orders, RecyclerViewAdapter<Order> recyclerViewAdapter) {
        mFinishMap.clear();
        mRecyclerViewAdapter = recyclerViewAdapter;
        mExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                if (mFinishMap.size() == orders.size()) {
                    message.what = FINISH;
                } else {
                    for (int i=0;i<orders.size();i++) {
                        if (orders.get(i).getPayRemaindSecond() > 0) {
                            orders.get(i).setPayRemaindSecond(orders.get(i).getPayRemaindSecond() - 1);
                        } else {
                            mFinishMap.put(i,true);
                        }
                    }
                    message.what = UPDATE;
                    mHandler.sendMessage(message);

                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void close() {
        mExecutorService.shutdown();
    }

}
