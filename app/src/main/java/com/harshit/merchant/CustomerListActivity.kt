package com.harshit.merchant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CustomerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        val btnEditAndControl = findViewById<Button>(R.id.btnEditAndControl)

        // एडिट बटन पर क्लिक करते ही कंट्रोल पैनल और प्रोफाइल खुलेगी
        btnEditAndControl.setOnClickListener {
            val intent = Intent(this, CustomerDetailControlActivity::class.java)
            startActivity(intent)
        }
    }
}
