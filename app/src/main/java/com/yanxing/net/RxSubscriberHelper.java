package com.yanxing.net;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yanxing.base.MyApplication;
import com.yanxing.model.BaseModel;
import com.yanxing.util.ToastUtil;
import com.yanxing.view.LoadDialog;

import rx.Subscriber;

/**
 * 统一处理处理onCompleted onError
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class RxSubscriberHelper<T extends BaseModel> extends Subscriber<T> {

    private FragmentManager mFragmentManager;

    /**
     * 用来销毁进度条对话框 LoadDialog，如果使用RxIOHelper RxAppCompatActivity RxFragment的FragmentManager
     * RxIOHelper内部工具类显示对话框，弱引用可能某些情况下FragmentManager为空，无法取消对话框
     * @param fragmentManager
     */
    protected RxSubscriberHelper(FragmentManager fragmentManager){
        this.mFragmentManager=fragmentManager;
    }

    protected RxSubscriberHelper(){
    }

    @Override
    public void onCompleted() {
        if (mFragmentManager != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
            if (fragment != null) {
                //移除正在显示的对话框
                fragmentTransaction.remove(fragment).commitNow();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (MyApplication.getInstance()!=null){
            ToastUtil.showToast(MyApplication.getInstance(),ErrorCodeUtil.getException(e));
        }
        if (mFragmentManager!=null){
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
            if (fragment != null) {
                fragmentTransaction.remove(fragment).commitNow();
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (t.getStatus().equals("0")){
            onCall(t);
        }else {
            if (MyApplication.getInstance()!=null){
                ToastUtil.showToast(MyApplication.getInstance(),t.getMessage());
            }
        }
    }

    public abstract void onCall(T t);
}
