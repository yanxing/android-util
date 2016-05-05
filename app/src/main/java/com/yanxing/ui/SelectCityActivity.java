package com.yanxing.ui;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.Area;
import com.yanxing.util.ParseJson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市
 * Created by lishuangxiang on 2016/4/12.
 */
@EActivity(R.layout.activity_select_city)
public class SelectCityActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @ViewById(R.id.province)
    ListView mProvince;

    @ViewById(R.id.city)
    ListView mCity;

    //省份
    private CommonAdapter<Area> mProvinceAdapter;
    //市
    private CommonAdapter<Area.CityBean> mCityAdapter;
    private List<Area> mAreaList=new ArrayList<Area>();
    private static int mIndex=0;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        //构造当前地区
        Area currentArea=new Area();
        currentArea.setName("当前地区");
        ArrayList<Area.CityBean> currentCity=new ArrayList<Area.CityBean>();
        Area.CityBean cityBean=new Area.CityBean();
        String currentCityStr=getIntent().getStringExtra("currentCity");
        cityBean.setName(currentCityStr);
        currentCity.add(cityBean);
        currentArea.setCity(currentCity);
        mAreaList.add(currentArea);
        //构造热门地区
        Area hotArea=new Area();
        hotArea.setName("热门地区");
        ArrayList<Area.CityBean> hotCity=new ArrayList<Area.CityBean>();
        Area.CityBean cityBean1=new Area.CityBean();
        cityBean1.setName("上海");
        Area.CityBean cityBean2=new Area.CityBean();
        cityBean2.setName("北京");
        Area.CityBean cityBean3=new Area.CityBean();
        cityBean3.setName("广州");
        Area.CityBean cityBean4=new Area.CityBean();
        cityBean4.setName("深圳");
        hotCity.add(cityBean1);
        hotCity.add(cityBean2);
        hotCity.add(cityBean3);
        hotCity.add(cityBean4);
        hotArea.setCity(hotCity);
        mAreaList.add(hotArea);
        mAreaList.addAll(ParseJson.getArea(getApplicationContext()));
        mProvinceAdapter=new CommonAdapter<Area>(mAreaList,R.layout.adapter_province) {

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.setText(R.id.province,mAreaList.get(position).getName());
//                holder.findViewById(R.id.current).setVisibility(View.GONE);
            }

        };
        mProvince.setAdapter(mProvinceAdapter);
        mProvince.setOnItemClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //选中第一项
                mProvince.performItemClick(mProvince.getChildAt(0),0,mProvince.getItemIdAtPosition(0));
            }
        },500);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i=0;i<mProvince.getChildCount();i++){
            if (position!=i){
                View view1=mProvince.getChildAt(i);
                view1.findViewById(R.id.current).setVisibility(View.GONE);
            }
        }
        view.findViewById(R.id.current).setVisibility(View.VISIBLE);
        mIndex=position;
        if (mCityAdapter==null){
            mCityAdapter=new CommonAdapter<Area.CityBean>(mAreaList.get(0).getCity(),R.layout.adapter_city) {
                @Override
                public void onBindViewHolder(ViewHolder holder, int index) {
                    if (mIndex==0){
                        holder.findViewById(R.id.current).setVisibility(View.VISIBLE);
                    }else {
                        holder.findViewById(R.id.current).setVisibility(View.GONE);
                    }
                    holder.setText(R.id.city,mAreaList.get(mIndex).getCity().get(index).getName());
                }
            };
            mCity.setAdapter(mCityAdapter);
        }else {
            mCityAdapter.update(mAreaList.get(mIndex).getCity());
        }
    }
}
