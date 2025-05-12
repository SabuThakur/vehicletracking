package com.example.vehicletracking.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.routeoptionmodel


class searchresultfaredatailsadapter(
    private var busList: List<routeoptionmodel>,
    private var selectedFareType: String = "Default" // "Female", "Kids", "Senior", or "Default"
) : RecyclerView.Adapter<searchresultfaredatailsadapter.BusViewHolder>() {

    inner class BusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textBusName: TextView = itemView.findViewById(R.id.busName)
        val textAmenities: TextView = itemView.findViewById(R.id.textAmenities)
        val textDeparture: TextView = itemView.findViewById(R.id.busTiming)
        val textCancellation: TextView = itemView.findViewById(R.id.textCancellation)
        val textFare: TextView = itemView.findViewById(R.id.textFare)
        val imageBus: ImageView = itemView.findViewById(R.id.imageBus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottomsheetsearchresult, parent, false)
        return BusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val bus = busList[position]

        holder.textBusName.text = bus.name
        holder.textAmenities.text = bus.amenities
        holder.textDeparture.text = bus.departure

        holder.textCancellation.text = bus.cancellationPolicy
        holder.imageBus.setImageResource(bus.imageResId)

        // Dynamically set fare
        val fare = when (selectedFareType) {
            "Female" -> bus.femaleFare
            "Kids" -> bus.kidFare
            "Senior" -> bus.seniorFare
            else -> bus.baseFare
        }
        holder.textFare.text = "₹$fare"
    }

    override fun getItemCount(): Int = busList.size

    fun updateFareType(type: String) {
        selectedFareType = type
        notifyDataSetChanged()
    }

    fun submitList(newList: List<routeoptionmodel>) {
        busList = newList
        notifyDataSetChanged()
    }
}
