package com.yanxing.networklibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.yanxing.networklibrary.dialog.BaseDialog;
import com.yanxing.networklibrary.dialog.LoadDialog;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;


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
    private ObservableTransformer<T, T> iOMainNoProgress(final LifecycleProvider lifecycleProvider) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                Observable<T> observable1
                        = observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                return observable1.compose(lifecycleProvider.bindToLifecycle());
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
    public ObservableTransformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager) {
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
    public ObservableTransformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager, String toast) {
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
    public ObservableTransformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager, BaseDialog baseDialog) {
        return iOMainHasProgressExecute(lifecycleProvider, fragmentManager, baseDialog, null);
    }

    private ObservableTransformer iOMainHasProgressExecute(final LifecycleProvider lifecycleProvider, final FragmentManager fragmentManager, final BaseDialog baseDialog, final String toast) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                Observable<T> observable1
                        = observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag(LoadDialog.TAG);
                                if (fragment != null) {
                                    if (baseDialog!=null){
                                        fragmentTransaction.remove(fragment).commitAllowingStateLoss();
                                        baseDialog.show(fragmentTransaction, LoadDialog.TAG);
                                    }
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
