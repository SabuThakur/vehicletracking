package com.example.vehicletracking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.postDelayed
import androidx.viewpager2.widget.ViewPager2
import com.example.vehicletracking.Adapter.imagesliderhomepage
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator


class homescreen : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: SpringDotsIndicator
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val imageList = listOf(
        R.drawable.slidehomepage1,
        R.drawable.slidehomepage2,
        R.drawable.slidehomepage3,
        R.drawable.slidehomepage4,
        R.drawable.slidehomepage5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        viewPager = findViewById(R.id.imageSlider)
        dotsIndicator = findViewById(R.id.dotsIndicator)

        val search = findViewById<EditText>(R.id.searchBar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val findbuss = findViewById<CardView>(R.id.findbus)
        val nearbystations=findViewById<CardView>(R.id.nearbystations)
        val payment=findViewById<CardView>(R.id.paymentSection)
        val saveroutess=findViewById<CardView>(R.id.savedroutes)
        val upcomingtime=findViewById<CardView>(R.id.upcomingbuses)
        val adapter = imagesliderhomepage(imageList)
        viewPager.adapter = adapter
        dotsIndicator.setViewPager2(viewPager)

        autoScrollImages()



        findbuss.setOnClickListener(View.OnClickListener{
            Toast.makeText(this, "searchbuses", Toast.LENGTH_SHORT).show()
          val intent = Intent(this,searchbuss::class.java)
         startActivity(intent)
      })
        nearbystations.setOnClickListener(View.OnClickListener{
            Toast.makeText(this, "near by stations", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,livetracking::class.java)
            startActivity(intent)
        })
// your button ID
        saveroutess.setOnClickListener {
            val intent = Intent(this, shortlistedbuses::class.java)
            startActivity(intent)
        }
        upcomingtime.setOnClickListener {
            val intent = Intent(this, upcomingbustimings::class.java)
            startActivity(intent)
        }


        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // Already on home
                    true
                }
                R.id.nav_bookings -> {
                   // startActivity(Intent(this, BookingsActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                  //  startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    private fun autoScrollImages() {
        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == imageList.size) currentPage = 0
                viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // every 3 seconds
            }
        }
        handler.post(runnable)
    }
}

