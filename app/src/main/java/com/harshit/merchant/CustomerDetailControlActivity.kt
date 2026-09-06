package com.harshit.merchant

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomerDetailControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail_control)

        val btnUploadPhoto = findViewById<Button>(R.id.btnUploadPhoto)
        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)
        
        val switchDeviceLock = findViewById<Switch>(R.id.switchDeviceLock)
        val switchAppLock = findViewById<Switch>(R.id.switchAppLock)
        val switchCallLock = findViewById<Switch>(R.id.switchCallLock)
        val switchSimLock = findViewById<Switch>(R.id.switchSimLock)
        val btnUninstallSystem = findViewById<Button>(R.id.btnUninstallSystem)

        btnUploadPhoto.setOnClickListener {
            Toast.makeText(this, "गैलरी खुल रही है...", Toast.LENGTH_SHORT).show()
        }

        btnSaveProfile.setOnClickListener {
            // यहाँ फायरबेस में नाम, नंबर, ईमेल सेव होगा
            Toast.makeText(this, "कस्टमर डिटेल्स सेव हो गईं!", Toast.LENGTH_SHORT).show()
        }

        // Toggles / Switches के लॉजिक
        switchDeviceLock.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Locked" else "Unlocked"
            Toast.makeText(this, "Device $status", Toast.LENGTH_SHORT).show()
        }

        switchAppLock.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Locked" else "Unlocked"
            Toast.makeText(this, "Apps (YouTube etc.) $status", Toast.LENGTH_SHORT).show()
        }

        switchCallLock.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Locked" else "Unlocked"
            Toast.makeText(this, "Calls $status", Toast.LENGTH_SHORT).show()
        }
        
        switchSimLock.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Locked" else "Unlocked"
            Toast.makeText(this, "SIM $status", Toast.LENGTH_SHORT).show()
        }

        btnUninstallSystem.setOnClickListener {
            Toast.makeText(this, "सिस्टम हटाने की रिक्वेस्ट भेजी गई!", Toast.LENGTH_LONG).show()
        }
    }
}
