package com.example.vehicletracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class femalebottomsheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.femalebottomsheetitem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Close Button
        val imgBack = view.findViewById<ImageView>(R.id.ivClose)  // Corrected findViewById usage
        imgBack.setOnClickListener {
            dismiss()  // Close the bottom sheet
        }

        // Enable Button
        val btnEnable = view.findViewById<Button>(R.id.btnEnableFemale)
        btnEnable.setOnClickListener {
            btnEnable.isEnabled = true  // Enable the button
            btnEnable.setBackgroundColor(resources.getColor(R.color.black)) // Change background color to green, for example
            btnEnable.text = "Enabled"
            dismiss() // Close the bottom sheet when button is clicked
            // You can trigger any other logic here for enabling the female fare toggle if needed
        }
    }
}
