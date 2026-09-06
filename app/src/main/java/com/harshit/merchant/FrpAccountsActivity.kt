package com.harshit.merchant

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FrpAccountsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frp_accounts)

        val etFrpImei = findViewById<EditText>(R.id.etFrpImei)
        val etFrpEmail = findViewById<EditText>(R.id.etFrpEmail)
        val etFrpPassword = findViewById<EditText>(R.id.etFrpPassword)
        val btnSaveFrp = findViewById<Button>(R.id.btnSaveFrp)

        btnSaveFrp.setOnClickListener {
            val imei = etFrpImei.text.toString().trim()
            val email = etFrpEmail.text.toString().trim()
            val password = etFrpPassword.text.toString().trim()

            if (imei.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // यहाँ हम फायरबेस में FRP डेटा सेव करेंगे
                Toast.makeText(this, "FRP अकाउंट सफलतापूर्वक सेव हो गया!", Toast.LENGTH_SHORT).show()
                
                // फील्ड्स खाली करें
                etFrpImei.text.clear()
                etFrpEmail.text.clear()
                etFrpPassword.text.clear()
            } else {
                Toast.makeText(this, "कृपया सभी डिटेल्स भरें", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
