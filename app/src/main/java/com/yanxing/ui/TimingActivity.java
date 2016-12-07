package com.yanxing.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.CountDownTimer;
import com.yanxing.util.LogUtil;

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

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private List<String> mStrings = new ArrayList<String>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_timing;
    }

    @Override
    protected void afterInstanceView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Random random=new Random(500L);
        int max=0;
        for (int i=0;i<16;i++){
            int time=random.nextInt(100);
            if (time>max){
                max=time;
            }
            LogUtil.d(TAG,String.valueOf(time));
            mStrings.add(String.valueOf(time));
        }
        mRecyclerViewAdapter = new RecyclerViewAdapter<String>(mStrings, R.layout.adapter_recycler_view) {

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                if (mStrings.get(position).equals("0")){
                    return;
                }
                TextView textView = (TextView) holder.findViewById(R.id.text);
                textView.setText(mStrings.get(position));
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        CountDownTimer countDownTimer=new CountDownTimer(max*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                for (int i=0;i<mStrings.size();i++){
                    int time=Integer.valueOf(mStrings.get(i));
                    if (time==0){
                        continue;
                    }
                    time--;
                    mStrings.add(i,String.valueOf(time));
                }
                mRecyclerViewAdapter.update(mStrings);
            }

            @Override
            public void onFinish() {

            }
        }.start();
        countDownTimer.start();

    }
}
