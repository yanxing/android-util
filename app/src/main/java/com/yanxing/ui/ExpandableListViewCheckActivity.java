package com.yanxing.ui;

import android.widget.ExpandableListView;

import com.yanxing.adapter.ExpandableListCheckAdapter;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.Child;
import com.yanxing.model.Parent;
import com.yanxing.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ExpandableListView点击一组，全选这个组
 * Created by lishuangxiang on 2016/7/26.
 */
public class ExpandableListViewCheckActivity extends BaseActivity {

    @BindView(R.id.expandableListView)
    ExpandableListView mExpandableListView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_expandable_listview_check;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(true,this);
        mExpandableListView.setGroupIndicator(null);
        Child child1=new Child(false,"西瓜1");
        Child child2=new Child(false,"西瓜2");
        Child child3=new Child(false,"西瓜3");
        List<Child> childList1=new ArrayList<>();
        childList1.add(child1);
        childList1.add(child2);
        childList1.add(child3);
        Parent parent1=new Parent(false,"目录一",childList1);

        List<Child> childList2=new ArrayList<>();
        Child child4=new Child(false,"西瓜4");
        Child child5=new Child(false,"西瓜5");
        Child child6=new Child(false,"西瓜6");
        childList2.add(child4);
        childList2.add(child5);
        childList2.add(child6);
        Parent parent2=new Parent(false,"目录二",childList2);

        List<Child> childList3=new ArrayList<>();
        Child child7=new Child(false,"西瓜7");
        Child child8=new Child(false,"西瓜8");
        childList3.add(child7);
        childList3.add(child8);
        Parent parent3=new Parent(false,"目录三",childList3);

        List<Child> childList4=new ArrayList<>();
        Child child9=new Child(false,"西瓜9");
        childList4.add(child9);
        Parent parent4=new Parent(false,"目录四",childList4);

        List<Parent> parentList=new ArrayList<>();
        parentList.add(parent1);
        parentList.add(parent2);
        parentList.add(parent3);
        parentList.add(parent4);
        ExpandableListCheckAdapter expandableListCheckAdapter = new ExpandableListCheckAdapter(this,parentList);
        mExpandableListView.setAdapter(expandableListCheckAdapter);
    }
}
