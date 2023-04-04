package com.example.bitfit

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bitfit.databinding.DataFragmentBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.lang.Math.round


class DataFragment(var db: DayDatabase, var days: MutableList<DayEntity>) : Fragment() {
    private lateinit var waterAverage : TextView
    private lateinit var ratingAverage : TextView
    private lateinit var chart : LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.data_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        waterAverage = view.findViewById(R.id.water_average)
        var avgWater = 0.0
        for (i in days.indices) {
            avgWater += days[i].oz!!.toDouble()
        }
        avgWater /= days.size
        waterAverage.text = "Average Oz: $avgWater"

        ratingAverage = view.findViewById(R.id.rating_average)
        var avgRating = 0.0
        for (i in days.indices) {
            avgRating += days[i].rating!!.toDouble()
        }
        avgRating /= days.size
        ratingAverage.text = "Average rating: $avgRating"

        chart = view.findViewById(R.id.chart)

        //Styling the chart
        chart.setBackgroundColor(Color.WHITE)
        chart.setTouchEnabled(false)
        chart.setPinchZoom(true)
        chart.setScaleEnabled(true)
        chart.description.isEnabled = false
        chart.isDragEnabled = true
        chart.setDrawGridBackground(false)
        chart.legend.form = Legend.LegendForm.LINE

        setData()
    }

    private fun setData() {
        val waterData = mutableListOf<Entry>()
        for (i in days.indices) {
            waterData.add(Entry(days[i].id.toFloat(), days[i].oz!!.toFloat(), resources.getDrawable(R.drawable.dot)))
        }
        val set = LineDataSet(waterData, "Dataset")

        set.setDrawIcons(false)
        set.color = Color.rgb(65, 63, 120)
        set.setCircleColor(Color.BLACK)

        set.lineWidth = 1f
        set.circleRadius = 3f

        set.setDrawCircleHole(false)

        set.formLineWidth = 1f
        set.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set.formSize = 15f

        set.valueTextSize = 9f

        set.enableDashedHighlightLine(10f, 5f, 0f)

        val sets = mutableListOf<LineDataSet>(set)

        val data = LineData(sets as List<ILineDataSet>?)

        chart.data = data
    }
}