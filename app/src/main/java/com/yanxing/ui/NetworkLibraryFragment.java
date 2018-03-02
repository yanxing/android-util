package com.yanxing.ui;

import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.networklibrary.RetrofitManage;
import com.yanxing.networklibrary.Transformer;
import com.yanxing.networklibrary.AbstractObserver;
import com.yanxing.ui.retrofit.DouBanAPI;
import com.yanxing.model.DouBan;

import butterknife.BindView;

/**
 * Networklibrary 例子
 * Created by lishuangxiang on 2016/5/9.
 */
public class NetworkLibraryFragment extends BaseFragment {

    @BindView(R.id.data)
    TextView mData;

    private String mBaseUrl = "https://api.douban.com/v2/movie/";

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_rxjava;
    }

    @Override
    protected void afterInstanceView() {
        RetrofitManage.getInstance().init(mBaseUrl,true);
        getData();
    }

    /**
     * 获取电影排行
     */
    public void getData() {
        RetrofitManage.getInstance().getRetrofit().create(DouBanAPI.class)
                .getTopMovie(0,10)
                .compose(new Transformer<DouBan>().iOMainHasProgress(this,getFragmentManager(),"请稍等..."))
                .subscribe(new AbstractObserver<DouBan>(getActivity(),getFragmentManager(),false) {
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
