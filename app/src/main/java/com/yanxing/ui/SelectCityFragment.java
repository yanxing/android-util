package com.yanxing.ui;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseFragment;
import com.yanxing.model.Area;
import com.yanxing.util.ParseJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择城市
 * Created by lishuangxiang on 2016/4/12.
 */
public class SelectCityFragment extends BaseFragment
        implements AdapterView.OnItemClickListener{

    @BindView(R.id.province)
    ListView mProvince;

    @BindView(R.id.city)
    ListView mCity;

    //省份
    private CommonAdapter<Area> mProvinceAdapter;
    //市
    private CommonAdapter<Area.CityBean> mCityAdapter;
    private List<Area> mAreaList=new ArrayList<Area>();
    /**
     * 记录上次选择的省索引
     */
    private int mIndex=0;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_select_city;
    }

    @Override
    protected void afterInstanceView() {
        //构造当前地区
        Area currentArea=new Area();
        currentArea.setName(getString(R.string.current_area));
        ArrayList<Area.CityBean> currentCity=new ArrayList<Area.CityBean>();
        Area.CityBean cityBean=new Area.CityBean();
        String currentCityStr=getArguments().getString("currentCity");
        cityBean.setName(currentCityStr);
        currentCity.add(cityBean);
        currentArea.setCity(currentCity);
        mAreaList.add(currentArea);
        mAreaList.addAll(ParseJsonUtil.getArea(getActivity()));
        mProvinceAdapter=new CommonAdapter<Area>(mAreaList,R.layout.adapter_province) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                if (mAreaList.get(position).isCheck()){
                    viewHolder.findViewById(R.id.current).setVisibility(View.VISIBLE);
                }else {
                    viewHolder.findViewById(R.id.current).setVisibility(View.GONE);
                }
                viewHolder.setText(R.id.province,mAreaList.get(position).getName());
            }
        };
        mProvince.setAdapter(mProvinceAdapter);
        mProvince.setOnItemClickListener(this);
        new Handler().postDelayed(() -> {
            //选中第一项
            mProvince.performItemClick(mProvince.getChildAt(0),0,mProvince.getItemIdAtPosition(0));
        },700);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAreaList.get(mIndex).setCheck(false);
        mAreaList.get(position).setCheck(true);
        mIndex=position;
        mProvinceAdapter.update(mAreaList);
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
