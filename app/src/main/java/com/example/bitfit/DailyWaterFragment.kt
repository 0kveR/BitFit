package com.example.bitfit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.DailyWaterFragmentBinding
import java.util.*

class DailyWaterFragment(var db: DayDatabase, var days: MutableList<DayEntity>) : Fragment() {
    private lateinit var waterbar: SeekBar
    private lateinit var ratingbar: SeekBar
    private lateinit var oz: TextView
    private lateinit var rating: TextView
    private lateinit var daysRV: RecyclerView
    private lateinit var addData: Button
    val TAG = "DAILY WATER FRAGMENT/"

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
        val view = inflater.inflate(R.layout.daily_water_fragment, container, false)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        waterbar = view.findViewById(R.id.waterbar)
        ratingbar = view.findViewById(R.id.ratebar)
        oz = view.findViewById(R.id.oz_count)
        rating = view.findViewById(R.id.rating_count)
        daysRV = view.findViewById(R.id.days_RV)

        addData = view.findViewById(R.id.add_data)

        val dayAdapter = DayAdapter(context, days)
        daysRV.adapter = dayAdapter
        if (daysRV.adapter == dayAdapter) {
            Log.i(TAG, "Adapter attached")
        }
        daysRV.layoutManager = LinearLayoutManager(context)
        dayAdapter.listener = object : DayAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                days.removeAt(position)
                db.updateDays(days)
                dayAdapter.notifyItemRemoved(position)
            }
        }
        dayAdapter.notifyDataSetChanged()


        waterbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                oz.text = "$progress oz"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {  }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {  }
        })

        ratingbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateText(rating, progress, 2)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        })

        Log.i(TAG, "SETTING LISTENER")
        addData.setOnClickListener{
            Log.i(TAG, "BTN CLICKED")
            val day: DayEntity = if (days.size == 0) {
                DayEntity(0, getDate(), waterbar.progress, ratingbar.progress)
            } else {
                DayEntity(days[0].id + 1, getDate(), waterbar.progress, ratingbar.progress)
            }
            days.add(0, day)
            db.updateDays(days)
            dayAdapter.notifyDataSetChanged()
        }
        Log.i(TAG, "SET LISTENER")

        view.findViewById<TextView>(R.id.water_title).setOnClickListener {
            days.clear()
            db.updateDays(days)
            dayAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addData(dayAdapter: DayAdapter) {
        Log.i(TAG, "BTN CLICKED")
        val day: DayEntity = if (days.size == 0) {
            DayEntity(0, getDate(), waterbar.progress, ratingbar.progress)
        } else {
            DayEntity(days[0].id + 1, getDate(), waterbar.progress, ratingbar.progress)
        }
        days.add(0, day)
        db.updateDays(days)
        dayAdapter.notifyDataSetChanged()
    }

    private fun getDate() : String {
        var finalString: String = ""
        val currentTime: Date = Calendar.getInstance().time
        currentTime.toString()

        when (currentTime.month) {
            0 -> finalString += "January"
            1 -> finalString += "February"
            2 -> finalString += "March"
            3 -> finalString += "April"
            4 -> finalString += "May"
            5 -> finalString += "June"
            6 -> finalString += "July"
            7 -> finalString += "August"
            8 -> finalString += "September"
            9 -> finalString += "October"
            10 -> finalString += "November"
            11 -> finalString += "December"
        }

        finalString += " ${currentTime.date}"
        finalString += ", ${currentTime.year + 1900}"
        Log.i("MainActivity", finalString)
        return finalString
    }

    @SuppressLint("SetTextI18n")
    private fun updateText(v: TextView, int: Int, mode: Int) {
        if (mode == 1) {
            v.text = "$int oz"
        } else {
            v.text = "$int/10"
        }
    }
}