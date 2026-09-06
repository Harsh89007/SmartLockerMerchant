package com.harshit.merchant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class MerchantDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merchant_dashboard)

        val cardAddCustomer = findViewById<CardView>(R.id.cardAddCustomer)
        val cardCustomerList = findViewById<CardView>(R.id.cardCustomerList)
        val cardFrpAccounts = findViewById<CardView>(R.id.cardFrpAccounts)
        val cardSupport = findViewById<CardView>(R.id.cardSupport)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        cardAddCustomer.setOnClickListener {
            startActivity(Intent(this, AddCustomerActivity::class.java))
        }

        cardCustomerList.setOnClickListener {
            // ये वाली स्क्रीन हम आगे बनाएंगे
            Toast.makeText(this, "Customer List खुल रही है...", Toast.LENGTH_SHORT).show()
        }

        cardFrpAccounts.setOnClickListener {
    val intent = Intent(this, FrpAccountsActivity::class.java)
    startActivity(intent)
        }
        

        cardSupport.setOnClickListener {
            Toast.makeText(this, "Support पेज जल्द उपलब्ध होगा", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            // 1. Firebase से Logout
            FirebaseAuth.getInstance().signOut()
            // 2. M-PIN डिलीट करना (ताकि वापस Login स्क्रीन पर जाए)
            SessionManager.saveMpin(this, "") 
            
            Toast.makeText(this, "लॉगआउट सफल!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
