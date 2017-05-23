package com.yanxing.net;


import com.yanxing.base.MyApplication;
import com.yanxing.util.LoadDialogUtil;
import com.yanxing.util.ToastUtil;

import rx.Subscriber;

/**
 * 统一处理处理onCompleted onError
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class RxSubscriberHelper<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        LoadDialogUtil.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        LoadDialogUtil.dismiss();
        if (MyApplication.getInstance()!=null){
            ToastUtil.showToast(MyApplication.getInstance(),ErrorCodeUtil.getException(e));
        }
    }

    @Override
    public void onNext(T t) {
        onCall(t);
    }

    public abstract void onCall(T t);
}
