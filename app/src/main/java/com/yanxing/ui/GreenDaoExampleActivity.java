package com.yanxing.ui;

import android.view.View;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.base.MyApplication;
import com.yanxing.dao.StudentDao;
import com.yanxing.model.Student;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * GreenDao例子
 * Created by lishuangxiang on 2016/5/5.
 */
@EActivity(R.layout.activity_greendao_example)
public class GreenDaoExampleActivity extends BaseActivity {

    @ViewById(R.id.listview)
    ListView mListView;

    private List<Student> mStudentList=new ArrayList<Student>();
    private CommonAdapter<Student> mStudentCommonAdapter;
    private StudentDao mStudentDao;

    private static int i=0;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        mStudentDao=((MyApplication)getApplicationContext()).getDaoSession().getStudentDao();
        Query query = mStudentDao.queryBuilder()
                .orderDesc(StudentDao.Properties.Id)
                .build();
        mStudentList=query.list();
        mStudentCommonAdapter = new CommonAdapter<Student>(mStudentList,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.text, mStudentList.get(position).getName()+"   "+mStudentList.get(position).getSex());
            }
        };
        mListView.setAdapter(mStudentCommonAdapter);
    }

    @Click(value = R.id.button)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
                Student student=new Student();
                student.setName("yanxing"+i);
                student.setSex("男");
                mStudentDao.insert(student);
                mStudentList.add(student);
                mStudentCommonAdapter.update(mStudentList);
                showToast("操作成功");
                i++;
                break;
        }
    }

    @ItemClick(value = R.id.listview)
    public void onItemClick(int position){
        mStudentDao.delete(mStudentList.get(position));
        mStudentList.remove(position);
        mStudentCommonAdapter.update(mStudentList);
        showToast("删除成功");
    }
}
