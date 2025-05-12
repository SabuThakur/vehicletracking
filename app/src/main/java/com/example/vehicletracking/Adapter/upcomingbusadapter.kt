package com.example.vehicletracking.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.upcomingbusmodel
import com.example.vehicletracking.upcomingbustimings

interface OnBusItemClickListener {
    fun onViewMoreClicked(bus: upcomingbusmodel)
}

class upcomingbusadapter(
    private val busList: List<upcomingbusmodel>,
    private val listener: OnBusItemClickListener
) : RecyclerView.Adapter<upcomingbusadapter.BusViewHolder>() {

    // Create the ViewHolder
    inner class BusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val busImage: ImageView = itemView.findViewById(R.id.busImage)
        val busName: TextView = itemView.findViewById(R.id.busName)
        val journeyDate: TextView = itemView.findViewById(R.id.journeyDate)
        val busRoute: TextView = itemView.findViewById(R.id.busRoute)
        val departureTime: TextView = itemView.findViewById(R.id.departureTime)
        val shareIcon: ImageView = itemView.findViewById(R.id.shareIcon)
        val viewMoreBtn: TextView = itemView.findViewById(R.id.viewMoreBtn)

        // Setting click listeners
        init {
            shareIcon.setOnClickListener {
                // Handle share action here
                shareBusDetails(itemView.context, adapterPosition)
            }
            viewMoreBtn.setOnClickListener {
                listener.onViewMoreClicked(busList[adapterPosition])
            }
        }

    }

    // Inflate the item layout and return the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemschedule, parent, false) // Your card layout
        return BusViewHolder(itemView)
    }

    // Bind data to the views
    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val busItem = busList[position]
        holder.busImage.setImageResource(busItem.busImage)
        holder.busName.text = busItem.busName
        holder.journeyDate.text = busItem.journeyDate
        holder.busRoute.text = busItem.busRoute
        holder.departureTime.text = busItem.departureTime
    }

    // Return the size of the data list
    override fun getItemCount() = busList.size

    // Share logic (e.g., Share via Intent)
    private fun shareBusDetails(context: Context, position: Int) {
        val bus = busList[position]
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this bus: ${bus.busName}, Route: ${bus.busRoute}, Departure Time: ${bus.departureTime}")
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}
