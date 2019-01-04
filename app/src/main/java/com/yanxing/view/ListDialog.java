package com.yanxing.view;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.yanxing.ui.R;

import java.util.List;

/**
 * 列表对话框
 * Created by lishuangxiang on 2016/1/8.
 */
public class ListDialog {

    private AlertDialog mListDialog;
    private ListView mListView;

    /**
     * @param activity
     * @param list 显示内容
     */
    public ListDialog(Activity activity, final List<String> list) {

        mListDialog = new AlertDialog.Builder(activity).create();
        mListDialog.show();
        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        //动态设置对话框的大小
        WindowManager.LayoutParams params = mListDialog.getWindow().getAttributes();
        params.width = width*4/5;
        mListDialog.getWindow().setAttributes(params);
        mListDialog.setContentView(R.layout.list_dialog);
        mListView= (ListView) mListDialog.findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(), R.layout.list_dialog_textview, list));
    }

    /**
     * 隐藏对话框
     */
    public void dismiss(){
        mListDialog.dismiss();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        mListView.setOnItemClickListener(onItemClickListener);
    }
}
