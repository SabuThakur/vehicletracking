package com.example.vehicletracking.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.filterbuttonmodel

class fiklterbuttonadapter(
    private val filters: List<filterbuttonmodel>,
    private val onFilterClick: (filterbuttonmodel) -> Unit
) : RecyclerView.Adapter<fiklterbuttonadapter.FilterViewHolder>() {

    class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cardSort)
        val title: TextView = itemView.findViewById(R.id.tvSortTitle)
        val filterDrawerButton: View = itemView.findViewById(R.id.btnFilterDrawer) // New
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemfilterbuttonsearchresult, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = filters[position]

        if (item.title == "Filter") {
            // Show the filter icon layout and hide card
            holder.card.visibility = View.GONE
            holder.filterDrawerButton.visibility = View.VISIBLE

            holder.filterDrawerButton.setOnClickListener {
                // Trigger click for drawer open
                onFilterClick(item)
            }

        } else {
            // Show regular card
            holder.card.visibility = View.VISIBLE
            holder.filterDrawerButton.visibility = View.GONE
            holder.title.text = item.title

            // Style based on selection
            if (item.isSelected) {
                holder.card.setCardBackgroundColor(Color.parseColor("#007BFF"))
                holder.title.setTextColor(Color.WHITE)
            } else {
                holder.card.setCardBackgroundColor(Color.WHITE)
                holder.title.setTextColor(Color.BLACK)
            }

            holder.card.setOnClickListener {
                item.isSelected = !item.isSelected
                notifyItemChanged(position)
                onFilterClick(item)
            }
        }
    }


    override fun getItemCount(): Int = filters.size
}
