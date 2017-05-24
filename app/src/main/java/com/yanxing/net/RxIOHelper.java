package com.yanxing.net;

import android.content.Context;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.yanxing.util.LoadDialogUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 封装RxJava线程和进度条部分
 * Created by 李双祥 on 2017/5/23.
 */
public class RxIOHelper<T> {

    /**
     * 封装Rx线程切换部分，没有进度条
     *
     * @param context RxAppCompatActivity
     * @return Observable
     */
    public Observable.Transformer<T, T> iOMainNoProgress(final RxFragment context) {
        return iOMainNoProgressExecute(context);
    }

    /**
     * 封装Rx线程切换部分，没有进度条
     *
     * @param context RxAppCompatActivity
     * @return Observable
     */
    public Observable.Transformer<T, T> iOMainNoProgress(final RxAppCompatActivity context) {
        return iOMainNoProgressExecute(context);
    }

    private Observable.Transformer<T, T> iOMainNoProgressExecute(final Object context) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                Observable<T> observable1
                        = observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                if (context instanceof RxAppCompatActivity) {
                    return observable1.compose(((RxAppCompatActivity) context).<T>bindToLifecycle());
                } else if (context instanceof RxFragment) {
                    return observable1.compose(((RxFragment) context).<T>bindToLifecycle());
                } else {
                    return observable1;
                }
            }
        };
    }

    /**
     * 封装Rx线程切换部分，有进度条
     *
     * @param context RxFragment
     * @param toast   提示文字，null使用默认提示文字
     * @return Observable
     */
    public Observable.Transformer<T, T> iOMainHasProgress(final RxFragment context, final String toast) {
        return iOMainHasProgressExecute(context, context.getActivity(), toast);
    }

    /**
     * 封装Rx线程切换部分，有进度条
     *
     * @param context RxAppCompatActivity
     * @param toast   提示文字，null使用默认提示文字
     * @return Observable
     */
    public Observable.Transformer<T, T> iOMainHasProgress(final RxAppCompatActivity context, final String toast) {
        return iOMainHasProgressExecute(context, context.getApplicationContext(), toast);
    }

    private Observable.Transformer<T, T> iOMainHasProgressExecute(final Object object, final Context context, final String toast) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                Observable<T> observable1
                        = observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                LoadDialogUtil.getInstance().showLoadingDialog(context, toast);
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
                if (object instanceof RxAppCompatActivity) {
                    return observable1.compose(((RxAppCompatActivity) object).<T>bindToLifecycle());
                } else if (object instanceof RxFragment) {
                    return observable1.compose(((RxFragment) object).<T>bindToLifecycle());
                } else {
                    return observable1;
                }
            }
        };
    }
}
