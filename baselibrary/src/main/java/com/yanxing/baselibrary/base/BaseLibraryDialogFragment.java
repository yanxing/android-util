package com.yanxing.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.yanxing.baselibrary.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 李双祥 on 2017/11/24.
 */
public abstract class BaseLibraryDialogFragment extends RxDialogFragment {

    public String TAG=getClass().getName();
    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment_style);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResID(), container);
        mUnbinder = ButterKnife.bind(this, view);
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterInstanceView();
    }

    /**
     * 子类布局ID
     */
    protected abstract int getLayoutResID();

    /**
     * 实例化控件之后操作
     */
    protected abstract void afterInstanceView();

    /**
     * 显示toast消息
     *
     * @param tip
     */
    public void showToast(String tip) {
        if (getActivity()!=null){
            Toast toast = Toast.makeText(getActivity(), tip, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
