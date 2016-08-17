package com.yanxing.dao;

import com.yanxing.model.DouBan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lishuangxiang on 2016/8/15.
 */
public interface DouBanDao {

    @GET("top250")
    Call<DouBan> getTopMovie(@Query("start") int start, @Query("count") int count);

}
