package com.yanxing.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yanxing.base.BaseActivity;
import com.yanxing.view.ListDialog;

import java.util.ArrayList;
import java.util.List;
/**
 * ListDialog测试
 * Created by lishuangxiang on 2016/1/21.
 */
public class ListDialogExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_list_dialog_example;
    }

    @Override
    protected void afterInstanceView() {
        final List<String> list=new ArrayList<String>();
        for (int i=1;i<4;i++){
            list.add("yanxing"+i);
        }
        final ListDialog listDialog=new ListDialog(this,list);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),getString(R.string.you_select)+list.get(position),Toast.LENGTH_LONG).show();
                listDialog.dismiss();
            }
        });
    }

}
