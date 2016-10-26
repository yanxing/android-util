package com.yanxing.ui;

import android.widget.AdapterView;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Created by lishuangxiang on 2016/10/26.
 */

public class HorizontalScrollviewActivity extends BaseActivity{

    @BindView(R.id.listview)
    ListView mListView;

    private CommonAdapter<String> mCommonAdapter;
    private List<String> mList=new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_horizontal_scrollview;
    }

    @Override
    protected void afterInstanceView() {
        //test
        for (int i=0;i<20;i++){
            mList.add(""+i);
        }
        mCommonAdapter=new CommonAdapter<String>(mList,R.layout.adapter_horizontal_scrollview) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
//                viewHolder.findViewById(R.id.position).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        showToast("点击了第"+(position+1)+"项");
//                    }
//                });

            }
        };
        mListView.setAdapter(mCommonAdapter);
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(int position){
        showToast("点击了第"+(position+1)+"项");
    }
}
