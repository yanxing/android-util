package com.yanxing.ui;

import android.widget.Button;
import android.widget.TextView;

import com.photo.util.AppUtil;
import com.yanxing.base.BaseActivity;
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
 * RxJava使用
 * Created by lishuangxiang on 2016/5/9.
 */
public class RxJavaExampleActivity extends BaseActivity {

    @BindView(R.id.get_data)
    Button mGetData;

    @BindView(R.id.data)
    TextView mData;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_rxjava_example;
    }

    @Override
    protected void afterInstanceView() {
        AppUtil.setStatusBarDarkMode(true, this);

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
