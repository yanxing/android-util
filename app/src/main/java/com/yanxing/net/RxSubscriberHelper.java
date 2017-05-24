package com.yanxing.net;


import com.yanxing.base.MyApplication;
import com.yanxing.model.BaseModel;
import com.yanxing.util.LoadDialogUtil;
import com.yanxing.util.ToastUtil;

import rx.Subscriber;

/**
 * 统一处理处理onCompleted onError
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class RxSubscriberHelper<T extends BaseModel> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        LoadDialogUtil.getInstance().dismiss();
    }

    @Override
    public void onError(Throwable e) {
        LoadDialogUtil.getInstance().dismiss();
        if (MyApplication.getInstance()!=null){
            ToastUtil.showToast(MyApplication.getInstance(),ErrorCodeUtil.getException(e));
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
