package com.harshit.merchant

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerDetailControlActivity : AppCompatActivity() {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("Customers")
    private var currentImei: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail_control)

        currentImei = intent.getStringExtra("CUSTOMER_IMEI") ?: ""
        if (currentImei.isEmpty()) {
            Toast.makeText(this, "Error: IMEI Not Found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val etCustomerName = findViewById<EditText>(R.id.etCustomerName)
        val etCustomerMobile = findViewById<EditText>(R.id.etCustomerMobile)
        val etCustomerEmail = findViewById<EditText>(R.id.etCustomerEmail)
        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)

        val btnControlLock = findViewById<LinearLayout>(R.id.btnControlLock)
        val badgeLock = findViewById<TextView>(R.id.badgeLock)

        val btnControlYoutube = findViewById<LinearLayout>(R.id.btnControlYoutube)
        val badgeYoutube = findViewById<TextView>(R.id.badgeYoutube)

        val btnControlCamera = findViewById<LinearLayout>(R.id.btnControlCamera)
        val badgeCamera = findViewById<TextView>(R.id.badgeCamera)

        val btnUninstallSystem = findViewById<Button>(R.id.btnUninstallSystem)
        val btnRemoveProfile = findViewById<Button>(R.id.btnRemoveProfile)

        // Fetch Initial Data
        databaseRef.child(currentImei).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    etCustomerName.setText(snapshot.child("name").getValue(String::class.java) ?: "")
                    etCustomerMobile.setText(snapshot.child("mobile").getValue(String::class.java) ?: "")
                    etCustomerEmail.setText(snapshot.child("email").getValue(String::class.java) ?: "")

                    val isLocked = snapshot.child("isDeviceLocked").getValue(Boolean::class.java) ?: false
                    val isYoutubeLocked = snapshot.child("isYoutubeLocked").getValue(Boolean::class.java) ?: false
                    val isCameraLocked = snapshot.child("isCameraLocked").getValue(Boolean::class.java) ?: false

                    setBadgeDirectly(badgeLock, isLocked)
                    setBadgeDirectly(badgeYoutube, isYoutubeLocked)
                    setBadgeDirectly(badgeCamera, isCameraLocked)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // Save Details
        btnSaveProfile.setOnClickListener {
            databaseRef.child(currentImei).child("name").setValue(etCustomerName.text.toString())
            databaseRef.child(currentImei).child("mobile").setValue(etCustomerMobile.text.toString())
            databaseRef.child(currentImei).child("email").setValue(etCustomerEmail.text.toString())
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show()
        }

        // Toggle Grid Controls
        btnControlLock.setOnClickListener { toggleBadgeAndFirebase(badgeLock, "isDeviceLocked") }
        btnControlYoutube.setOnClickListener { toggleBadgeAndFirebase(badgeYoutube, "isYoutubeLocked") }
        btnControlCamera.setOnClickListener { toggleBadgeAndFirebase(badgeCamera, "isCameraLocked") }

        // Uninstall Flow
        btnUninstallSystem.setOnClickListener {
            databaseRef.child(currentImei).child("uninstallRequested").setValue(true).addOnSuccessListener {
                Toast.makeText(this, "Uninstall Command Sent!", Toast.LENGTH_LONG).show()
                btnUninstallSystem.visibility = View.GONE
                btnRemoveProfile.visibility = View.VISIBLE
            }
        }

        // Remove Profile Flow
        btnRemoveProfile.setOnClickListener {
            databaseRef.child(currentImei).removeValue().addOnSuccessListener {
                Toast.makeText(this, "Customer Removed Permanently!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun setBadgeDirectly(badge: TextView, isLocked: Boolean) {
        if (isLocked) {
            badge.text = "ON"
            badge.setBackgroundColor(Color.parseColor("#4CAF50"))
        } else {
            badge.text = "OFF"
            badge.setBackgroundColor(Color.parseColor("#F44336"))
        }
    }

    private fun toggleBadgeAndFirebase(badge: TextView, firebaseKey: String) {
        val isCurrentlyOn = badge.text.toString() == "ON"
        val newState = !isCurrentlyOn
        setBadgeDirectly(badge, newState)
        databaseRef.child(currentImei).child(firebaseKey).setValue(newState)
        if (firebaseKey == "isDeviceLocked") {
            databaseRef.child(currentImei).child("status").setValue(if (newState) "Locked" else "Unlocked")
        }
    }
}
