package com.harshit.merchant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToSignUp = findViewById<TextView>(R.id.tvGoToSignUp)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "लॉगिन सफल!", Toast.LENGTH_SHORT).show()
                            // लॉगिन के बाद चेक करेंगे कि M-PIN सेट है या नहीं (वह लॉजिक हम Splash में भी संभालेंगे)
                            // अभी के लिए सीधा M-PIN सेट/एंटर करने भेजते हैं
                            startActivity(Intent(this, SetMpinActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "एरर: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "ईमेल और पासवर्ड भरें", Toast.LENGTH_SHORT).show()
            }
        }

        tvGoToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}
