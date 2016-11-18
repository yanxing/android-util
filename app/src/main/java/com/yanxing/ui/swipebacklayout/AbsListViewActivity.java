package com.yanxing.ui.swipebacklayout;

import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.model.User;
import com.yanxing.ui.R;
import com.yanxing.util.CommonUtil;
import com.yanxing.view.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lishuangxiang on 2016/11/17.
 */

public class AbsListViewActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView mListView;

    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_abslist_view;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(true,this);
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
        mSwipeBackLayout.setTouchView(mListView);
        mSwipeBackLayout.addOnSlidingFinishListener(new SwipeBackLayout.OnSlidingFinishListener() {
            @Override
            public void onSlidingFinish() {
                AbsListViewActivity.this.finish();
            }
        });
    }
}
