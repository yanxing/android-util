package com.yanxing.ui;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuangxiang on 2016/5/24.
 */
@EActivity(R.layout.activity_xrecyclerview)
public class XRecyclerViewActivity extends BaseActivity {

    @ViewById(R.id.recyclerview)
    XRecyclerView mXRecyclerView;

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private List<String> mStrings;


    @AfterViews
    @Override
    protected void afterInstanceView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        //test
        mStrings=new ArrayList<String>();
        mStrings.add("1");
        mStrings.add("2");
        mStrings.add("3");
        mStrings.add("4");
        mStrings.add("5");
        mRecyclerViewAdapter=new RecyclerViewAdapter<String>(mStrings,R.layout.adapter_xrecyclerview){

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setText(R.id.name,mStrings.get(position));
            }
        };
        mXRecyclerView.setAdapter(mRecyclerViewAdapter);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                try {
                    Thread.sleep(1000);
                    mXRecyclerView.refreshComplete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadMore() {
                // load more data here
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction==ItemTouchHelper.START){
                    TextView textView= (TextView) viewHolder.itemView.findViewById(R.id.menu2);
                    textView.setVisibility(View.VISIBLE);
                }else {
                    TextView textView= (TextView) viewHolder.itemView.findViewById(R.id.menu2);
                    textView.setVisibility(View.GONE);
//                    textView.to
                }
//                int position = viewHolder.getAdapterPosition();
//                mRecyclerViewAdapter.notifyItemRemoved(position);
//                mStrings.remove(position-1);
//                viewHolder.it
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
                    , float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

            }
        });
        itemTouchHelper.attachToRecyclerView(mXRecyclerView);

    }
}
