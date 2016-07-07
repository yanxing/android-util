package com.yanxing.ui.animation;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.User;
import com.yanxing.ui.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * LayoutAnimation
 * Created by lishuangxiang on 2016/7/7.
 */
@EActivity(R.layout.activity_layout_animation_example)
public class LayoutAnimationExampleActivity extends BaseActivity {

    @ViewById(R.id.listview)
    ListView mListView;

    @Override
    @AfterViews
    protected void afterInstanceView() {
        final List<User> list=new ArrayList<User>();
        for (int i=0;i<20;i++){
            User user=new User("1","yanxing");
            list.add(user);

        }
        CommonAdapter<User> adapter = new CommonAdapter<User>(list,R.layout.list_dialog_textview)
        {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.text, list.get(position).getUsername());
            }
        };
        mListView.setAdapter(adapter);
    }
}
