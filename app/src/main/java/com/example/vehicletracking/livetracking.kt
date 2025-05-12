package com.example.vehicletracking
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.os.Handler

class livetracking : AppCompatActivity() , OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private var busMarker: Marker? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livetracking)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set default location
        val defaultLocation = LatLng(31.1048, 77.1734)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Simulate bus moving
        simulateBusMovement()
            }

    private fun simulateBusMovement() {
        val handler = Handler(Looper.getMainLooper())
        val route = listOf(
            LatLng(31.1048, 77.1734),
            LatLng(31.1068, 77.1800),
            LatLng(31.1100, 77.1850),
            LatLng(31.1150, 77.1900)
        )

        var index = 0
        handler.post(object : Runnable {
            override fun run() {
                if (index < route.size) {
                    val newLocation = route[index++]

                    if (busMarker == null) {
                        busMarker = mMap.addMarker(
                            MarkerOptions().position(newLocation).title("Bus Location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        )
                    } else {
                        busMarker?.position = newLocation
                    }

                    mMap.animateCamera(CameraUpdateFactory.newLatLng(newLocation))

                    handler.postDelayed(this, 3000) // update every 3 sec
                }
            }
        })
    }
}