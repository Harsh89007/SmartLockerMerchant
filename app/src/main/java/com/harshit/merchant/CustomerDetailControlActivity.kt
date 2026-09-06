package com.harshit.merchant

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CustomerDetailControlActivity : AppCompatActivity() {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("Customers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail_control)

        val customerImei = intent.getStringExtra("CUSTOMER_IMEI") ?: "DEMO_IMEI"

        val switchDeviceLock = findViewById<Switch>(R.id.switchDeviceLock)
        val switchAppLock = findViewById<Switch>(R.id.switchAppLock)
        val btnUninstallSystem = findViewById<Button>(R.id.btnUninstallSystem)

        // 1. Device Lock / Unlock
        switchDeviceLock?.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child("isDeviceLocked").setValue(isChecked)
            databaseRef.child(customerImei).child("status").setValue(if (isChecked) "Locked" else "Unlocked")
            Toast.makeText(this, "Device Status Updated", Toast.LENGTH_SHORT).show()
        }

        // 2. App Lock (YouTube / WhatsApp etc)
        switchAppLock?.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child("isYoutubeLocked").setValue(isChecked)
            Toast.makeText(this, "App Lock Updated", Toast.LENGTH_SHORT).show()
        }

        // 3. UNINSTALL BUTTON (ओनरशिप छोड़ना और लिस्ट से हटाना)
        btnUninstallSystem?.setOnClickListener {
            // कमान भेजना ताकि क्लाइंट ऐप ओनरशिप छोड़ दे
            databaseRef.child(customerImei).child("uninstallRequested").setValue(true).addOnSuccessListener {
                // फायरबेस से भी इस कस्टमर का डेटा डिलीट कर देना ताकि लिस्ट से गायब हो जाए
                databaseRef.child(customerImei).removeValue().addOnSuccessListener {
                    Toast.makeText(this, "डिवाइस अनइंस्टॉल हो गया और लिस्ट से हटा दिया गया!", Toast.LENGTH_LONG).show()
                    finish() // वापस लिस्ट पर लौट जाएं
                }
            }
        }
    }
}

