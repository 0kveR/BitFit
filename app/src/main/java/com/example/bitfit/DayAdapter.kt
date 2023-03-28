package com.example.bitfit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class DayAdapter(private val context: Context, private val days: List<DayEntity>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    var listener: OnItemClickListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView
        val oz: TextView
        val rating: TextView
        val layout: ConstraintLayout

        init {
            date = itemView.findViewById(R.id.date)
            oz = itemView.findViewById(R.id.oz)
            rating = itemView.findViewById(R.id.rating)
            layout = itemView.findViewById(R.id.item_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.day, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days.get(position)
        holder.itemView.setOnLongClickListener {
            listener?.onItemClick(position)
            true
        }
        holder.date.text = day.date
        holder.oz.text = "${day.oz} oz"
        holder.rating.text = "${day.rating}/10"

        if (day.oz!! < 4 || day.oz > 12) {
            holder.layout.background = ContextCompat.getDrawable(context, R.drawable.day_bad)
        } else if (day.oz in 4..6 || day.oz in 10..12) {
            holder.layout.background = ContextCompat.getDrawable(context, R.drawable.day_mid)
        } else {
            holder.layout.background = ContextCompat.getDrawable(context, R.drawable.day_good)
        }
    }
}