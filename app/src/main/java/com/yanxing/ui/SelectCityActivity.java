package com.yanxing.ui;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.model.Area;
import com.yanxing.util.ParseJson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择城市
 * Created by lishuangxiang on 2016/4/12.
 */
public class SelectCityActivity extends BaseActivity
        implements AdapterView.OnItemClickListener{

    @BindView(R.id.province)
    ListView mProvince;

    @BindView(R.id.city)
    ListView mCity;

    //省份
    private ProvinceAdapter mProvinceAdapter;
    //市
    private CommonAdapter<Area.CityBean> mCityAdapter;
    private List<Area> mAreaList=new ArrayList<Area>();
    private static int mIndex=0;
    private boolean mIsClick=false;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void afterInstanceView() {
        //构造当前地区
        Area currentArea=new Area();
        currentArea.setName(getString(R.string.current_area));
        ArrayList<Area.CityBean> currentCity=new ArrayList<Area.CityBean>();
        Area.CityBean cityBean=new Area.CityBean();
        String currentCityStr=getIntent().getStringExtra("currentCity");
        cityBean.setName(currentCityStr);
        currentCity.add(cityBean);
        currentArea.setCity(currentCity);
        mAreaList.add(currentArea);
        mAreaList.addAll(ParseJson.getArea(getApplicationContext()));
        mProvinceAdapter=new ProvinceAdapter();
        mProvince.setAdapter(mProvinceAdapter);
        mProvince.setOnItemClickListener(this);
        new Handler().postDelayed(() -> {
            //选中第一项
            mProvince.performItemClick(mProvince.getChildAt(0),0,mProvince.getItemIdAtPosition(0));
        },700);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mIsClick=true;
        mIndex=position;
        for (int i=0;i<mProvince.getChildCount();i++){
            if (mIndex!=i){
                View view1=mProvince.getChildAt(i);
                view1.findViewById(R.id.current).setVisibility(View.GONE);
            }
        }
        view.findViewById(R.id.current).setVisibility(View.VISIBLE);
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

    /**
     * 城市适配器，view布局不重用
     */
    private class ProvinceAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mAreaList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAreaList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_province,null);
            TextView province= (TextView) convertView.findViewById(R.id.province);
            province.setText(mAreaList.get(position).getName());
            if (mIsClick){
                if (position==mIndex){
                    convertView.findViewById(R.id.current).setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }
    }
}
