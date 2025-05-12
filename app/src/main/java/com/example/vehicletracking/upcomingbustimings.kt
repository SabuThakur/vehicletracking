package com.example.vehicletracking

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.Adapter.OnBusItemClickListener
import com.example.vehicletracking.Adapter.upcomingbusadapter
import com.example.vehicletracking.model.upcomingbusmodel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class upcomingbustimings : AppCompatActivity(), OnBusItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var busAdapter: upcomingbusadapter
    private var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upcomingbustimings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgBack = findViewById<ImageView>(R.id.backButton)

        imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // goes back to previous activity
        }

        recyclerView = findViewById(R.id.scheduleRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val busList = listOf(
            upcomingbusmodel(R.drawable.slidehomepage5, "Volvo Express", "12 May 2025", "Shimla ➔ Manali", "08:00 AM - 02:00 PM"),
            upcomingbusmodel(R.drawable.slidehomepage3, "Luxury Bus", "15 May 2025", "Delhi ➔ Jaipur", "09:00 AM - 03:00 PM")
        )

        // ✅ Pass 'this' as listener
        busAdapter = upcomingbusadapter(busList, this)
        recyclerView.adapter = busAdapter
    }


    // ✅ Move this outside onCreate()
    override fun onViewMoreClicked(bus: upcomingbusmodel) {
        showBusDetailsBottomSheet(bus)
    }

    fun showBusDetailsBottomSheet(bus: upcomingbusmodel) {
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottomsheetsearchresult, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)

        bottomSheetView.findViewById<TextView>(R.id.busName).text = bus.busName
        bottomSheetView.findViewById<TextView>(R.id.textAmenities).text = "WiFi, AC, Charging"
        bottomSheetView.findViewById<TextView>(R.id.busTiming).text = bus.departureTime
        bottomSheetView.findViewById<TextView>(R.id.textCancellation).text = "Free Cancellation : 12h after booking"
        bottomSheetView.findViewById<TextView>(R.id.textFare).text = "₹999"
        bottomSheetView.findViewById<ImageView>(R.id.imageBus).setImageResource(bus.busImage)

        dialog.show()
        val closeIcon = bottomSheetView.findViewById<ImageView>(R.id.bcloseicon)
        closeIcon?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        bottomSheetDialog = dialog // store reference so we can dismiss it globally if needed
    }
    }

