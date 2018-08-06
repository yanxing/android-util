package com.yanxing.ui


import com.yanxing.adapterlibrary.CommonAdapter
import com.yanxing.adapterlibrary.ViewHolder
import com.yanxing.base.BaseFragment
import com.yanxing.base.SampleApplicationLike
import com.yanxing.dao.StudentDao
import com.yanxing.model.Student

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_greendao.*

/**
 * GreenDao例子
 * Created by lishuangxiang on 2016/5/5.
 */
class GreenDaoFragment : BaseFragment() {

    private var mStudentList: MutableList<Student> = ArrayList()
    private lateinit var mStudentCommonAdapter: CommonAdapter<Student>
    private lateinit var mStudentDao: StudentDao

    override fun getLayoutResID(): Int {
        return R.layout.fragment_greendao
    }

    override fun afterInstanceView() {
        mStudentDao = SampleApplicationLike.getInstance().daoSession.studentDao
        mStudentList = mStudentDao.queryBuilder().orderDesc(StudentDao.Properties.Id).list()
        mStudentCommonAdapter = object : CommonAdapter<Student>(mStudentList, R.layout.list_dialog_textview) {
            override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
                viewHolder.setText(R.id.text, mDataList[position].name + "   " + mDataList[position].sex)
            }
        }
        listview.adapter = mStudentCommonAdapter
        var i=0
        button.setOnClickListener {
            val student = Student("yanxing$i", "0", "13")
            mStudentDao.insert(student)
            mStudentList.add(student)
            mStudentCommonAdapter!!.update(mStudentList)
            showToast(getString(R.string.operation))
            i++
        }
        listview.setOnItemClickListener { parent, view, position, id ->
            run {
                mStudentDao.delete(mStudentList[position])
                mStudentList.removeAt(position)
                mStudentCommonAdapter.update(mStudentList)
                showToast(getString(R.string.delete_success))
            }
        }
    }
}
