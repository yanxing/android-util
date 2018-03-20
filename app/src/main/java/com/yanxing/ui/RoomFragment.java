package com.yanxing.ui;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.dao.RoomDataBaseHelper;
import com.yanxing.dao.Student2Dao;
import com.yanxing.model.Student2;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 李双祥 on 2018/3/20.
 */
public class RoomFragment extends BaseFragment {

    @BindView(R.id.content)
    TextView mContent;

    private RoomDataBaseHelper mRoomDataBaseHelper;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_room;
    }

    @Override
    protected void afterInstanceView() {
        mRoomDataBaseHelper = Room.databaseBuilder(getActivity(), RoomDataBaseHelper.class, "room")
                .allowMainThreadQueries()
                .build();
        showStudents();
        getView();

    }

    @OnClick(R.id.add)
    public void add() {
        Student2Dao student2Dao = mRoomDataBaseHelper.getStudentDao();
        Student2 student2 = new Student2();
        student2.setName("李双祥");
        student2.setBirth("2018-03-20");
        student2.setSex("0");
        student2Dao.insert(student2);
        showStudents();
    }

    @OnClick(R.id.delete)
    public void delete() {
        Student2Dao student2Dao = mRoomDataBaseHelper.getStudentDao();
        List<Student2> student2s = student2Dao.findAll();
        if (student2s != null && student2s.size() > 0) {
            student2Dao.delete(student2s.get(student2s.size() - 1));
        }
        showStudents();
    }

    private void showStudents() {
        Student2Dao student2Dao = mRoomDataBaseHelper.getStudentDao();
        StringBuilder sb = new StringBuilder();
        List<Student2> student2s = student2Dao.findAll();
        for (Student2 student2 : student2s) {
            sb.append(student2.toString()).append("\n");
        }
        mContent.setText(sb.toString());
    }
}
