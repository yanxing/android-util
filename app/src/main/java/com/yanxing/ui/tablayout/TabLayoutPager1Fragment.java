package com.yanxing.ui.tablayout;



import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanxing.adapter.RecyclerViewAdapter;
import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;
import com.yanxing.util.CommonUtil;
import com.yanxing.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager1Fragment extends BaseFragment {


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tablayoutpager1;
    }

    @Override
    protected void afterInstanceView() {
        RecyclerView recyclerView=getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false));
        List<Integer> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add(i);
        }
        RecyclerViewAdapter adapter=new RecyclerViewAdapter<Integer>(list,R.layout.adapter_recycler_view1){
            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                TextView textView=holder.findViewById(R.id.text);
                textView.setText(String.valueOf(mDataList.get(position)));
                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.width= CommonUtil.dp2px(requireContext(),20);
                textView.setLayoutParams(layoutParams);
            }
        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        LogUtil.d("TabLayoutPager","0可见");
        super.onResume();
    }

}
