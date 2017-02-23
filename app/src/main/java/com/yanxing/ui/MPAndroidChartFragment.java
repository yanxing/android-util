package com.yanxing.ui;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.yanxing.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MPAndroidChart demo
 * Created by lishuangxiang on 2017/2/23.
 */
public class MPAndroidChartFragment extends BaseFragment {

    @BindView(R.id.chart)
    LineChart mLineChart;

    @BindView(R.id.barChart)
    BarChart mBarChart;

    @BindView(R.id.pieChart)
    PieChart mPieChart;

    @BindView(R.id.radarChart)
    RadarChart mRadarChart;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mpandroid_chart;
    }

    @Override
    protected void afterInstanceView() {
        addLineChart();
        addBarChart();
        addPieChart();
        addRadarChart();
    }

    /**
     * 添加折线图
     */
    public void addLineChart(){
        List<Entry> mpPointList=new ArrayList<>();
        for (int i=1;i<5;i++){
            mpPointList.add(new Entry(i,i));
        }
        mpPointList.add(new Entry(5,10));
        mpPointList.add(new Entry(6,7));
        LineDataSet dataSet = new LineDataSet(mpPointList, "LineChart");
        dataSet.setColor(getResources().getColor(R.color.colorRed));
        dataSet.setValueTextColor(getResources().getColor(R.color.colorMainDark));
        dataSet.setCircleColor(getResources().getColor(R.color.colorRed));
        dataSet.setLineWidth(1);
        dataSet.setValueTextSize(11);
        dataSet.setLabel("LineDataSet");
        LineData lineData = new LineData(dataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
        Description description=new Description();
        description.setText("折线图");
        mLineChart.setDescription(description);
    }

    /**
     * 添加柱状图
     */
    public void addBarChart(){
        List<BarEntry> mpPointList=new ArrayList<>();
        List<BarEntry> mpPointList2=new ArrayList<>();
        mpPointList.add(new BarEntry(0.7f,10));
        mpPointList.add(new BarEntry(2,7));
        mpPointList.add(new BarEntry(3,10));
        mpPointList.add(new BarEntry(4,7));

        mpPointList2.add(new BarEntry(0.7f,2));
        mpPointList2.add(new BarEntry(2,10));
        mpPointList2.add(new BarEntry(3,8));
        mpPointList2.add(new BarEntry(4,8));
        BarDataSet dataSet = new BarDataSet(mpPointList, "BarChart");
        BarDataSet dataSet2 = new BarDataSet(mpPointList2, "Group 2");
        dataSet.setColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setValueTextColor(getResources().getColor(R.color.colorMainDark));
        dataSet.setValueTextSize(11);
        dataSet.setBarShadowColor(getResources().getColor(R.color.colorMainGray));
        dataSet.setLabel("BarDataSet");

        dataSet2.setColor(getResources().getColor(R.color.colorOrange));
        dataSet2.setValueTextColor(getResources().getColor(R.color.colorMainDark));
        dataSet2.setValueTextSize(11);
        dataSet2.setBarShadowColor(getResources().getColor(R.color.colorMainGray));
        dataSet2.setLabel("BarDataSet2");

        BarData barData = new BarData(dataSet,dataSet2);
        barData.setBarWidth(0.4f);
        mBarChart.setData(barData);
        mBarChart.invalidate();
        Description description=new Description();
        description.setText("柱状图");
        mBarChart.setDescription(description);
        mBarChart.groupBars(0.4f,0.1f,0.0f);
    }

    /**
     * 添加饼图
     */
    public void addPieChart(){
        List<PieEntry> pieEntryList=new ArrayList<>();
        pieEntryList.add(new PieEntry(18.5f, "Green"));
        pieEntryList.add(new PieEntry(26.7f, "Yellow"));
        pieEntryList.add(new PieEntry(24.0f, "Red"));
        pieEntryList.add(new PieEntry(30.8f, "Blue"));

        PieDataSet pieDataSet=new PieDataSet(pieEntryList,"PieChart");
        pieDataSet.setColors(getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorYellow)
                ,getResources().getColor(R.color.colorRed)
                ,getResources().getColor(R.color.colorBlue));
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieDataSet.setValueLineColor(getResources().getColor(R.color.white));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        PieData pieData=new PieData();
        pieData.setDataSet(pieDataSet);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
        mPieChart.setCenterText("yanxing");
        mPieChart.setCenterTextColor(getResources().getColor(R.color.colorMainDark));
        Description description=new Description();
        description.setText("饼图");
        mPieChart.setDescription(description);
    }

    /**
     * 雷达图表
     */
    public void addRadarChart(){
        List<RadarEntry> radarEntries=new ArrayList<>();
        radarEntries.add(new RadarEntry(6f));
        radarEntries.add(new RadarEntry(5f));
        radarEntries.add(new RadarEntry(6f));
        radarEntries.add(new RadarEntry(9f));
        radarEntries.add(new RadarEntry(10f));

        RadarDataSet radarDataSet=new RadarDataSet(radarEntries,"RadarChart");
        RadarData radarData=new RadarData(radarDataSet);
        radarData.setValueTextColor(getResources().getColor(R.color.colorRed));
        radarDataSet.setValueTextSize(11);
        mRadarChart.setData(radarData);
        mRadarChart.invalidate();
        Description description=new Description();
        description.setText("雷达图");
        mRadarChart.setDescription(description);
    }

}
