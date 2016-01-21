package com.yanxing.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * adapterlibrary测试
 * Created by lishuangxiang on 2016/1/20.
 */
public class AdapterExampleActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView= (ListView) findViewById(R.id.listview);
        setSupportActionBar(toolbar);
        List<User> list=new ArrayList<User>();
        for (int i=0;i<20;i++){
            User user=new User("1","yanxing");
            list.add(user);

        }
        CommonAdapter<User> adapter = new CommonAdapter<User>(list,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, User user) {
                viewHolder.setText(R.id.text, user.getUsername());
            }
        };
        mListView.setAdapter(adapter);
    }
}
