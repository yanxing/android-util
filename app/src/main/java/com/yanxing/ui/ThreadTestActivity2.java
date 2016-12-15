package com.yanxing.ui;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yanxing.util.LogUtil;

public class ThreadTestActivity2 extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test2);
        mButton= (Button) findViewById(R.id.finish);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(1000);
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("ThreadTestActivity","onDestroy");
        if (mButton!=null){
            LogUtil.d("ThreadTestActivity","onDestroy button is not null");
        }else {
            LogUtil.d("ThreadTestActivity","onDestroy button is null");
        }
    }
}
