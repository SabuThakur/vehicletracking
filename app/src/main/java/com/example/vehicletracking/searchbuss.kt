package com.example.vehicletracking

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.Adapter.buscardfindbus

import com.example.vehicletracking.Adapter.bustypefindbus
import com.example.vehicletracking.Adapter.dateadapterfindbus
import com.example.vehicletracking.model.buscardmodel
import com.example.vehicletracking.model.bustypefindbusssmodel
import com.example.vehicletracking.model.datefindbusmodel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class searchbuss : AppCompatActivity() {

    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var dateAdapter:dateadapterfindbus
    private lateinit var dateselected: TextView
    private lateinit var searchbt: Button
    private val dateList = ArrayList<datefindbusmodel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchbuss)

       searchbt = findViewById(R.id.searchButton)
        searchbt.setOnClickListener(View.OnClickListener{
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, searchresult::class.java)
            startActivity(intent)
        })
        val imgBack = findViewById<ImageView>(R.id.backButton)

        imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // goes back to previous activity
        }
        dateRecyclerView = findViewById(R.id.dateRecycler)
        dateselected = findViewById(R.id.writedateselect)


            setupDatePicker()



        // Setup RecyclerView
        dateAdapter = dateadapterfindbus(dateList) { selectedDate ->
            dateselected.text = selectedDate
        }

        dateRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        dateRecyclerView.adapter = dateAdapter

        generateNextFiveDates()
        val busTypes = listOf(
            bustypefindbusssmodel("All", true),
            bustypefindbusssmodel(" Volvo AC"),
            bustypefindbusssmodel("deluxe"),
            bustypefindbusssmodel("Ordinary")
        )

        val bustyperw = findViewById<RecyclerView>(R.id.busTypeRecycler)
        bustyperw.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val busTypeAdapter = bustypefindbus(busTypes) { selectedType ->
            // handle selected type if needed
        }
        bustyperw.adapter = busTypeAdapter

        val busList = listOf(
            buscardmodel(
                "What's in a AC bus ?",
                R.drawable.volvoacbus,
                "Comfort: The buses have 2x2 comfortable, luxurious passenger seats.\n" +"\n"+
                        "Air Conditioning: Equipped with good branded air-conditioners for a comfortable and cool journey.\n" +"\n"+
                        "Entertainment: Include 21-inch TVs and CD players for passenger entertainment.\n" +
                        "Technology: Provide mobile and laptop chargers.\n" +"\n"+
                        "Safety and Vision: Toughened window glasses for clear vision, and a front panel designed to increase driver's visibility.\n" +"\n"+
                        "Luggage: Luggage space is provided under the passenger seating area.\n" +"\n"+
                        "Engine: Fitted with a powerful, fuel-efficient, and electronically controlled engine with a turbocharger and intercooler, Euro III compliant. "
            ),
            buscardmodel(
                " What's in a Deluxe Bus ?",
                R.drawable.deluxe,
                "Seating:\n" +
                        "The buses are designed with a 2X2 seating arrangement to maximize passenger comfort. \n" +
                        "Air-Conditioned:\n" +
                        "Deluxe buses are air-conditioned for a cooler and more comfortable journey, especially in warmer climates. \n" +
                        "Entertainment:\n" +
                        "Many Deluxe buses feature 21-inch TVs and music systems to entertain passengers during the journey. \n" +
                        "Other amenities:\n" +
                        "Other features may include lights above each seat, speaker and call bell switches, and mobile/laptop chargers. \n" +
                        "Emergency exits:\n" +
                        "Safety is a priority, with emergency doors often provided behind the rear axle for passenger safety. \n" +
                        "Comfortable seating:\n" +
                        "The seats are designed for comfort, often with features like pushback and recline. "
            ),
            buscardmodel(" What's in a Ordinary Bus ?", R.drawable.ordinary, "Seating:\n" +
                    "Typically 2x3 configuration, meaning two seats per row and then three rows. This allows for a higher number of seats (47 or 37) in a standard bus. \n" +
                    "Comfort:\n" +
                    "Buses are equipped with comfortable, hi-tech seats designed for longer journeys. \n" +
                    "Route:\n" +
                    "Plied on both long and intra-state routes, meaning routes within Himachal Pradesh as well as routes that stretch further. \n" +
                    "Fare:\n" +
                    "Buses run on ordinary fare, meaning they are the standard price category within the HRTC network. \n" +
                    "Maintenance:\n" +
                    "Generally well-maintained and clean, with adequate interiors to ensure a comfortable passenger experience. \n" +
                    "Charging Ports:\n" +
                    "Some newer buses may include USB charging ports for every seat, providing passengers with an opportunity to charge their devices while traveling. \n" +
                    "Luggage:\n" +
                    "Luggage space is typically provided under the seating area for passengers to store their belongings. \n" +
                    "Window Glass:\n" +
                    "Window glass is often toughened to ensure clear vision for passengers while traveling. ")
        )

        val busCardRecycler = findViewById<RecyclerView>(R.id.busCardRecyclerView)
        busCardRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = buscardfindbus(busList) { selectedBus ->
            showBusInfoBottomSheet(selectedBus)
        }

        busCardRecycler.adapter = adapter

    }

    private fun showBusInfoBottomSheet(bus: buscardmodel) {
        val view = LayoutInflater.from(this).inflate(R.layout.bottomsheetbusdatailfindbus, null)

        val dialog = android.app.Dialog(this)
        dialog.setContentView(view)
        dialog.setCancelable(true)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setGravity(android.view.Gravity.CENTER)

        val titleTv = view.findViewById<TextView>(R.id.tvBusDetailTitle)
        val descTv  = view.findViewById<TextView>(R.id.tvBusDetailDesc)
        val img     = view.findViewById<ImageView>(R.id.imgBusDetail)

        titleTv.text = bus.title
        img.setImageResource(bus.imageResId)

        val sb = SpannableStringBuilder()
        val lines = bus.description.trim().lines()
        lines.forEachIndexed { idx, line ->
            val colonIndex = line.indexOf(':')
            if (colonIndex > 0) {
                // pre-colon part
                val before = line.substring(0, colonIndex)
                // the colon + everything after
                val rest   = line.substring(colonIndex)
                // append before, make it bold
                val start = sb.length
                sb.append(before)
                sb.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start, sb.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                // append colon + rest normally
                sb.append(rest)
            } else {
                // no colon—just append whole line
                sb.append(line)
            }
            if (idx != lines.lastIndex) sb.append("\n")
        }

        descTv.text = sb

        dialog.show()

    }

    private fun setupDatePicker() {
        val departureDateEditText = findViewById<TextView>(R.id.showMoreDates)

        departureDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                   dateselected.setText(selectedDate)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
    private fun generateNextFiveDates() {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)  // Mon, Tue
        val dateFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)  // 01 Jan

        for (i in 0 until 5) {
            val date = calendar.time
            dateList.add(datefindbusmodel(dayFormat.format(date), dateFormat.format(date)))
            calendar.add(Calendar.DATE, 1)
        }
        dateAdapter.notifyDataSetChanged()
    }
}


