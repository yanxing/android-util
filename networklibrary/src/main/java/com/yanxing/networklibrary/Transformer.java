package com.yanxing.networklibrary;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
 * 封装RxJava线程切换（任务在子线程，回调在主线程）和进度条部分
 * Created by 李双祥 on 2017/5/23.
 */
public class Transformer<T> {

    /**
     * 没有进度对话框
     *
     * @param lifecycleProvider RxLifecycle解决RxJava可能导致的内存泄漏
     * @return
     */
    public ObservableTransformer<T, T> iOMainNoProgress(final LifecycleProvider lifecycleProvider) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycleProvider.bindToLifecycle());
            }
        };
    }

    /**
     * 默认进度对话框
     *
     * @param lifecycleProvider RxLifecycle解决RxJava可能导致的内存泄漏
     * @param fragmentManager   用于销毁默认进度条Fragment
     * @return
     */
    public ObservableTransformer<T, T> iOMainHasProgress(final LifecycleProvider lifecycleProvider, FragmentManager fragmentManager) {
        return iOMainHasProgressExecute(lifecycleProvider, fragmentManager, null, null);
    }


    /**
     * 默认进度对话框
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
     * 有进度对话框
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
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                try {
                                    if (fragmentManager == null) {
                                        return;
                                    }
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    Fragment fragment = fragmentManager.findFragmentByTag(LoadDialog.TAG);
                                    if (fragment != null) {
                                        if (baseDialog != null) {
                                            fragmentTransaction.remove(fragment).commitAllowingStateLoss();
                                            baseDialog.show(fragmentTransaction, LoadDialog.TAG);
                                        }
                                    } else {
                                        if (baseDialog == null) {
                                            LoadDialog loadDialog = new LoadDialog();
                                            if (toast != null) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString(LoadDialog.ARGUMENT_KEY, toast);
                                                loadDialog.setArguments(bundle);
                                            }
                                            loadDialog.show(fragmentTransaction, LoadDialog.TAG);
                                        } else {
                                            baseDialog.show(fragmentTransaction, LoadDialog.TAG);
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycleProvider.<T>bindToLifecycle());
            }
        };
    }
}
