package com.yanxing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yanxing.model.Parent;
import com.yanxing.ui.R;

import java.util.List;

/**
 * Created by lishuangxiang on 2016/7/25.
 */
public class ExpandableListCheckAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Parent> mParentList;

    public ExpandableListCheckAdapter(Context context, List<Parent> parentList) {
        this.mContext = context;
        this.mParentList = parentList;
    }

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mParentList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mParentList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_group_directory, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mGroupItemCheck = (CheckBox) convertView.findViewById(R.id.group_checkBox);
            groupViewHolder.mGroupItemText = (TextView) convertView.findViewById(R.id.group_text);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.mGroupItemText.setText(mParentList.get(groupPosition).getContent());
        groupViewHolder.mGroupItemCheck.setChecked(mParentList.get(groupPosition).isCheck());
        groupViewHolder.mGroupItemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已经全选,将反选
                if (mParentList.get(groupPosition).isCheck()){
                    for (int i=0;i<mParentList.get(groupPosition).getChildList().size();i++){
                        mParentList.get(groupPosition).getChildList().get(i).setCheck(false);
                    }
                    mParentList.get(groupPosition).setCheck(false);
                }else {
                    for (int i=0;i<mParentList.get(groupPosition).getChildList().size();i++){
                        mParentList.get(groupPosition).getChildList().get(i).setCheck(true);
                    }
                    mParentList.get(groupPosition).setCheck(true);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        ChildrenViewHolder childrenViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_children_directory, null);
            childrenViewHolder = new ChildrenViewHolder();
            childrenViewHolder.mChildrenItemCheck = (CheckBox) convertView.findViewById(R.id.children_checkBox);
            childrenViewHolder.mChildrenItemText = (TextView) convertView.findViewById(R.id.children_text);
            convertView.setTag(childrenViewHolder);
        } else {
            childrenViewHolder = (ChildrenViewHolder) convertView.getTag();
        }
        childrenViewHolder.mChildrenItemText.setText(mParentList.get(groupPosition).getChildList().get(childPosition).getContent());
        childrenViewHolder.mChildrenItemCheck.setChecked(mParentList.get(groupPosition).getChildList().get(childPosition).isCheck());
        childrenViewHolder.mChildrenItemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check=mParentList.get(groupPosition).getChildList().get(childPosition).isCheck();
                mParentList.get(groupPosition).getChildList().get(childPosition).setCheck(!check);
            }
        });
        return convertView;
    }

    private class GroupViewHolder {
        CheckBox mGroupItemCheck;
        TextView mGroupItemText;
    }

    private class ChildrenViewHolder {
        CheckBox mChildrenItemCheck;
        TextView mChildrenItemText;
    }
}
