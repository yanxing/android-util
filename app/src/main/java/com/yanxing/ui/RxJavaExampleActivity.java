package com.yanxing.ui;

import android.widget.Button;
import android.widget.TextView;

import com.photo.util.AppUtil;
import com.yanxing.base.BaseActivity;
import com.yanxing.dao.DouBanDao;
import com.yanxing.model.DouBan;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        return R.layout.activity_rejava_example;
    }

    @Override
    protected void afterInstanceView() {
        AppUtil.setStatusBarDarkMode(true,this);

    }

    @OnClick(R.id.get_data)
    public void onClick() {
        getData();
    }

    /**
     * 获取数据
     */
    public void getData(){
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DouBanDao douBanDao = retrofit.create(DouBanDao.class);
        Call<DouBan> call = douBanDao.getTopMovie(0, 10);
        call.enqueue(new Callback<DouBan>() {
            @Override
            public void onResponse(Call<DouBan> call, Response<DouBan> response) {
                DouBan douBan=response.body();
                StringBuilder stringBuilder=new StringBuilder();
                for (int i=0;i<douBan.getSubjects().size();i++){
                    stringBuilder.append(String.valueOf(i));
                    stringBuilder.append(".");
                    stringBuilder.append(douBan.getSubjects().get(i).getTitle());
                    stringBuilder.append("\n");
                }
                mData.setText(stringBuilder.toString());
            }

            @Override
            public void onFailure(Call<DouBan> call, Throwable t) {
                mData.setText(t.getMessage());
            }
        });
    }
}
