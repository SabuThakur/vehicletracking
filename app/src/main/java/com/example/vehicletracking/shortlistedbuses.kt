package com.example.vehicletracking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicletracking.Adapter.itemsearchbusresultadapter
import com.example.vehicletracking.model.itemsearchbusresultmodel

class shortlistedbuses : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyMessage: TextView
    private lateinit var browseButton: Button
    private lateinit var adapter: itemsearchbusresultadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shortlistedbuses)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.shortlistedbusRecyclerView)
        emptyMessage = findViewById(R.id.emptyMessage)
        browseButton = findViewById(R.id.browseBusesButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Use the global shortlist data directly
        val shortlistedBuses = ShortlistBusStore.shortlistedBuses

        adapter = itemsearchbusresultadapter(this, shortlistedBuses, 1.0)
        recyclerView.adapter = adapter

        // ✅ Show/hide based on list content
        if (shortlistedBuses.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
            browseButton.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyMessage.visibility = View.GONE
            browseButton.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        browseButton.setOnClickListener {
            startActivity(Intent(this, searchresult::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.close_icon).setOnClickListener {
            onBackPressed()
        }
    }
}
