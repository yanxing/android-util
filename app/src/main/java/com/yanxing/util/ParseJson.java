package com.yanxing.util;

import android.content.Context;

import com.google.gson.Gson;
import com.yanxing.model.Area;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析json
 * Created by lishuangxiang on 2016/4/8.
 */
public class ParseJson {


    /**
     * 解析省市区数据
     * @param context
     */
    public static List<Area> getArea(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStream inputStream = context.getAssets().open("city.json");
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                stringBuffer.append(new String(buffer, 0, len, "UTF-8"));
            }
            inputStream.close();
            String content = stringBuffer.toString();
            Gson gson=new Gson();
            JSONArray jsonArray=new JSONArray(content);
            List<Area> areaList=new ArrayList<Area>();
            for (int i=0;i<jsonArray.length();i++){
                areaList.add(gson.fromJson(jsonArray.getString(i),Area.class));
            }
            return areaList;
        } catch (Exception e) {

        }
        return null;
    }
}
