package com.example.vehicletracking.Adapter
import android.content.Context
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.vehicletracking.R
import com.example.vehicletracking.model.promocardmodel

class  promocardadpter (
        private val context: Context,
        private val promoList: List<promocardmodel>
    ) : RecyclerView.Adapter<promocardadpter.PromoViewHolder>() {

        inner class PromoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvTitle = itemView.findViewById<TextView>(R.id.tvPromoTitle)
            val tvDescription = itemView.findViewById<TextView>(R.id.tvPromoDescription)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.promocardlayout, parent, false)
            return PromoViewHolder(view)
        }

        override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
            val promo = promoList[position]
            holder.tvTitle.text = promo.title
            holder.tvDescription.text = promo.description
        }

        override fun getItemCount(): Int = promoList.size
    }

