package com.yanxing.view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yanxing.commonlibrary.adapter.RecyclerViewAdapter;
import com.yanxing.model.Table;
import com.yanxing.ui.R;
import com.yanxing.util.CommonUtil;
import com.yanxing.util.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格 第一行为标题，最后一行为合计
 * Created by lishuangxiang on 2017/4/4.
 */
public class TableView extends LinearLayout {

    private TextView mHead1;
    private RecyclerView mFirstColumn;
    private LinearLayout mOtherHead;
    private RecyclerView mOtherColumn;
    private View mButtomLine;
    private View mTopLine;
    private View mText;
    private View mLine;

    //展示第一列数据
    private RecyclerViewAdapter<Table> mFirstColumnAdapter;
    //展示其他列数据
    private RecyclerViewAdapter<Table> mOtherColumnAdapter;
    //全部数据
    private List<Table> mColumnData = new ArrayList<>();
    private OnClickTableRow mOnClickTableRow;
    private Context mContext;


    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_view, this);
        mHead1=view.findViewById(R.id.head1);
        mFirstColumn=view.findViewById(R.id.firstColumn);
        mOtherHead=view.findViewById(R.id.otherHead);
        mOtherColumn=view.findViewById(R.id.otherColumn);
        mButtomLine=view.findViewById(R.id.buttomLine);
        mTopLine=view.findViewById(R.id.topLine);
        mText=view.findViewById(R.id.text);
        mLine=view.findViewById(R.id.line);
        //第一列
        mFirstColumnAdapter = new RecyclerViewAdapter<Table>(mColumnData, R.layout.table_view_first_column_item) {
            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnClickTableRow!=null){
                            if (position!=mColumnData.size()-1){//尾部合计不作点击事件
                                mOnClickTableRow.onTableRowClick(position);
                            }
                        }
                    }
                });
                TextView content=holder.findViewById(R.id.content);
                content.setText(mColumnData.get(position).getFirstColumn());
                //最后一行为合计
                if (position==mColumnData.size()-1){
                    content.setBackgroundColor(mContext.getResources().getColor(R.color.color_f7f7f7));
                }else {
                    content.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            }
        };
        //其他列
        mOtherColumnAdapter = new RecyclerViewAdapter<Table>(mColumnData, R.layout.table_view_other_column_item) {
            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                LinearLayout otherContent = holder.findViewById(R.id.otherContent);
                //避免动态添加控件重复 http://blog.csdn.net/u010074743/article/details/54670017?locationNum=15&fps=1
                otherContent.removeAllViews();
                for (int i = 0; i < mColumnData.get(position).getOtherColumn().size(); i++) {
                    //最后一行为合计
                    if (position==mColumnData.size()-1){
                        addContent(mColumnData.get(position).getOtherColumn().get(i), otherContent,true);
                    }else {
                        addContent(mColumnData.get(position).getOtherColumn().get(i), otherContent,false);
                    }
                }
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnClickTableRow!=null){
                            if (position!=mColumnData.size()-1) {//尾部合计不作点击事件
                                mOnClickTableRow.onTableRowClick(position);
                            }
                        }
                    }
                });
            }
        };
        LinearLayoutManager firstLinearLayoutManager=new LinearLayoutManager(mContext);
        firstLinearLayoutManager.setSmoothScrollbarEnabled(true);
        firstLinearLayoutManager.setAutoMeasureEnabled(true);
        mFirstColumn.setLayoutManager(firstLinearLayoutManager);
        mFirstColumn.setHasFixedSize(true);
        mFirstColumn.setNestedScrollingEnabled(false);

        LinearLayoutManager OtherLinearLayoutManager=new LinearLayoutManager(mContext);
        OtherLinearLayoutManager.setSmoothScrollbarEnabled(true);
        OtherLinearLayoutManager.setAutoMeasureEnabled(true);
        mOtherColumn.setLayoutManager(OtherLinearLayoutManager);
        mOtherColumn.setHasFixedSize(true);
        mOtherColumn.setNestedScrollingEnabled(false);
        mFirstColumn.setAdapter(mFirstColumnAdapter);
        mOtherColumn.setAdapter(mOtherColumnAdapter);
        syncScroll(mFirstColumn,mOtherColumn);
    }


    public void setOnClickTableRow(OnClickTableRow onClickTableRow){
        this.mOnClickTableRow=onClickTableRow;
    }

    /**
     * 同时滑动两个RecycleView
     * @param leftList
     * @param rightList
     */
    private void syncScroll(final RecyclerView leftList, final RecyclerView rightList) {
        leftList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    rightList.scrollBy(dx, dy);
                }
            }
        });

        rightList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    leftList.scrollBy(dx, dy);
                }
            }
        });
    }


    /**
     * 设置表格数据，数据第一行为表头
     */
    public void setTableData(List<Table> tables) {
        if (tables == null || tables.size() == 0) {
            return;
        }
        //设置表头
        Table table = tables.get(0);
        mTopLine.setVisibility(VISIBLE);
        mButtomLine.setVisibility(VISIBLE);
        mText.setVisibility(VISIBLE);
        mHead1.setText(table.getFirstColumn());
        mLine.setVisibility(VISIBLE);
        if (table.getOtherColumn() != null) {
            mOtherHead.removeAllViews();
            for (int i = 0; i < table.getOtherColumn().size(); i++) {
                addHead(table.getOtherColumn().get(i));
            }
        }

        //移除标题数据
        tables.remove(0);
        mColumnData.clear();
        mColumnData.addAll(tables);

        if (mColumnData.size()>0){
            //构建尾部合计
            Table foot=new Table();
            foot.setFirstColumn(mContext.getString(R.string.he_ji));
            List<String> strings=new ArrayList<>();
            for (Table table11:mColumnData){
                for (int i=0;i<table11.getOtherColumn().size();i++){
                    String temp=table11.getOtherColumn().get(i);
                    if (!CommonUtil.isDigit(temp)){//非数字不计算
                        if (strings.size()<=i){//第一次合计则添加，合计集合长度大小已确定不添加
                            strings.add("");
                        }
                        continue;//可能某一行还是数字
                    }
                    if (strings.size()<=i){//第一次合计时
                        strings.add(temp);
                    }else {
                        String b=strings.get(i);
                        String c=temp;
                        if ((!b.contains("."))&&(!c.contains("."))){//都是整数计算，结果也是整数
                            int before=Integer.parseInt(strings.get(i));
                            int current=Integer.parseInt(temp);
                            strings.remove(i);
                            strings.add(i,String.valueOf(before+current));
                        }else {
                            double before=Double.parseDouble(strings.get(i));
                            double current=Double.parseDouble(temp);
                            strings.remove(i);
                            strings.add(i,String.valueOf((DoubleUtil.add(before,current))));
                        }
                    }
                }
            }
            foot.setOtherColumn(strings);
            mColumnData.add(foot);
        }

        mFirstColumnAdapter.update(mColumnData);
        mOtherColumnAdapter.update(mColumnData);
    }

    /**
     * 添加表头
     * @param content
     */
    private void addHead(String content) {
        //上分割线
        View lineTop = new View(mContext);
        ViewGroup.LayoutParams paramsLine = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , CommonUtil.dp2px(mContext, 1));
        lineTop.setBackgroundColor(mContext.getResources().getColor(R.color.color_dde0df));
        lineTop.setLayoutParams(paramsLine);

        //文本
        TextView text = new TextView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(CommonUtil.dp2px(mContext, 80)
                , CommonUtil.dp2px(mContext, 40));
        text.setGravity(Gravity.CENTER);
        text.setText(content);
        text.setLayoutParams(params);
        text.setTextSize(12);
        text.setBackgroundColor(mContext.getResources().getColor(R.color.color_f7f7f7));
        text.setTextColor(mContext.getResources().getColor(R.color.black));

        //下分割线
        View lineBottom = new View(mContext);
        lineBottom.setBackgroundColor(mContext.getResources().getColor(R.color.color_dde0df));
        lineBottom.setLayoutParams(paramsLine);

        LinearLayout linearLayout = new LinearLayout(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(lineTop);
        linearLayout.addView(text);
        linearLayout.addView(lineBottom);

        //右分割线
        View lineRight = new View(mContext);
        ViewGroup.LayoutParams paramsLineRight = new ViewGroup.LayoutParams(CommonUtil.dp2px(mContext, 1)
                , ViewGroup.LayoutParams.MATCH_PARENT);
        lineRight.setBackgroundColor(mContext.getResources().getColor(R.color.color_dde0df));
        lineRight.setLayoutParams(paramsLineRight);

        mOtherHead.addView(linearLayout);
        mOtherHead.addView(lineRight);
    }

    /**
     * 添加内容
     *
     * @param content
     */
    private void addContent(String content, ViewGroup viewGroup,boolean isFoot) {
        //文本
        TextView text = new TextView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(CommonUtil.dp2px(mContext, 80)
                , CommonUtil.dp2px(mContext, 40));
        text.setGravity(Gravity.CENTER);
        text.setText(content);
        text.setLayoutParams(params);
        text.setTextSize(9);
        if (isFoot){
            text.setBackgroundColor(mContext.getResources().getColor(R.color.color_f7f7f7));
        }else {
            text.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        text.setTextColor(mContext.getResources().getColor(R.color.black));

        //下分割线
        View lineBottom = new View(mContext);
        ViewGroup.LayoutParams paramsLine = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , CommonUtil.dp2px(mContext, 1));
        lineBottom.setBackgroundColor(mContext.getResources().getColor(R.color.color_dde0df));
        lineBottom.setLayoutParams(paramsLine);

        LinearLayout linearLayout = new LinearLayout(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(text);
        linearLayout.addView(lineBottom);

        //右分割线
        View lineRight = new View(mContext);
        ViewGroup.LayoutParams paramsLineRight = new ViewGroup.LayoutParams(CommonUtil.dp2px(mContext, 1)
                , ViewGroup.LayoutParams.MATCH_PARENT);
        lineRight.setBackgroundColor(mContext.getResources().getColor(R.color.color_dde0df));
        lineRight.setLayoutParams(paramsLineRight);
        viewGroup.addView(linearLayout);
        viewGroup.addView(lineRight);
    }


    public interface OnClickTableRow{
        /**
         * 返回表格第几列（从0开始），不含表头
         * @param position
         */
        void onTableRowClick(int position);
    }
}
