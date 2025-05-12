package com.example.vehicletracking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class splashscreen : AppCompatActivity() {
    private lateinit var logoroutemate: ImageView
    private lateinit var car: ImageView
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation
    private lateinit var leftAnim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        logoroutemate= findViewById(R.id.logoroutemate1)
        text2 = findViewById(R.id.text2)

        topAnim = AnimationUtils.loadAnimation(this, R.anim.topanim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottomanim)
        leftAnim = AnimationUtils.loadAnimation(this, R.anim.leftanim)

        logoroutemate.startAnimation(topAnim)
        text2.startAnimation(bottomAnim)

      Handler(Looper.getMainLooper()).postDelayed({
                val sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE)
                val isFirstTime = sharedPreferences.getBoolean("FirstTime", false)

                if (isFirstTime) {
                    startActivity(Intent(this, homescreen::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, onboarding::class.java))
                    finish()
                }

        }, 3000)




    }}
