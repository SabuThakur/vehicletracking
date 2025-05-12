package com.example.vehicletracking

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.Adapter.*
import com.example.vehicletracking.model.*

enum class FareCategory {
    DEFAULT, FEMALE, KIDS, SENIOR_CITIZEN
}

class searchresult : AppCompatActivity() {

    private lateinit var rvBuses: RecyclerView
    private lateinit var adapter:itemsearchbusresultadapter
    private lateinit var filterButtonsRecycler:RecyclerView
    private lateinit var filterAdapter:fiklterbuttonadapter

    private val filterButtonList = mutableListOf<filterbuttonmodel>()
    private val originalBusList = mutableListOf<itemsearchbusresultmodel>()
    private var selectedFareCategory = FareCategory.DEFAULT
    private val selectedFilters = mutableSetOf<String>()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var femaleinfo : TextView
    private lateinit var femaleFareToggle: LinearLayout
    private lateinit var femaleFareSwitchh: SwitchCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_searchresult)
        drawerLayout = findViewById(R.id.filterDrawerLayout)
        femaleinfo = findViewById(R.id.femaleInfo)
        femaleFareToggle = findViewById(R.id.femaleFareToggle)
        femaleFareSwitchh = findViewById(R.id.femaleFareSwitch)

        val imgBack = findViewById<ImageView>(R.id.backIcon)

        imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // goes back to previous activity
        }



        // Initialize filters
        val filters = listOf(
            filterbuttonmodel("Filter"),
            filterbuttonmodel("Sort"),
            filterbuttonmodel("Single Seat"),
            filterbuttonmodel("AC"),
            filterbuttonmodel("Non AC"),
            filterbuttonmodel("Seater"),
            filterbuttonmodel("Sleeper"),
        )

        // Set up filter recycler view
        filterButtonsRecycler = findViewById(R.id.filterRecyclerView)
        filterAdapter = fiklterbuttonadapter(filters) { selectedFilter ->
            if (selectedFilter.title == "Filter") {
                drawerLayout.openDrawer(GravityCompat.END)
            } else {
                selectedFilters.addOrRemoveFilter(selectedFilter)
                applyFiltersAndFare()
            }
        }

        filterButtonsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filterButtonsRecycler.adapter = filterAdapter

        // Setup Bus Recycler
        rvBuses = findViewById(R.id.busRecyclerView)
        rvBuses.setHasFixedSize(true)
        rvBuses.layoutManager = LinearLayoutManager(this)
        rvBuses.itemAnimator = null
        rvBuses.isNestedScrollingEnabled = false

        // Initialize Promo List
        val recyclerPromo = findViewById<RecyclerView>(R.id.promoRecyclerView)
        val promoList = listOf(
            promocardmodel("SAVE20 Applied", "Get 20% off on your ride"),
            promocardmodel("FIRSTBUS", "₹50 off for new users"),
            promocardmodel("SPRING25", "Flat 25% discount valid today"),
            promocardmodel("FAMILYRIDE", "Special fares for group bookings")
        )
        val promoAdapter = promocardadpter(this, promoList)
        recyclerPromo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerPromo.adapter = promoAdapter

        // Setup Gender Fare Toggle
        setupGenderFareToggle()

        // Setup Bus List
        setupBusRecycler()
        femaleinfo.setOnClickListener {
            val bottomSheet = femalebottomsheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

    }
    private fun setupGenderFareToggle() {
        femaleFareSwitchh.isChecked = false
        femaleFareToggle.setBackgroundResource(R.drawable.bgtoggleoff)

        femaleFareSwitchh.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                femaleFareToggle.setBackgroundResource(R.drawable.trackfemale)
                selectedFareCategory = FareCategory.FEMALE
                showCustomToast("Booking for Female ON",femaleFareToggle)
            } else {
                femaleFareToggle.setBackgroundResource(R.drawable.bgtoggleoff)
                selectedFareCategory = FareCategory.DEFAULT
                showCustomToast("Booking for Female OFF", femaleFareToggle)
            }
            applyFiltersAndFare()
        }
    }
    private fun showCustomToast(message: String, anchorView: View) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.customtoastlayout, null)

        val text: TextView = layout.findViewById(R.id.toastText)
        text.text = message

        val toast = Toast(applicationContext)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT

        // Positioning below the anchor view
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1] + anchorView.height

        toast.setGravity(Gravity.TOP or Gravity.START, x, y)
        toast.show()
    }

    private fun applyFiltersAndFare() {
        val filteredList = originalBusList.filter { bus ->
            selectedFilters.all { filter ->
                when (filter) {
                    "AC" -> bus.isAC
                    "Non AC" -> !bus.isAC
                    "Seater" -> bus.isSeater
                    "Sleeper" -> !bus.isSeater
                    else -> true
                }
            }
        }.map { bus ->
            val baseFare = bus.originalFare

            // Apply fare modification based on selectedFareCategory (Female, Kids, etc.)
            bus.fare = when (selectedFareCategory) {
                FareCategory.FEMALE -> (baseFare * 0.5).toInt() // 50% off for females
                FareCategory.KIDS -> (baseFare * 0.5).toInt() // 50% off for kids
                FareCategory.SENIOR_CITIZEN -> (baseFare * 0.8).toInt() // 20% off for seniors
                FareCategory.DEFAULT -> baseFare
            }

            bus
        }

        // Submit the updated list to the adapter to reflect the changes
        adapter.submitList(filteredList)
    }

    private fun setupBusRecycler() {
        originalBusList.addAll(
            listOf(
                itemsearchbusresultmodel("3.6", "NEW2BUS promo applied", "NueGo", "AC Seater 2+2", "11:59 PM - 06:15 AM (06h 16m)", 449, 499, "21 Seats Left", true, "AC, Water Bottle", "11:59 PM", "06:15 AM", true, true),
                itemsearchbusresultmodel("4.2", "SAVE20 applied", "HRTC Volvo", "Volvo Multi-Axle", "10:00 PM - 05:30 AM (07h 30m)", 899, 999, "14 Seats Left", false, "AC, Charging Port", "10:00 PM", "05:30 AM", false, false)
            )
        )


        adapter = itemsearchbusresultadapter(this, originalBusList.toMutableList(), genderFareMultiplier = 1.0)
        rvBuses.adapter = adapter
        applyFiltersAndFare()
    }



    private fun MutableSet<String>.addOrRemoveFilter(filter: filterbuttonmodel) {
        if (filter.isSelected) {
            this.add(filter.title)
        } else {
            this.remove(filter.title)
        }
    }
}
