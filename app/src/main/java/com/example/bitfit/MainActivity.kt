package com.example.bitfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bitfit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var db: DayDatabase
    lateinit var days: MutableList<DayEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DayDatabase(this)
        days = db.getAllDays()

        val navigation: BottomNavigationView = binding.bottomNavigation

        navigation.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.report_data -> {
                    days = db.getAllDays()
                    fragment = DailyWaterFragment(db, days)
                }
                R.id.graph_data -> {
                    days = db.getAllDays()
                    fragment = DataFragment(db, days)
                }
            }
            replaceFragment(fragment)
            true
        }

        navigation.selectedItemId = R.id.report_data
    }

    private fun replaceFragment(articleListFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, articleListFragment)
        fragmentTransaction.commit()
    }
}