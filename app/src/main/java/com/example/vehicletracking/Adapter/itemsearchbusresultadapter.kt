package com.example.vehicletracking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.ShortlistBusStore
import com.example.vehicletracking.model.itemsearchbusresultmodel

class itemsearchbusresultadapter(
    private val context: Context,
    private var busList: MutableList<itemsearchbusresultmodel>,
    private var genderFareMultiplier: Double
) : RecyclerView.Adapter<itemsearchbusresultadapter.BusViewHolder>() {

    inner class BusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val tvPromo: TextView = itemView.findViewById(R.id.tvPromoApplied)
        val tvBusName: TextView = itemView.findViewById(R.id.tvBusName)
        val tvBusType: TextView = itemView.findViewById(R.id.tvBusType)
        val tvTimeDuration: TextView = itemView.findViewById(R.id.tvTimeDuration)
        val tvFare: TextView = itemView.findViewById(R.id.tvFare)
        val tvOriginalFare: TextView = itemView.findViewById(R.id.tvOriginalFare)
        val tvSeatsLeft: TextView = itemView.findViewById(R.id.tvSeatsLeft)
        val llElectric: LinearLayout = itemView.findViewById(R.id.llElectric)
        val btnBusDetails: LinearLayout = itemView.findViewById(R.id.btnBusDetails)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val textAmenities: TextView = itemView.findViewById(R.id.textAmenities)
        val textTiming: TextView = itemView.findViewById(R.id.textTiming)
        val viewMoreBtn: TextView = itemView.findViewById(R.id.viewMoreBtn)
        val shortlistplusButtonn: ImageView = itemView.findViewById(R.id.shortlistplusbutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemsearchbusresult, parent, false)
        return BusViewHolder(view)
    }

    override fun getItemCount(): Int = busList.size

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val item = busList[position]

        holder.tvRating.text = "★ ${item.rating}"
        holder.tvPromo.text = item.promo
        holder.tvBusName.text = item.name
        holder.tvBusType.text = item.type
        holder.tvTimeDuration.text = item.timing
        holder.tvSeatsLeft.text = item.seatsLeft
        holder.textAmenities.text = item.amenities
        holder.textTiming.text = "Departure: ${item.departure} | Arrival: ${item.arrival}"
        holder.shortlistplusButtonn.setOnClickListener {
            val selectedBus = busList[position]

            val isShortlisted = ShortlistBusStore.shortlistedBuses.contains(selectedBus)

            if (!isShortlisted) {
                ShortlistBusStore.shortlistedBuses.add(selectedBus)
                holder.shortlistplusButtonn.setBackgroundResource(R.drawable.bgblueseleted) // Blue background
                Toast.makeText(context, "Bus shortlisted!", Toast.LENGTH_SHORT).show()
            } else {
                ShortlistBusStore.shortlistedBuses.remove(selectedBus)
                holder.shortlistplusButtonn.setBackgroundResource(R.drawable.bggrayunselected) // Default background
                Toast.makeText(context, "Bus removed from shortlist", Toast.LENGTH_SHORT).show()
            }
        }


        val finalFare = (item.fare * genderFareMultiplier).toInt()
        holder.tvFare.text = "₹$finalFare"
        holder.tvOriginalFare.text = "₹${item.originalFare}"

        holder.llElectric.visibility = if (item.isElectric) View.VISIBLE else View.GONE
        holder.expandableLayout.visibility = View.GONE

        holder.btnBusDetails.setOnClickListener {
            val isVisible = holder.expandableLayout.visibility == View.VISIBLE
            holder.expandableLayout.visibility = if (isVisible) View.GONE else View.VISIBLE
        }

        holder.viewMoreBtn.setOnClickListener {
            Toast.makeText(context, "More info for ${item.name}", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateGenderFareMultiplier(multiplier: Double) {
        genderFareMultiplier = multiplier
        notifyDataSetChanged()
    }

    fun submitList(newList: List<itemsearchbusresultmodel>) {
        this.busList = newList.toMutableList()
        notifyDataSetChanged()
    }
}
