package com.yanxing.ui;

import android.view.View;
import android.widget.Toast;

import com.yanxing.base.BaseActivity;
import com.yanxing.view.ConfirmDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * ConfirmDialog测试
 * Created by lishuangxiang on 2016/1/21.
 */
@EActivity(R.layout.activity_confirm_example)
public class ConfirmExampleActivity extends BaseActivity {

    @Override
    @AfterViews
    protected void afterInstanceView() {
        final ConfirmDialog confirmDialog=new ConfirmDialog(this,getString(R.string.exit));
        confirmDialog.setConfirmButton(v -> {
            Toast.makeText(getApplicationContext(), R.string.click_confirm,Toast.LENGTH_LONG).show();
            confirmDialog.dismiss();
        });
    }

}
