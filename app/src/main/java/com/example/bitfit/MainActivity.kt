package com.example.bitfit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private var days = mutableListOf<DayEntity>()
    private lateinit var db: DayDatabase
    private lateinit var binding: ActivityMainBinding
    private lateinit var waterbar: SeekBar
    private lateinit var ratingbar: SeekBar
    private lateinit var oz: TextView
    private lateinit var rating: TextView
    private lateinit var daysRV: RecyclerView
    private lateinit var addBtn: Button

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DayDatabase(this)
        days = db.getAllDays()

        binding.let {
            waterbar = it.waterbar
            ratingbar = it.ratebar
            oz = it.ozCount
            rating = it.ratingCount
            daysRV = it.daysRV
            addBtn = it.addBtn
        }

        val dayAdapter = DayAdapter(this, days)
        daysRV.adapter = dayAdapter
        daysRV.layoutManager = LinearLayoutManager(this)
        dayAdapter.listener = object : DayAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                days.removeAt(position)
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

        addBtn.setOnClickListener {
            val day: DayEntity
            if (days.size == 0) {
                day = DayEntity(0, getDate(), waterbar.progress, ratingbar.progress)
            } else {
                day = DayEntity(days[0].id + 1, getDate(), waterbar.progress, ratingbar.progress)
            }
            days.add(0, day)
            db.updateDays(days)
            dayAdapter.notifyDataSetChanged()
        }

        findViewById<TextView>(R.id.main_title).setOnClickListener {
            days.clear()
            db.updateDays(days)
            dayAdapter.notifyDataSetChanged()
        }
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