package com.harshit.merchant

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CustomerDetailControlActivity : AppCompatActivity() {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("Customers")
    private val customerImei = "DEMO_IMEI_12345" // इसे हम बाद में लिस्ट से डायनामिक करेंगे

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
            Toast.makeText(this, "गैलरी खोली जा रही है", Toast.LENGTH_SHORT).show()
        }

        btnSaveProfile.setOnClickListener {
            Toast.makeText(this, "कस्टमर डिटेल्स फायरबेस पर सेव हो गईं!", Toast.LENGTH_SHORT).show()
        }

        // Firebase Realtime Database Updates
        switchDeviceLock.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child("isDeviceLocked").setValue(isChecked)
            Toast.makeText(this, "Device Lock: $isChecked", Toast.LENGTH_SHORT).show()
        }

        switchAppLock.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child("isAppLocked").setValue(isChecked)
            Toast.makeText(this, "App Lock (YouTube etc): $isChecked", Toast.LENGTH_SHORT).show()
        }

        switchCallLock.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child("isCallLocked").setValue(isChecked)
            Toast.makeText(this, "Call Lock: $isChecked", Toast.LENGTH_SHORT).show()
        }
        
        switchSimLock.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child("isSimLocked").setValue(isChecked)
            Toast.makeText(this, "SIM Lock: $isChecked", Toast.LENGTH_SHORT).show()
        }

        btnUninstallSystem.setOnClickListener {
            databaseRef.child(customerImei).child("uninstallRequested").setValue(true)
            Toast.makeText(this, "Uninstall Command Sent to Client via Firebase!", Toast.LENGTH_LONG).show()
        }
    }
}

