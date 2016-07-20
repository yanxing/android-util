package com.yanxing.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * BufferKnife+Android ButterKnife Zelezny
 * androidannotations与ButterKnife使用心得：两者都有view注解、事件绑定，而androidannotations还有线程切换、网络访问等功能；
 * 两者都是预编译，比其他采用运行时反射View注解框架性能优些；androidannotations中布局可以通过@EActivity注解写到Activity类上面，而ButterKnife需要自己写代码
 * setContent或者LayoutInflater加载布局，从而两者的BaseActivity里面的代码量不同，使用ButterKnife子类Activity返回一个布局对象给BaseActivity，
 * 然后写上ButterKnife.bind(this)，所有的子类就不用重复写ButterKnife.bind(this)，而androidannotations不需要这些写代码，一个注解就行了，但是注册和启动
 * Activity需要给Activity类名加上后缀_;Android studio有ButterKnife插件，可以自动生成View注解和事件绑定，而androidannotations没有此类插件，这点ButterKnife
 * 更节省时间。
 * Created by lishuangxiang on 2016/7/20.
 */
public class ButterKnifeExampleActivity extends FragmentActivity {

    @BindView(R.id.textview1)
    TextView mTextview1;

    @BindView(R.id.text_view_test)
    TextView mTextViewTest;

    @BindView(R.id.textView3)
    TextView mTextView3;

    @BindView(R.id.textView4)
    TextView mTextView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife_example);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @OnClick({R.id.textview1, R.id.text_view_test, R.id.textView3, R.id.textView4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview1:
                break;
            case R.id.text_view_test:
                break;
            case R.id.textView3:
                break;
            case R.id.textView4:
                break;
        }
    }
}
