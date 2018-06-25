package com.yanxing.ui.retrofit;

import com.yanxing.model.Brand;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author 李双祥 on 2018/6/25.
 */
public interface ServiceAPI {

    /**
     *
     * @param lat
     * @param lng
     * @return
     */
    @FormUrlEncoded
    @POST("dtww/brand/findbrandlist")
    Observable<Brand> getBrands(@Field("lat") String lat,@Field("lng") String lng);

}
