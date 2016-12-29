package com.yanxing.ui;

import android.widget.Button;
import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.dao.DouBanDao;
import com.yanxing.model.DouBan;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava使用 更多例子 https://github.com/cn-ljb/rxjava_for_android
 * http://gank.io/post/560e15be2dca930e00da1083
 * Created by lishuangxiang on 2016/5/9.
 */
public class RxJavaFragment extends BaseFragment {

    @BindView(R.id.get_data)
    Button mGetData;

    @BindView(R.id.data)
    TextView mData;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_rxjava;
    }

    @Override
    protected void afterInstanceView() {
    }

    @OnClick(R.id.get_data)
    public void onClick() {
        getData();
    }

    /**
     * 获取数据
     */
    public void getData() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DouBanDao douBanDao = retrofit.create(DouBanDao.class);
        douBanDao.getTopMovie(0, 10)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DouBan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DouBan douBan) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < douBan.getSubjects().size(); i++) {
                            stringBuilder.append(String.valueOf(i));
                            stringBuilder.append(".");
                            stringBuilder.append(douBan.getSubjects().get(i).getTitle());
                            stringBuilder.append("\n");
                        }
                        mData.setText(stringBuilder.toString());
                    }
                });
    }
}
