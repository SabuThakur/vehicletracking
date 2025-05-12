package com.example.vehicletracking.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.R
import com.example.vehicletracking.model.onboardingmodel

class onboardingadapter(private val pages: List<onboardingmodel>) :
        RecyclerView.Adapter<onboardingadapter.OnboardingViewHolder>() {

        class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.image)
            val title: TextView = view.findViewById(R.id.title)
            val description: TextView = view.findViewById(R.id.description)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.onboardingitem, parent, false)
            return OnboardingViewHolder(view)
        }

        override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
            val item = pages[position]
            holder.image.setImageResource(item.imageRes)
            holder.title.text = item.title
            holder.description.text = item.description
        }

        override fun getItemCount(): Int = pages.size
    }