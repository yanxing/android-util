package com.yanxing.ui;

import com.yanxing.base.BaseFragment;
import com.yanxing.model.Table;
import com.yanxing.view.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * TableView
 * Created by 李双祥 on 2017/4/16.
 */
public class TableViewFragment extends BaseFragment {

    @BindView(R.id.tableView)
    TableView mTableView;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_table_view;
    }

    @Override
    protected void afterInstanceView() {
        List<Table> tableList=new ArrayList<>();
        //表头
        Table table=new Table();
        table.setFirstColumn("项目");
        List<String> stringList=new ArrayList<>();
        for (int i=0;i<15;i++){
            stringList.add("收入"+(i+1));
        }
        table.setOtherColumn(stringList);
        tableList.add(table);
        //内容
        for (int i=0;i<20;i++){
            Table table1=new Table();
            table1.setFirstColumn("项目"+(i+1));
            List<String> stringList1=new ArrayList<>();
            for (int j=0;j<15;j++){
                Random random=new Random();
                stringList1.add(String.valueOf(random.nextInt(100)*100));
            }
            table1.setOtherColumn(stringList1);
            tableList.add(table1);
        }

        mTableView.setTableData(tableList);
    }
}
