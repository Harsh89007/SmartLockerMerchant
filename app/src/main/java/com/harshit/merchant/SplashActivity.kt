package com.harshit.merchant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            val savedMpin = SessionManager.getMpin(this)

            if (currentUser != null) {
                // मर्चेंट लॉगिन है, अब चेक करो कि M-PIN सेट है या नहीं
                if (savedMpin != null) {
                    startActivity(Intent(this, EnterMpinActivity::class.java))
                } else {
                    startActivity(Intent(this, SetMpinActivity::class.java))
                }
            } else {
                // मर्चेंट लॉगिन नहीं है, साइन-अप पेज पर भेजो
                startActivity(Intent(this, SignUpActivity::class.java))
            }
            finish()
        }, 2000) // 2 सेकंड का स्प्लैश स्क्रीन
    }
}
