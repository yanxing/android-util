package com.yanxing.ui;

import android.widget.Toast;

import com.yanxing.base.BaseActivity;
import com.yanxing.view.ConfirmDialog;

import org.androidannotations.annotations.AfterViews;

/**
 * ConfirmDialog测试
 * Created by lishuangxiang on 2016/1/21.
 */
public class ConfirmExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_confirm_example;
    }

    @Override
    protected void afterInstanceView() {
        final ConfirmDialog confirmDialog=new ConfirmDialog(this,getString(R.string.exit));
        confirmDialog.setConfirmButton(v -> {
            Toast.makeText(getApplicationContext(), R.string.click_confirm,Toast.LENGTH_LONG).show();
            confirmDialog.dismiss();
        });
    }

}
