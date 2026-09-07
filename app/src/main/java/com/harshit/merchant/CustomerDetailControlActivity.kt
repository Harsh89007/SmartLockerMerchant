package com.harshit.merchant

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CustomerDetailControlActivity : AppCompatActivity() {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("Customers")
    private var currentImei: String = "" // कोई डेमो नहीं!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail_control)

        // 1. Customer List से असली IMEI मंगाना
        currentImei = intent.getStringExtra("CUSTOMER_IMEI") ?: ""
        if (currentImei.isEmpty()) {
            Toast.makeText(this, "Error: IMEI Not Found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2. UI Elements
        val btnControlLock = findViewById<LinearLayout>(R.id.btnControlLock)
        val badgeLock = findViewById<TextView>(R.id.badgeLock)
        
        val btnControlYoutube = findViewById<LinearLayout>(R.id.btnControlYoutube)
        val badgeYoutube = findViewById<TextView>(R.id.badgeYoutube)

        val btnUninstallSystem = findViewById<Button>(R.id.btnUninstallSystem)
        val btnRemoveProfile = findViewById<Button>(R.id.btnRemoveProfile)

        // 3. Grid Buttons पर क्लिक करने का लॉजिक (ON/OFF टॉगल)
        btnControlLock.setOnClickListener {
            toggleBadgeAndFirebase(badgeLock, "isDeviceLocked")
        }
        btnControlYoutube.setOnClickListener {
            toggleBadgeAndFirebase(badgeYoutube, "isYoutubeLocked")
        }

        // 4. UNINSTALL BUTTON लॉजिक
        btnUninstallSystem.setOnClickListener {
            // फायरबेस में कमांड भेजेंगे ताकि क्लाइंट ऐप ओनरशिप छोड़ दे
            databaseRef.child(currentImei).child("uninstallRequested").setValue(true)
                .addOnSuccessListener {
                    Toast.makeText(this, "Uninstall Command Sent! Client App will remove MDM.", Toast.LENGTH_LONG).show()
                    
                    // Uninstall बटन छुपाओ, Remove बटन दिखाओ
                    btnUninstallSystem.visibility = View.GONE
                    btnRemoveProfile.visibility = View.VISIBLE
                }
        }

        // 5. REMOVE PROFILE BUTTON लॉजिक
        btnRemoveProfile.setOnClickListener {
            // फायरबेस से कस्टमर की पूरी प्रोफाइल डिलीट मार दो
            databaseRef.child(currentImei).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Customer Removed Permanently!", Toast.LENGTH_LONG).show()
                    finish() // वापस लिस्ट पर चला जाएगा
                }
        }
    }

    // बैज का कलर और टेक्स्ट बदलने का फंक्शन
    private fun toggleBadgeAndFirebase(badge: TextView, firebaseKey: String) {
        val isCurrentlyOn = badge.text.toString() == "ON"
        val newState = !isCurrentlyOn
        
        if (newState) {
            badge.text = "ON"
            badge.setBackgroundColor(Color.parseColor("#4CAF50")) // Green
        } else {
            badge.text = "OFF"
            badge.setBackgroundColor(Color.parseColor("#F44336")) // Red
        }
        
        // असली फायरबेस पर सेव करो
        databaseRef.child(currentImei).child(firebaseKey).setValue(newState)
    }
}
