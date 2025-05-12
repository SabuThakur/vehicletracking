package com.example.vehicletracking.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.datefindbusmodel

class dateadapterfindbus(
    private val dateList: List<datefindbusmodel>,
    private val onDateSelected: (String) -> Unit
) : RecyclerView.Adapter<dateadapterfindbus.DateViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateItemLayout: CardView = itemView.findViewById(R.id.dateItemLayout) // Use Cardview = itemView.findViewById(R.id.dateItemLayout)
        val dateDay: TextView = itemView.findViewById(R.id.dateDay)
        val dateFull: TextView = itemView.findViewById(R.id.dateFull)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = pos
                    notifyItemChanged(selectedPosition)
                    onDateSelected(dateList[pos].date)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemdatessearchbus, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val item = dateList[position]
        holder.dateDay.text = item.day
        holder.dateFull.text = item.date

        if (position == selectedPosition) {
            holder.dateItemLayout.setBackgroundResource(R.drawable.dateselectedbgfindbus)
            holder.dateDay.setTextColor(Color.WHITE)
            holder.dateFull.setTextColor(Color.WHITE)
        } else {
            holder.dateItemLayout.setBackgroundResource(R.drawable.dateunselectedbgfindbus)
            holder.dateDay.setTextColor(Color.BLACK)
            holder.dateFull.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int = dateList.size
}
