package com.yanxing.networklibrary;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;

import com.yanxing.networklibrary.dialog.LoadDialog;
import com.yanxing.networklibrary.refresh.PullToRefresh;
import com.yanxing.networklibrary.util.ErrorCodeUtil;
import com.yanxing.networklibrary.util.LogUtil;
import com.yanxing.networklibrary.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 统一处理onCompleted onError方法
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class BaseAbstractObserver<T> implements Observer<T> {

    protected PullToRefresh mPullToRefresh;
    protected FragmentManager mFragmentManager;
    protected Context mContext;
    /**
     * 自定义的错误提示，替代接口返回的错误信息，不替换onError方法里面的报错信息显示
     */
    protected String mMessage;
    /**
     * 是否显示错误信息提示，默认提示
     */
    protected boolean mIsShowToast = true;

    protected BaseAbstractObserver(Context context) {
        this(context, true);
    }

    /**
     * @param context
     * @param message 自定义错误信息，接口报错生效
     */
    protected BaseAbstractObserver(Context context, String message) {
        this.mContext = context;
        this.mMessage = message;
    }

    protected BaseAbstractObserver(Context context, boolean isShowToast) {
        this(context, null, null, isShowToast);
    }

    /**
     * 含有刷新组件，请求结束完成刷新状态
     *
     * @param pullToRefresh
     */
    protected BaseAbstractObserver(Context context, PullToRefresh pullToRefresh) {
        this(context, pullToRefresh, true);
    }

    /**
     * @param pullToRefresh 用来结束掉刷新状态
     * @param message       自定义错误信息，接口报错生效
     */
    protected BaseAbstractObserver(Context context, PullToRefresh pullToRefresh, String message) {
        this.mPullToRefresh = pullToRefresh;
        this.mContext = context;
        this.mMessage = message;
    }

    /**
     * @param pullToRefresh 用来结束掉刷新状态
     * @param isShowToast   true显示错误信息
     */
    protected BaseAbstractObserver(Context context, PullToRefresh pullToRefresh, FragmentManager fragmentManager, boolean isShowToast) {
        this.mPullToRefresh = pullToRefresh;
        this.mContext = context;
        this.mIsShowToast = isShowToast;
        this.mFragmentManager = fragmentManager;
    }

    /**
     * @param pullToRefresh 用来结束掉刷新状态
     * @param isShowToast   true显示错误信息
     */
    protected BaseAbstractObserver(Context context, PullToRefresh pullToRefresh, boolean isShowToast) {
        this(context, pullToRefresh, null, isShowToast);
    }

    /**
     * @param fragmentManager 用来请求结束，移除对话框
     */
    protected BaseAbstractObserver(Context context, FragmentManager fragmentManager) {
        this(context, fragmentManager, true);
    }

    /**
     * @param fragmentManager 用来请求结束，移除对话框
     * @param message         自定义错误信息，接口报错生效
     */
    protected BaseAbstractObserver(Context context, FragmentManager fragmentManager, String message) {
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mMessage = message;
    }

    /**
     * @param fragmentManager 用来请求结束，移除对话框
     */
    protected BaseAbstractObserver(Context context, FragmentManager fragmentManager, boolean isShowToast) {
        this(context, null, fragmentManager, isShowToast);
    }

    @Override
    public void onComplete() {
        if (mPullToRefresh != null) {
            mPullToRefresh.refreshComplete();
        }
        if (mFragmentManager != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
            if (fragment != null) {
                //移除正在显示的对话框
                try {
                    fragmentTransaction.remove(fragment).commitNowAllowingStateLoss();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        //打印具体的错误信息
        LogUtil.e(getClass().getName(), Log.getStackTraceString(e));
        if (mContext != null && mIsShowToast) {
            ToastUtil.showToast(mContext, ErrorCodeUtil.getException(e));
        }
        if (mPullToRefresh != null) {
            mPullToRefresh.refreshComplete();
        }
        if (mFragmentManager != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
            if (fragment != null) {
                try {
                    fragmentTransaction.remove(fragment).commitNowAllowingStateLoss();
                } catch (Exception e1) {
                }
            }
        }
    }

    @Override
    public void onNext(T t) {
        onCall(t);
    }

    /**
     * 请求成功返回调用
     *
     * @param t
     */
    public abstract void onCall(T t);

    @Override
    public void onSubscribe(@NonNull Disposable var1) {

    }

}
