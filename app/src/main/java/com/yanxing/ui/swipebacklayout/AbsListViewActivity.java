package com.yanxing.ui.swipebacklayout;

import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
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
        final List<Integer> list=new ArrayList<Integer>();
        for (int i=0;i<20;i++){
            list.add(i);
        }
        CommonAdapter<Integer> adapter = new CommonAdapter<Integer>(list,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.text, String.valueOf(list.get(position)));
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
