package com.yanxing.networklibrary;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;

import com.yanxing.networklibrary.model.BaseModel;
import com.yanxing.networklibrary.refresh.PullToRefresh;
import com.yanxing.networklibrary.util.ErrorCodeUtil;
import com.yanxing.networklibrary.util.ToastUtil;


/**
 * 根据接口返回状态码预处理，如果onNext方法中接口请求返回成功状态逻辑处理不一样，可重写此方法
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class AbstractObserver<T extends BaseModel> extends BaseAbstractObserver<T> {

    protected AbstractObserver(Context context) {
        super(context);
    }

    protected AbstractObserver(Context context, String message) {
        super(context, message);
    }

    protected AbstractObserver(Context context, boolean isShowToast) {
        super(context, isShowToast);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh) {
        super(context, pullToRefresh);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, String message) {
        super(context, pullToRefresh, message);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, FragmentManager fragmentManager, boolean isShowToast) {
        super(context, pullToRefresh, fragmentManager, isShowToast);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, boolean isShowToast) {
        super(context, pullToRefresh, isShowToast);
    }

    protected AbstractObserver(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager);
    }

    protected AbstractObserver(Context context, FragmentManager fragmentManager, String message) {
        super(context, fragmentManager, message);
    }

    protected AbstractObserver(Context context, FragmentManager fragmentManager, boolean isShowToast) {
        super(context, fragmentManager, isShowToast);
    }

    @Override
    public void onNext(T t) {
        if (ErrorCodeUtil.isSuccess(t.getStatus())) {
            onCall(t);
        } else {
            if (mContext != null && mIsShowToast) {
                if (TextUtils.isEmpty(mMessage)) {
                    ToastUtil.showToast(mContext, TextUtils.isEmpty(t.getMessage()) ? "" : t.getMessage());
                } else {
                    ToastUtil.showToast(mContext, mMessage);
                }
            }
        }
    }
}
