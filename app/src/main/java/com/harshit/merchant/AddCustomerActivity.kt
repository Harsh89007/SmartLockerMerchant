package com.harshit.merchant

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.UUID

class AddCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)

        val btnShowStep1Qr = findViewById<Button>(R.id.btnShowStep1Qr)
        val imgStep1Qr = findViewById<ImageView>(R.id.imgStep1Qr)
        
        val etClientAppLink = findViewById<EditText>(R.id.etClientAppLink)
        val btnGenerateLinkQr = findViewById<Button>(R.id.btnGenerateLinkQr)
        val imgStep2Qr = findViewById<ImageView>(R.id.imgStep2Qr)
        
        val imgStep3PairingQr = findViewById<ImageView>(R.id.imgStep3PairingQr)
        val btnOkDone = findViewById<Button>(R.id.btnOkDone)

        // Step 1: Test DPC Fixed QR Generate करना
        btnShowStep1Qr.setOnClickListener {
            val dpcJson = """
                {
                    "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME": "com.afwsamples.testdpc/com.afwsamples.testdpc.DeviceAdminReceiver",
                    "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM": "gJD2YwtOiWJHkSMkkIfLRlj-quNqG1fb6v100QmzM9w=",
                    "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION": "https://testdpc-latest-apk.url"
                }
            """.trimIndent()
            
            val bitmap = generateQrCode(dpcJson)
            if (bitmap != null) {
                imgStep1Qr.setImageBitmap(bitmap)
            }
        }

        // Step 2: Dynamic Link QR
        btnGenerateLinkQr.setOnClickListener {
            val link = etClientAppLink.text.toString().trim()
            if (link.isNotEmpty()) {
                val bitmap = generateQrCode(link)
                if (bitmap != null) {
                    imgStep2Qr.setImageBitmap(bitmap)
                    imgStep2Qr.visibility = View.VISIBLE
                    Toast.makeText(this, "QR Generated!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "कृपया पहले लिंक डालें", Toast.LENGTH_SHORT).show()
            }
        }

        // Step 3: Pairing QR (मर्चेंट की ID के साथ ताकि क्लाइंट स्कैन कर सके)
        val merchantId = FirebaseAuth.getInstance().currentUser?.uid ?: "DEMO_MERCHANT"
        val pairingToken = "merchant_$merchantId"
        val pairingBitmap = generateQrCode(pairingToken)
        if (pairingBitmap != null) {
            imgStep3PairingQr.setImageBitmap(pairingBitmap)
        }

        // OK Button: क्लिक करते ही Customer List स्क्रीन पर चला जाएगा
        btnOkDone.setOnClickListener {
            Toast.makeText(this, "डिवाइस सिंक! कस्टमर लिस्ट चेक करें।", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CustomerListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun generateQrCode(text: String): Bitmap? {
        return try {
            val multiFormatWriter = MultiFormatWriter()
            val bitMatrix: BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400)
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

