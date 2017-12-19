package com.yanxing.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * adapterlibrary测试
 * Created by lishuangxiang on 2016/1/20.
 */
public class CommonAdapterFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView mListView;

    @BindView(R.id.gridView)
    GridView mGridView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_common_adapter;
    }

    @Override
    protected void afterInstanceView() {
        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(i);

        }
        CommonAdapter<Integer> adapter = new CommonAdapter<Integer>(list, R.layout.list_dialog_textview) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.text, String.valueOf(mDataList.get(position)));
            }
        };
        //ListView
        mListView.setAdapter(adapter);
        //GridView
        mGridView.setAdapter(adapter);
        //RecycleView
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter<Integer>(list, R.layout.adapter_recycler_view) {

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                TextView textView = (TextView) holder.findViewById(R.id.text);
                //瀑布流，动态设置item大小
                ViewGroup.LayoutParams lp = textView.getLayoutParams();
                lp.height = (int) (50 + Math.random() * 300);
                textView.setLayoutParams(lp);
                textView.setText(String.valueOf(list.get(position)));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(getString(R.string.you_click) + (position + 1) + getString(R.string.ge));
                    }
                });
            }
        };
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
