package com.harshit.merchant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SetMpinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_mpin)

        val etNewMpin = findViewById<EditText>(R.id.etNewMpin)
        val etConfirmMpin = findViewById<EditText>(R.id.etConfirmMpin)
        val btnSaveMpin = findViewById<Button>(R.id.btnSaveMpin)

        btnSaveMpin.setOnClickListener {
            val pin1 = etNewMpin.text.toString().trim()
            val pin2 = etConfirmMpin.text.toString().trim()

            if (pin1.length == 4 && pin1 == pin2) {
                // M-PIN सेव करो
                SessionManager.saveMpin(this, pin1)
                Toast.makeText(this, "M-PIN सेट हो गया!", Toast.LENGTH_SHORT).show()
                
                // डैशबोर्ड पर भेजो
                startActivity(Intent(this, MerchantDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "PIN 4 अंकों का होना चाहिए और दोनों मैच होने चाहिए", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

