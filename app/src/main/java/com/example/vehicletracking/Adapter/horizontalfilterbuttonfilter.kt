package com.example.vehicletracking.Adapter

    import android.content.Context
    import android.graphics.Color
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.core.content.ContextCompat
    import androidx.recyclerview.widget.RecyclerView
    import com.example.vehicletracking.R
    import com.example.vehicletracking.model.horizontalfilterbuttonmodel

class  horizontalfilterbuttonfilter(
        private val context: Context,
        private val filterList: List<horizontalfilterbuttonmodel>,
        private val onFilterToggled: (horizontalfilterbuttonmodel) -> Unit
    ) : RecyclerView.Adapter< horizontalfilterbuttonfilter.FilterViewHolder>() {

        inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvFilterLabel = itemView.findViewById<TextView>(R.id.tvFilterLabel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_horizontalfilterbutton, parent, false)
            return FilterViewHolder(view)
        }

        override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
            val filter = filterList[position]
            holder.tvFilterLabel.text = filter.label
            holder.tvFilterLabel.setBackgroundResource(
                if (filter.isSelected) R.drawable.bghorizontalselectedfilter else R.drawable.bghorizontalunselectedfilter
            )
            holder.tvFilterLabel.setTextColor(
                if (filter.isSelected) Color.WHITE else Color.DKGRAY
            )

            holder.itemView.setOnClickListener {
                filter.isSelected = !filter.isSelected
                notifyItemChanged(position)
                onFilterToggled(filter)
            }
        }

        override fun getItemCount(): Int = filterList.size
    }

