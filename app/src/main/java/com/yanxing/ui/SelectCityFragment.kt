package com.yanxing.ui

import android.os.Handler
import android.view.View
import android.widget.AdapterView

import com.yanxing.base.BaseFragment
import com.yanxing.commonlibrary.adapter.CommonAdapter
import com.yanxing.commonlibrary.adapter.ViewHolder
import com.yanxing.model.Area
import com.yanxing.util.ParseJsonUtil

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_select_city.*

/**
 * 选择城市
 * Created by lishuangxiang on 2016/4/12.
 */
class SelectCityFragment : BaseFragment(), AdapterView.OnItemClickListener {

    //省份
    private var mProvinceAdapter: CommonAdapter<Area>? = null
    //市
    private var mCityAdapter: CommonAdapter<Area.CityBean>? = null
    private val mAreaList = ArrayList<Area>()
    /**
     * 记录上次选择的省索引
     */
    private var mIndex = 0

    override fun getLayoutResID(): Int {
        return R.layout.fragment_select_city
    }

    override fun afterInstanceView() {
        //构造当前地区
        val currentArea = Area()
        currentArea.name = getString(R.string.current_area)
        val currentCity = ArrayList<Area.CityBean>()
        val cityBean = Area.CityBean()
        val currentCityStr = arguments?.getString("currentCity")
        cityBean.name = currentCityStr
        currentCity.add(cityBean)
        currentArea.city = currentCity
        mAreaList.add(currentArea)
        mAreaList.addAll(ParseJsonUtil.getArea(activity))
        mProvinceAdapter = object : CommonAdapter<Area>(mAreaList, R.layout.adapter_province) {
            override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
                if (mDataList[position].isCheck) {
                    viewHolder.findViewById<View>(R.id.current).visibility = View.VISIBLE
                } else {
                    viewHolder.findViewById<View>(R.id.current).visibility = View.GONE
                }
                viewHolder.setText(R.id.province, mDataList[position].name)
            }
        }
        province.adapter = mProvinceAdapter
        province.onItemClickListener = this
        Handler().postDelayed({ province.performItemClick(province.getChildAt(0), 0, province.getItemIdAtPosition(0)) }, 700)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        mAreaList[mIndex].isCheck = false
        mAreaList[position].isCheck = true
        mIndex = position
        mProvinceAdapter!!.update(mAreaList)
        if (mCityAdapter == null) {
            mCityAdapter = object : CommonAdapter<Area.CityBean>(mAreaList[0].city, R.layout.adapter_city) {
                override fun onBindViewHolder(holder: ViewHolder, index: Int) {
                    if (mIndex == 0) {
                        holder.findViewById<View>(R.id.current).visibility = View.VISIBLE
                    } else {
                        holder.findViewById<View>(R.id.current).visibility = View.GONE
                    }
                    holder.setText(R.id.city, mDataList[index].name)
                }
            }
            city.adapter = mCityAdapter
        } else {
            mCityAdapter!!.update(mAreaList[mIndex].city)
        }
    }
}
