package com.yanxing.ui

import com.yanxing.base.BaseFragment
import com.yanxing.model.Table

import java.util.ArrayList
import java.util.Random

import kotlinx.android.synthetic.main.fragment_table_view.*

/**
 * TableView
 * Created by 李双祥 on 2017/4/16.
 */
class TableViewFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.fragment_table_view
    }

    override fun afterInstanceView() {
        val tableList = ArrayList<Table>()
        //表头
        val table = Table()
        table.firstColumn = "项目"
        val stringList = ArrayList<String>()
        for (i in 0..14) {
            stringList.add("收入" + (i + 1))
        }
        table.otherColumn = stringList
        tableList.add(table)
        //内容
        for (i in 0..19) {
            val table1 = Table()
            table1.firstColumn = "项目" + (i + 1)
            val stringList1 = ArrayList<String>()
            for (j in 0..14) {
                val random = Random()
                stringList1.add((random.nextInt(100) * 100).toString())
            }
            table1.otherColumn = stringList1
            tableList.add(table1)
        }

        tableView.setTableData(tableList)
    }
}
