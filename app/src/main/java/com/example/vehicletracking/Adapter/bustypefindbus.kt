package com.example.vehicletracking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.bustypefindbusssmodel


class bustypefindbus(
    private val busTypes: List<bustypefindbusssmodel>,
    private val onItemSelected: (bustypefindbusssmodel) -> Unit
) : RecyclerView.Adapter<bustypefindbus.BusTypeViewHolder>() {

    inner class BusTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeText: TextView = itemView.findViewById(R.id.tvBusType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itembustypesearchbus, parent, false)
        return BusTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusTypeViewHolder, position: Int) {
        val busType = busTypes[position]
        holder.typeText.text = busType.name

        // Change background based on selection
        val context = holder.itemView.context
        val background = if (busType.isSelected)
            ContextCompat.getDrawable(context, R.drawable.bustypeselectedfindbus)
        else
            ContextCompat.getDrawable(context, R.drawable.bustypeunselectedfindbus)

        holder.itemView.background = background


        holder.typeText.setOnClickListener {
            busTypes.forEach { it.isSelected = false }  // Deselect all
            busType.isSelected = true
            notifyDataSetChanged()
            onItemSelected(busType)
        }
    }

    override fun getItemCount(): Int = busTypes.size
}
