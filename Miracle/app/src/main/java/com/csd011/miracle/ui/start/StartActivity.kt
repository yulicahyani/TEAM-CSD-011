package com.csd011.miracle.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.csd011.miracle.R
import com.csd011.miracle.databinding.ActivityStartBinding
import com.csd011.miracle.ui.login.LoginActivity
import com.csd011.miracle.ui.signup.SignupActivity

class StartActivity : AppCompatActivity() {

    private  lateinit var activityStartBinding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)

        activityStartBinding.loginBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        activityStartBinding.signupBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}