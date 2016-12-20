package com.yanxing.ui;

import android.view.View;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.base.MyApplication;
import com.yanxing.dao.StudentDao;
import com.yanxing.model.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * GreenDao例子
 * Created by lishuangxiang on 2016/5/5.
 */
public class GreenDaoExampleActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView mListView;

    private List<Student> mStudentList=new ArrayList<Student>();
    private CommonAdapter<Student> mStudentCommonAdapter;
    private StudentDao mStudentDao;

    private static int i=0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_greendao_example;
    }

    @Override
    protected void afterInstanceView() {
        mStudentDao=((MyApplication)getApplicationContext()).getDaoSession().getStudentDao();
        mStudentList=mStudentDao.queryBuilder().orderDesc(StudentDao.Properties.Id).list();
        mStudentCommonAdapter = new CommonAdapter<Student>(mStudentList,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.text, mStudentList.get(position).getName()+"   "+mStudentList.get(position).getSex());
            }
        };
        mListView.setAdapter(mStudentCommonAdapter);
    }

    @OnClick(value = R.id.button)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
                Student student=new Student("yanxing"+i,"0","13");
                mStudentDao.insert(student);
                mStudentList.add(student);
                mStudentCommonAdapter.update(mStudentList);
                showToast(getString(R.string.operation));
                i++;
                break;
        }
    }

    @OnItemClick(value = R.id.listview)
    public void onItemClick(int position){
        mStudentDao.delete(mStudentList.get(position));
        mStudentList.remove(position);
        mStudentCommonAdapter.update(mStudentList);
        showToast(getString(R.string.delete_success));
    }
}
