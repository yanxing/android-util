package com.yanxing.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseActivity;
import com.yanxing.base.BaseFragment;
import com.yanxing.view.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 扩展RecyclerView,增加禁止上下滑动和滑动到指定位置(参考http://blog.csdn.net/tyzlmjj/article/details/49227601)
 * Created by lishuangxiang on 2016/11/11.
 */
public class ExtendRecyclerViewFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter<String> mRecyclerViewAdapter;
    private CustomLinearLayoutManager mLayoutManager;
    private boolean mNeedScroll;
    private List<String> mStrings = new ArrayList<>();
    private int mPosition = 20;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_extend_recyclerview;
    }

    @Override
    protected void afterInstanceView() {
        for (int i = 0; i < 80; i++) {
            mStrings.add(String.valueOf(i));
        }
        mLayoutManager = new CustomLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter<String>(mStrings, R.layout.adapter_recycler_view) {

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                TextView textView = (TextView) holder.findViewById(R.id.text);
                textView.setText(mStrings.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(getString(R.string.you_click) + (position + 1) + getString(R.string.ge));
                    }
                });
                holder.itemView.setOnLongClickListener(v -> {
                    showToast(getString(R.string.long_an) + (position + 1) + getString(R.string.ge));
                    return true;
                });
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerViewListener());
    }

    @OnClick({R.id.canScroll, R.id.noScroll, R.id.scrollPosition})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.canScroll:
                mLayoutManager.setScrollEnable(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                break;
            case R.id.noScroll:
                mLayoutManager.setScrollEnable(false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                break;
            case R.id.scrollPosition:
                mPosition = 20;
                moveToPosition(mPosition);
                break;
        }
    }

    /**
     * 滑动监听，当指定位置位于最后可见项的后面时，移动最后的距离
     */
    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mNeedScroll) {
                mNeedScroll = false;
                int top = mRecyclerView.getChildAt(mPosition - mLayoutManager.findFirstVisibleItemPosition()).getTop();
                mRecyclerView.scrollBy(0, top);
            }
        }
    }

    /**
     * 指定位置置顶
     *
     * @param n
     */
    private void moveToPosition(int n) {
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
        //当要置顶的项在当前显示的第一个项的前面时
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {//当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时,先显示出来
            mRecyclerView.scrollToPosition(n);
            mNeedScroll = true;
        }
    }
}
