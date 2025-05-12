package com.example.vehicletracking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.routeoptionmodel

class bottomsheetsearchresulttadapter(
    private val busList: List<routeoptionmodel>,
    private val onBusClick: (routeoptionmodel) -> Unit
) : RecyclerView.Adapter<bottomsheetsearchresulttadapter.BusViewHolder>() {

    private var expandedPosition = -1

    inner class BusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val busName: TextView = itemView.findViewById(R.id.busName)
        val textAmenities: TextView = itemView.findViewById(R.id.textAmenities)
        val textTiming: TextView = itemView.findViewById(R.id.textTiming)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val viewMoreBtn: Button = itemView.findViewById(R.id.viewMoreBtn)

        fun bind(bus: routeoptionmodel, isExpanded: Boolean) {
            busName.text = bus.name
            textAmenities.text = bus.amenities
            textTiming.text = "Departure: ${bus.departure} | Arrival: ${bus.arrival}"

            expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            viewMoreBtn.text = if (isExpanded) "View Less" else "View More"

            itemView.setOnClickListener {
                onBusClick(bus)
            }

            viewMoreBtn.setOnClickListener {
                expandedPosition = if (isExpanded) -1 else adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottomsheetsearchresult, parent, false)
        return BusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        holder.bind(busList[position], isExpanded)
    }

    override fun getItemCount(): Int = busList.size
}
