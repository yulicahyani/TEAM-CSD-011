package com.csd011.miracle.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.csd011.miracle.R
import com.csd011.miracle.databinding.ActivityStartBinding
import com.csd011.miracle.ui.login.LoginActivity
import com.csd011.miracle.ui.main.MainActivity
import com.csd011.miracle.ui.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {

    private  lateinit var activityStartBinding: ActivityStartBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)

        auth = FirebaseAuth.getInstance()

        activityStartBinding.loginBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        activityStartBinding.signupBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}