package com.example.vehicletracking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.buscardmodel


class buscardfindbus(
    private val cards: List<buscardmodel>,
    private val onCardClick: (buscardmodel) -> Unit

) : RecyclerView.Adapter<buscardfindbus.CardViewHolder>() {
    private val summaryMap = mapOf(
        "What's in a AC bus ?"       to "Learn about AC bus comfort, entertainment & more.",
        "What's in a Deluxe Bus ?"    to "Discover Deluxe bus seating, AC, and extras.",
        "What's in a Ordinary Bus ?"  to "See what Ordinary buses offer in fares and seating."
    )

    private val iconMap = mapOf(
        "What's in a AC bus ?"       to R.drawable.acicon,       // your AC icon
        "What's in a Deluxe Bus ?"    to R.drawable.chargingicon,   // your Deluxe icon
        "What's in a Ordinary Bus ?"  to R.drawable.seat1// your Ordinary icon
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itembuscardfindbus, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgBus: ImageView = view.findViewById(R.id.imgBus)
        private val tvTitle: TextView = view.findViewById(R.id.tvBusTitle)
        private val tvInfo: TextView = view.findViewById(R.id.tvBusInfo)

        fun bind(card: buscardmodel) {
            imgBus.setImageResource(card.imageResId)
            tvTitle.text = card.title
            val key = card.title.trim()
            tvInfo.text = summaryMap[key]
                ?: card.description.substringBefore("\n")
            val iconRes = iconMap[card.title.trim()]
            if (iconRes != null) {
                tvInfo.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0)
                tvInfo.compoundDrawablePadding = 12  // space between icon & text
            } else {
                tvInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            // 4) Tap ripple + bounce + click callback (unchanged)
            itemView.setOnClickListener {
                val anim = AnimationUtils.loadAnimation(
                    itemView.context,
                    R.anim.cardtapfindbus
                )
                itemView.startAnimation(anim)
                itemView.postDelayed({ onCardClick(card) }, anim.duration)
                itemView.setOnClickListener {
                    onCardClick(card)
                }
            }
        }
    }}
