package com.harshit.merchant

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CustomerDetailControlActivity : AppCompatActivity() {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("Customers")
    private val customerImei = "DEMO_IMEI_12345" // इसे हम बाद में डायनामिक करेंगे

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail_control)

        // Advance Controls
        setupSwitch(R.id.switchLock, "isDeviceLocked")
        setupSwitch(R.id.switchUnlock, "isUnlocked")
        setupSwitch(R.id.switchWarningAudio, "warningAudio")
        setupSwitch(R.id.switchGetLocation, "getLocation")

        // Phone Controls
        setupSwitch(R.id.switchCamera, "cameraControl")
        setupSwitch(R.id.switchOutgoingCalls, "outgoingCalls")
        setupSwitch(R.id.switchFileTransfer, "fileTransfer")
        setupSwitch(R.id.switchAppInstallation, "appInstallation")

        // App Controls
        setupSwitch(R.id.switchWhatsapp, "whatsappLock")
        setupSwitch(R.id.switchTelegram, "telegramLock")
        setupSwitch(R.id.switchChrome, "chromeLock")
        setupSwitch(R.id.switchYoutube, "youtubeLock")
        setupSwitch(R.id.switchInstagram, "instagramLock")
        setupSwitch(R.id.switchPhonepe, "phonepeLock")

        // Uninstall Button
        val btnUninstallSystem = findViewById<Button>(R.id.btnUninstallSystem)
        btnUninstallSystem.setOnClickListener {
            databaseRef.child(customerImei).child("uninstallRequested").setValue(true)
                .addOnSuccessListener {
                    databaseRef.child(customerImei).removeValue().addOnSuccessListener {
                        Toast.makeText(this, "डिवाइस अनइंस्टॉल और लिस्ट से रिमूव कर दिया गया!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
        }
    }

    private fun setupSwitch(switchId: Int, firebaseKey: String) {
        val switch = findViewById<Switch>(switchId)
        switch?.setOnCheckedChangeListener { _, isChecked ->
            databaseRef.child(customerImei).child(firebaseKey).setValue(isChecked)
            Toast.makeText(this, "$firebaseKey: $isChecked", Toast.LENGTH_SHORT).show()
        }
    }
}
