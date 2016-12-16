package com.yanxing.ui;

import android.widget.GridView;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * adapterlibrary测试
 * Created by lishuangxiang on 2016/1/20.
 */
public class AdapterExampleActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView mListView;

    @BindView(R.id.gridView)
    GridView mGridView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_adapter_example;
    }

    @Override
    protected void afterInstanceView() {
        final List<User> list=new ArrayList<User>();
        for (int i=0;i<6;i++){
            User user=new User("1","yanxing");
            list.add(user);

        }
        CommonAdapter<User> adapter = new CommonAdapter<User>(list,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.text, list.get(position).getUsername());
            }
        };
        mListView.setAdapter(adapter);

        mGridView.setAdapter(adapter);
    }
}
