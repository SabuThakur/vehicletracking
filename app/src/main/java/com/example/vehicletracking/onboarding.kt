package com.example.vehicletracking

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.vehicletracking.Adapter.onboardingadapter
import com.example.vehicletracking.model.onboardingmodel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import me.relex.circleindicator.CircleIndicator3

class onboarding : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Make status bar transparent
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        viewPager = findViewById(R.id.viewPager1)
        val indicator = findViewById<SpringDotsIndicator>(R.id.dots_indicator)
        val buttonGetStarted = findViewById<Button>(R.id.btn_get_started)

        val pages = listOf(
            onboardingmodel(R.drawable.onboarding11, "FIND BUS \nTIMINGS!", "Check schedules anytime."),
            onboardingmodel(R.drawable.onboarding2, "LIVE \nTRACKING!", "Track buses in real-time."),
            onboardingmodel(R.drawable.onboarding3, "EASY \nBOOKING!", "Book tickets in a few clicks.")
        )

        val adapter = onboardingadapter(pages)
        viewPager.adapter = adapter
        indicator.setViewPager2(viewPager)

        // Auto-scroll logic
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (currentPage < pages.size - 1) {
                    currentPage++
                } else {
                    currentPage = 0
                }
                viewPager.setCurrentItem(currentPage, false)
                handler.postDelayed(this, 2000) // Change every 500ms
            }
        }

        handler.postDelayed(runnable, 500)

        buttonGetStarted.setOnClickListener {
            startActivity(Intent(this, loginpage::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Prevent memory leak
    }
}
