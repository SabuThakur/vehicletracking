package com.example.vehicletracking

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Registerpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registerpage)

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setTitle("Please Wait")
        progressDialog.setCancelable(false)
        val logo = findViewById<ImageView>(R.id.logo)
        var username: EditText = findViewById(R.id.registerusername)
        var email: EditText = findViewById(R.id.registeremail)
        var password: EditText = findViewById(R.id.registerpassword)
        var confirm_pass: EditText = findViewById(R.id.registerconfirmpassword)
        var phonenumber: EditText = findViewById(R.id.phonenumber)
        var btn: Button = findViewById(R.id.btnRegister)
        var txtsignin: TextView = findViewById(R.id.txtSignIn)
        val spinner: Spinner = findViewById(R.id.spinnerGender)
        val GenderOption = arrayOf("Select Gender", "Male", "Female", "Others")
        val animation = AnimationUtils.loadAnimation(this, R.anim.slidedownlogologin)
        logo.startAnimation(animation)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, GenderOption)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (position != 0) {
                    Toast.makeText(
                        applicationContext,
                        "Selected: $selectedItem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        txtsignin.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, loginpage::class.java)
            startActivity(intent)
        })

        btn.setOnClickListener(View.OnClickListener {
            var uname = username.text.toString()
            var pass = password.text.toString()
            var Email = email.text.toString()
            var cpassword = confirm_pass.text.toString()
            if (uname.length == 0 || pass.length == 0 || Email.length == 0 || cpassword.length == 0) {
                Toast.makeText(applicationContext, "Please fill all Details", Toast.LENGTH_SHORT)
                    .show()
            } else if (!Email.contains("@gmail.com")) {
                Toast.makeText(applicationContext, "Invalid Email", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(pass)) {
                Toast.makeText(this, "Invalid Pass", Toast.LENGTH_SHORT)
                    .show()
            } else if (pass != cpassword) {
                Toast.makeText(this, "Pass DoesNot match", Toast.LENGTH_SHORT)
                    .show()
            } else {
                progressDialog.show()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, pass)
                    .addOnCompleteListener { task ->
                        progressDialog.dismiss()
                        if (task.isSuccessful) {

                            val sharedPref = getSharedPreferences("LoginDetails", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPref.edit()
                            editor.putString("username", uname)
                            editor.putString("email", Email)
                            editor.putString("password", pass)
                            editor.putBoolean("FirstTime", true)
                            editor.apply()

                            Toast.makeText(
                                applicationContext,
                                "Record Inserted",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, homescreen::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "Errororrr :-" + task.exception, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        progressDialog.dismiss()
                        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                    }
            }
        })
    }

    fun isValidPassword(passwordHere: String): Boolean {
        var hasLetter = false
        var hasDigit = false
        var hasSpecial = false

        if (passwordHere.length < 8)
            return false

        for (p in passwordHere.indices) {
            when {
                passwordHere[p].isLetter() -> hasLetter = true
                passwordHere[p].isDigit() -> hasDigit = true
                passwordHere[p] in "@#\$%^&+=!" -> hasSpecial = true
            }
        }
        return hasLetter && hasDigit && hasSpecial
    }
}
