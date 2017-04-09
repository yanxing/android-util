package com.yanxing.ui;


import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
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

    protected String[] mMonths = new String[]{
            "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01"
    };

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
        dataSet.setLabel("");

        dataSet2.setColor(getResources().getColor(R.color.colorOrange));
        dataSet2.setValueTextColor(getResources().getColor(R.color.colorMainDark));
        dataSet2.setValueTextSize(11);
        dataSet2.setBarShadowColor(getResources().getColor(R.color.colorMainGray));
        dataSet2.setLabel("");

        BarData barData = new BarData(dataSet,dataSet2);
        barData.setBarWidth(0.3f);


        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        });

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(mBarChart);
        mBarChart.setMarker(mv);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.setData(barData);

        Legend l = mBarChart.getLegend();
        l.setEnabled(false);
        l.setDrawInside(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        mBarChart.setTouchEnabled(true);
        mBarChart.setDragDecelerationFrictionCoef(0.9f);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(true);
        mBarChart.groupBars(0f,0.5f,0.1f);
        mBarChart.setHorizontalScrollBarEnabled(true);
        mBarChart.invalidate();

    }

    /**
     * 添加饼图
     */
//    public void addPieChart(){
//        List<PieEntry> pieEntryList=new ArrayList<>();
//        pieEntryList.add(new PieEntry(18.5f, "Green"));
//        pieEntryList.add(new PieEntry(26.7f, "Yellow"));
//        pieEntryList.add(new PieEntry(24.0f, "Red"));
//        pieEntryList.add(new PieEntry(30.8f, "Blue"));
//
//        PieDataSet pieDataSet=new PieDataSet(pieEntryList,"PieChart");
//        pieDataSet.setColors(getResources().getColor(R.color.colorPrimary)
//                ,getResources().getColor(R.color.colorYellow)
//                ,getResources().getColor(R.color.colorRed)
//                ,getResources().getColor(R.color.colorBlue));
//        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
//        pieDataSet.setValueLineColor(getResources().getColor(R.color.white));
//        pieDataSet.setSliceSpace(2);
//        pieDataSet.setValueFormatter(new PercentFormatter());
//        PieData pieData=new PieData();
//        pieData.setDataSet(pieDataSet);
//        mPieChart.setData(pieData);
//        mPieChart.invalidate();
//        mPieChart.setCenterText("yanxing");
//        mPieChart.setCenterTextColor(getResources().getColor(R.color.colorMainDark));
//        Description description=new Description();
//        description.setText("饼图");
//        mPieChart.setDescription(description);
//
//        Legend l = mPieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//    }

    /**
     * 添加饼图
     */
    public void addPieChart(){
        List<PieEntry> pieEntryList=new ArrayList<>();
        pieEntryList.add(new PieEntry(18.5f, "现金大项业绩"));
        pieEntryList.add(new PieEntry(26.7f, "现金小项业绩"));
        pieEntryList.add(new PieEntry(24.0f, "销卡大项业绩"));
        pieEntryList.add(new PieEntry(30.8f, "销卡小项业绩"));

        PieDataSet pieDataSet=new PieDataSet(pieEntryList,"");
        pieDataSet.setColors(getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorYellow)
                ,getResources().getColor(R.color.colorRed)
                ,getResources().getColor(R.color.colorBlue));
//        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
//        pieDataSet.setValueLineColor(getResources().getColor(R.color.white));
        mPieChart.setUsePercentValues(false);
        mPieChart.setHighlightPerTapEnabled(true);
        pieDataSet.setValueFormatter(new PercentFormatter());
        PieData pieData=new PieData();
        pieData.setDataSet(pieDataSet);
        pieData.setValueTextColor(getResources().getColor(R.color.white));
        pieData.setValueTextSize(14);
        mPieChart.setData(pieData);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setDrawEntryLabels(false);//不绘制字体
//        mPieChart.setExtraOffsets(-20, 10, 5, 5);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextColor(getResources().getColor(R.color.colorBlue));
        l.setTextSize(12);
//        l.setXEntrySpace(117f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
        mPieChart.invalidate();
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

    public class MyMarkerView extends MarkerView {

        private TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);

            tvContent = (TextView) findViewById(R.id.tvContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            if (e instanceof CandleEntry) {

                CandleEntry ce = (CandleEntry) e;

                tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
            } else {

                tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
            }

            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }


}
