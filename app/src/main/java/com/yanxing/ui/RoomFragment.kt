package com.yanxing.ui

import androidx.room.Room

import com.yanxing.base.BaseFragment
import com.yanxing.dao.RoomDataBaseHelper
import com.yanxing.model.Student2

import kotlinx.android.synthetic.main.fragment_room.*

/**
 * @author 李双祥 on 2018/3/20.
 */
class RoomFragment : BaseFragment() {

    private var mRoomDataBaseHelper: RoomDataBaseHelper? = null

    override fun getLayoutResID(): Int {
        return R.layout.fragment_room
    }

    override fun afterInstanceView() {
        mRoomDataBaseHelper = Room.databaseBuilder(activity!!, RoomDataBaseHelper::class.java, "room")
                .allowMainThreadQueries()
                .build()
        showStudents()
        add.setOnClickListener {
            val student2Dao = mRoomDataBaseHelper!!.studentDao
            val student2 = Student2()
            student2.name = "李双祥"
            student2.birth = "2018-03-20"
            student2.sex = "0"
            student2Dao.insert(student2)
            showStudents()
        }
        delete.setOnClickListener {
            val student2Dao = mRoomDataBaseHelper!!.studentDao
            val student2s = student2Dao.findAll()
            if (student2s != null && student2s.size > 0) {
                student2Dao.delete(student2s[student2s.size - 1])
            }
            showStudents()
        }
    }

    private fun showStudents() {
        val student2Dao = mRoomDataBaseHelper!!.studentDao
        val sb = StringBuilder()
        val student2s = student2Dao.findAll()
        for (student2 in student2s) {
            sb.append(student2.toString()).append("\n")
        }
        content.text = sb.toString()
    }
}
