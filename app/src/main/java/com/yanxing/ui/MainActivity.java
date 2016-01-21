package com.yanxing.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity{

    @ViewById
    Toolbar toolbar;

    @Override
    @AfterViews
    protected void afterInstanceView() {
        setSupportActionBar(toolbar);
    }

    @Click(value = {R.id.adapter_button,R.id.list_dialog_button})
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            //通用适配器
            case R.id.adapter_button:
                intent.setClass(this,AdapterExampleActivity.class);
                startActivity(intent);
                break;
            //列表适配器
            case R.id.list_dialog_button:
                intent.setClass(this,ListDialogExampleActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
