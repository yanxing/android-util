package com.yanxing.ui.animation;

import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.User;
import com.yanxing.ui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * LayoutAnimation
 * Created by lishuangxiang on 2016/7/7.
 */
public class LayoutAnimationExampleActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView mListView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_layout_animation_example;
    }

    @Override
    protected void afterInstanceView() {
        final List<User> list=new ArrayList<User>();
        for (int i=0;i<20;i++){
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
    }
}
