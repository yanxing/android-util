package com.yanxing.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * adapterlibrary测试
 */
public class AdapterActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView= (ListView) findViewById(R.id.listview);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        List<String> list=new ArrayList<String>();
        list.add("测试");
        list.add("测试");
        CommonAdapter<String> adapter = new CommonAdapter<String>(list,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, String item) {
                viewHolder.setText(R.id.text, item);
            }
        };
        mListView.setAdapter(adapter);
    }
}
