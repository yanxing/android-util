package com.yanxing.ui;

import android.widget.Button;
import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.networklibrary.RetrofitManage;
import com.yanxing.networklibrary.RxIOHelper;
import com.yanxing.networklibrary.RxSubscriberHelper;
import com.yanxing.ui.retrofit.DouBanAPI;
import com.yanxing.model.DouBan;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Networklibrary 例子
 * Created by lishuangxiang on 2016/5/9.
 */
public class NetworkLibraryFragment extends BaseFragment {

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
        RetrofitManage.getInstance().init(baseUrl,true);
        RetrofitManage.getInstance().getRetrofit().create(DouBanAPI.class)
                .getTopMovie(0,10)
                .compose(new RxIOHelper<DouBan>().iOMainHasProgress(this,getFragmentManager(),"请稍等..."))
                .subscribe(new RxSubscriberHelper<DouBan>(getActivity(),getFragmentManager()) {
                    @Override
                    public void onCall(DouBan douBan) {

                    }

                    @Override
                    public void onNext(DouBan douBan) {
                        super.onNext(douBan);
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
