package com.yanxing.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseActivity;
import com.yanxing.view.ExtendRecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 扩展RecyclerView,增加滚动到指定position和禁止上下拉滑动
 * Created by lishuangxiang on 2016/11/11.
 */

public class ExtendRecyclerViewActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    RecyclerViewAdapter mRecyclerViewAdapter;
    private List<String> mStrings=new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_extend_recyclerview;
    }

    @Override
    protected void afterInstanceView() {
        for (int i=0;i<50;i++){
            mStrings.add(String.valueOf(i));
        }
        //线性
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter<String>(mStrings,R.layout.adapter_recycler_view){

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                TextView textView= (TextView) holder.findViewById(R.id.text);
                textView.setText(mStrings.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(getString(R.string.you_click)+(position+1)+getString(R.string.ge));
                    }
                });
                holder.itemView.setOnLongClickListener(v -> {
                    showToast(getString(R.string.long_an)+(position+1)+getString(R.string.ge));
                    return true;
                });
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        setScrollEnabled(true);
    }

    public void setScrollEnabled(boolean enabled){
        try {
            Class c = Class.forName("android.support.v7.widget.RecyclerView");
            Field fs = c.getDeclaredField("mLayout");
            fs.setAccessible(true);
//            fs.get
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
