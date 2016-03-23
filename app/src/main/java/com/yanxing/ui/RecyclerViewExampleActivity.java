package com.yanxing.ui;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by gxianglishuan on 2016/3/23.
 */
@EActivity(R.layout.activity_recyclerview_example)
public class RecyclerViewExampleActivity extends BaseActivity {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    RecyclerViewAdapter mRecyclerViewAdapter;
    private List<String> mStrings=new ArrayList<String>();

    @AfterViews
    @Override
    protected void afterInstanceView() {
        mStrings.add("0");
        mStrings.add("1");
        mStrings.add("0");
        mStrings.add("1");
        mStrings.add("0");
        mStrings.add("1");
        mStrings.add("0");
        mStrings.add("1");
        mStrings.add("0");
        mStrings.add("1");
        //线性
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 网格
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        //瀑布流
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        // 设置item动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new RecyclerViewAdapter<String>(mStrings,R.layout.adapter_recycler_view){

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                View view=holder.getView();
                TextView textView= (TextView) holder.findViewById(R.id.text);
                //瀑布流，动态设置item大小
                ViewGroup.LayoutParams lp = textView.getLayoutParams();
                lp.height = (int) (50+Math.random() * 300);
                textView.setLayoutParams(lp);
                textView.setText(mStrings.get(position));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("你点击了第"+(position+1)+"个");
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showToast("你长按了第"+(position+1)+"个");
                        return true;
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
