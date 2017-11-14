package com.yanxing.ui.retrofit;

import com.yanxing.model.DouBan;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lishuangxiang on 2016/8/15.
 */
public interface DouBanAPI {

    @GET("top250")
    Observable<DouBan> getTopMovie(@Query("start") int start, @Query("count") int count);

}
