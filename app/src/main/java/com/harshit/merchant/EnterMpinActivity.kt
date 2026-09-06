package com.harshit.merchant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EnterMpinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_mpin)

        val etEnterMpin = findViewById<EditText>(R.id.etEnterMpin)
        val btnLoginMpin = findViewById<Button>(R.id.btnLoginMpin)

        btnLoginMpin.setOnClickListener {
            val enteredPin = etEnterMpin.text.toString().trim()
            val savedPin = SessionManager.getMpin(this)

            if (enteredPin == savedPin) {
                // सही M-PIN, डैशबोर्ड पर भेजो
                startActivity(Intent(this, MerchantDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "गलत M-PIN!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
