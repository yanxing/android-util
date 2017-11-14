package com.yanxing.networklibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle.LifecycleProvider;
import com.yanxing.networklibrary.dialog.BaseDialog;
import com.yanxing.networklibrary.dialog.LoadDialog;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 封装RxJava线程切换和进度条部分
 * Created by 李双祥 on 2017/5/23.
 */
public class RxIOHelper<T> {


    /**
     * 封装RxJava线程切换部分，没有进度对话框
     *
     * @param lifecycleProvider RxLifecycle解决RxJava可能导致的内存泄漏
     * @return
     */
    private Observable.Transformer<T, T> iOMainNoProgress(final LifecycleProvider lifecycleProvider) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                Observable<T> observable1
                        = observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                return observable1.compose(lifecycleProvider.<T>bindToLifecycle());
            }
        };
    }

    /**
     * 封装RxJava线程切换部分，默认进度对话框
     *
     * @param lifecycleProvider RxLifecycle解决RxJava可能导致的内存泄漏
     * @param fragmentManager   用于销毁默认进度条Fragment
     * @return
     */
    public Observable.Transformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager) {
        return iOMainHasProgressExecute(lifecycleProvider, fragmentManager, null, null);
    }


    /**
     * 封装RxJava线程切换部分，默认进度对话框
     *
     * @param lifecycleProvider RxLifecycle解决RxJava可能导致的内存泄漏
     * @param fragmentManager   用于销毁默认进度条Fragment
     * @param toast             提示文字
     * @return
     */
    public Observable.Transformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager, String toast) {
        return iOMainHasProgressExecute(lifecycleProvider, fragmentManager, null, toast);
    }

    /**
     * 封装RxJava线程切换部分，有进度对话框
     *
     * @param lifecycleProvider RxLifecycle解决RxJava可能导致的内存泄漏
     * @param fragmentManager   用于销毁默认进度条Fragment
     * @param baseDialog        自定义的进度对话框
     * @return
     */
    public Observable.Transformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager, BaseDialog baseDialog) {
        return iOMainHasProgressExecute(lifecycleProvider, fragmentManager, baseDialog, null);
    }

    private Observable.Transformer<T, T> iOMainHasProgressExecute(final LifecycleProvider lifecycleProvider, final FragmentManager fragmentManager, final BaseDialog baseDialog, final String toast) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                Observable<T> observable1
                        = observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag(LoadDialog.TAG);
                                if (fragment != null) {
                                    fragmentTransaction.remove(fragment).commitAllowingStateLoss();
                                } else {
                                    if (baseDialog == null) {
                                        LoadDialog loadDialog = new LoadDialog();
                                        if (toast != null) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("tip", toast);
                                            loadDialog.setArguments(bundle);
                                        }
                                        loadDialog.show(fragmentTransaction, LoadDialog.TAG);
                                    } else {
                                        baseDialog.show(fragmentTransaction, LoadDialog.TAG);
                                    }
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
                return observable1.compose(lifecycleProvider.<T>bindToLifecycle());
            }
        };
    }
}
