package com.yanxing.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yanxing.base.BaseActivity;
import com.yanxing.view.ConfirmDialog;

/**
 * ConfirmDialog测试
 * Created by lishuangxiang on 2016/1/21.
 */
public class ConfirmExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final ConfirmDialog confirmDialog=new ConfirmDialog(this,"确定退出吗？");
        confirmDialog.setConfirmButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"你点击了确定",Toast.LENGTH_LONG).show();
                confirmDialog.dismiss();
            }
        });
    }

}
