package com.example.vehicletracking

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class loginpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginpage)
        val logo = findViewById<ImageView>(R.id.appLogo)
        var button1: Button = findViewById(R.id.loginButton)
        var button2: Button = findViewById(R.id.googleButton)
        var email: EditText=findViewById(R.id.emailEditText)
        var password: EditText = findViewById(R.id.passwordEditText)
        var textviewsignup: TextView = findViewById(R.id.signupText)

        val sharedPref = getSharedPreferences("LoginDetails", MODE_PRIVATE)
        val savedUsername = sharedPref.getString("email", null)
        val savedPassword = sharedPref.getString("password", null)

        val animation = AnimationUtils.loadAnimation(this, R.anim.slidedownlogologin)
        logo.startAnimation(animation)
        button1.setOnClickListener(View.OnClickListener {
            var Email = email.text.toString()
            var pass = password.text.toString()
            if (Email.length == 0 || pass.length == 0) {
                Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else if (!Email.contains("@gmail.com")) {
                Toast.makeText(applicationContext, "Invalid Email", Toast.LENGTH_SHORT).show()
            } else if (pass.length < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT)
                    .show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(Email, pass)
                    .addOnSuccessListener { task ->
                                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT)
                                    .show()

                                val editor: SharedPreferences.Editor = sharedPref.edit()
                                editor.putBoolean("FirstTime", true)
                                editor.apply()

                                val intent = Intent(this, homescreen::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()

                            }.addOnFailureListener(this) { exception ->
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                )
                textviewsignup.setOnClickListener(View.OnClickListener {
                    val intent = Intent(this, Registerpage::class.java)
                    startActivity(intent)
                  })
                }
        }
