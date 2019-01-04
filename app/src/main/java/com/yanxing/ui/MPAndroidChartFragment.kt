package com.yanxing.ui


import android.content.Context
import android.widget.TextView

import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.yanxing.base.BaseFragment

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_mpandroid_chart.*

/**
 * MPAndroidChart demo
 * Created by lishuangxiang on 2017/2/23.
 */
class MPAndroidChartFragment : BaseFragment() {





    protected var mMonths = arrayOf("03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01", "03-01")

    override fun getLayoutResID(): Int {
        return R.layout.fragment_mpandroid_chart
    }

    override fun afterInstanceView() {
        addLineChart()
        addBarChart()
        addPieChart()
        addRadarChart()
    }

    /**
     * 添加折线图
     */
    fun addLineChart() {
        val mpPointList = ArrayList<Entry>()
        for (i in 1..4) {
            mpPointList.add(Entry(i.toFloat(), i.toFloat()))
        }
        mpPointList.add(Entry(5f, 10f))
        mpPointList.add(Entry(6f, 7f))
        val dataSet = LineDataSet(mpPointList, "LineChart")
        dataSet.color = resources.getColor(R.color.colorRed)
        dataSet.valueTextColor = resources.getColor(R.color.colorMainDark)
        dataSet.setCircleColor(resources.getColor(R.color.colorRed))
        dataSet.lineWidth = 1f
        dataSet.valueTextSize = 11f
        dataSet.label = "LineDataSet"
        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()
        val description = Description()
        description.text = "折线图"
        chart.description = description
    }

    /**
     * 添加柱状图
     */
    fun addBarChart() {
        val mpPointList = ArrayList<BarEntry>()
        val mpPointList2 = ArrayList<BarEntry>()
        mpPointList.add(BarEntry(0.7f, 10f))
        mpPointList.add(BarEntry(2f, 7f))
        mpPointList.add(BarEntry(3f, 10f))
        mpPointList.add(BarEntry(4f, 7f))

        mpPointList2.add(BarEntry(0.7f, 2f))
        mpPointList2.add(BarEntry(2f, 10f))
        mpPointList2.add(BarEntry(3f, 8f))
        mpPointList2.add(BarEntry(4f, 8f))
        val dataSet = BarDataSet(mpPointList, "BarChart")
        val dataSet2 = BarDataSet(mpPointList2, "Group 2")
        dataSet.color = resources.getColor(R.color.colorPrimary)
        dataSet.valueTextColor = resources.getColor(R.color.colorMainDark)
        dataSet.valueTextSize = 11f
        dataSet.barShadowColor = resources.getColor(R.color.colorMainGray)
        dataSet.label = ""

        dataSet2.color = resources.getColor(R.color.colorOrange)
        dataSet2.valueTextColor = resources.getColor(R.color.colorMainDark)
        dataSet2.valueTextSize = 11f
        dataSet2.barShadowColor = resources.getColor(R.color.colorMainGray)
        dataSet2.label = ""

        val barData = BarData(dataSet, dataSet2)
        barData.barWidth = 0.3f


        val xAxis = barChart.xAxis
        xAxis.isEnabled = false
        xAxis.valueFormatter = IAxisValueFormatter { value, axis -> mMonths[value.toInt() % mMonths.size] }

        val mv = MyMarkerView(activity!!, R.layout.custom_marker_view)
        mv.chartView = barChart
        barChart.marker = mv

        barChart.description.isEnabled = false
        barChart.data = barData

        val l = barChart.legend
        l.isEnabled = false
        l.setDrawInside(false)
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.textSize = 8f
        barChart.setTouchEnabled(true)
        barChart.dragDecelerationFrictionCoef = 0.9f
        barChart.isDragEnabled = true
        barChart.setScaleEnabled(true)
        barChart.groupBars(0f, 0.5f, 0.1f)
        barChart.isHorizontalScrollBarEnabled = true
        barChart.invalidate()

    }

    /**
     * 添加饼图
     */
    fun addPieChart() {
        val pieEntryList = ArrayList<PieEntry>()
        pieEntryList.add(PieEntry(18.5f, "现金大项业绩"))
        pieEntryList.add(PieEntry(26.7f, "现金小项业绩"))
        pieEntryList.add(PieEntry(24.0f, "销卡大项业绩"))
        pieEntryList.add(PieEntry(30.8f, "销卡小项业绩"))

        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.setColors(resources.getColor(R.color.colorPrimary), resources.getColor(R.color.colorYellow)
                , resources.getColor(R.color.colorRed), resources.getColor(R.color.colorBlue))
        pieChart.setUsePercentValues(false)
        pieChart.isHighlightPerTapEnabled = true
        pieDataSet.valueFormatter = PercentFormatter()
        val pieData = PieData()
        pieData.dataSet = pieDataSet
        pieData.setValueTextColor(resources.getColor(R.color.white))
        pieData.setValueTextSize(14f)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = false
        pieChart.setDrawEntryLabels(false)//不绘制字体

        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.textColor = resources.getColor(R.color.colorBlue)
        l.textSize = 12f
        radarChart.invalidate()
    }

    /**
     * 雷达图表
     */
    fun addRadarChart() {
        val radarEntries = ArrayList<RadarEntry>()
        radarEntries.add(RadarEntry(6f))
        radarEntries.add(RadarEntry(5f))
        radarEntries.add(RadarEntry(6f))
        radarEntries.add(RadarEntry(9f))
        radarEntries.add(RadarEntry(10f))

        val radarDataSet = RadarDataSet(radarEntries, "RadarChart")
        val radarData = RadarData(radarDataSet)
        radarData.setValueTextColor(resources.getColor(R.color.colorRed))
        radarDataSet.valueTextSize = 11f
        radarChart.data = radarData
        radarChart.invalidate()
        val description = Description()
        description.text = "雷达图"
        radarChart.description = description
    }

    inner class MyMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

        private val tvContent: TextView

        init {

            tvContent = findViewById<TextView>(R.id.tvContent)
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        override fun refreshContent(e: Entry?, highlight: Highlight?) {

            if (e is CandleEntry) {

                val ce = e as CandleEntry?

                tvContent.text = "" + Utils.formatNumber(ce!!.high, 0, true)
            } else {

                tvContent.text = "" + Utils.formatNumber(e!!.y, 0, true)
            }

            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
    }


}
