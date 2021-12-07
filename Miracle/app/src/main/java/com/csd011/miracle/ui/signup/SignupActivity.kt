package com.csd011.miracle.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.csd011.miracle.R
import com.csd011.miracle.databinding.ActivitySignupBinding
import com.csd011.miracle.ui.login.LoginActivity
import com.csd011.miracle.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var activitySignupBinding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(activitySignupBinding.root)

        auth = FirebaseAuth.getInstance()
        
        activitySignupBinding.btnSignup.setOnClickListener {
            val email: String = activitySignupBinding.etEmailEt.text.toString().trim()
            val password: String = activitySignupBinding.etPassword.text.toString().trim()

            if (email.isEmpty()){
                activitySignupBinding.etEmailEt.error = "Email belum terisi"
                activitySignupBinding.etEmailEt.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                activitySignupBinding.etEmailEt.error = "Email tidak valid"
                activitySignupBinding.etEmailEt.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 8){
                activitySignupBinding.etPassword.error = "Password harus lebih dari 8 karakter"
                activitySignupBinding.etPassword.requestFocus()
                return@setOnClickListener
            }

            signupUser(email, password)

        }

        activitySignupBinding.tvLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signupUser(email: String, password: String) {
        activitySignupBinding.loadProgressBar.progressBar.visibility = View.VISIBLE
        activitySignupBinding.loadProgressBar.pbText.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activitySignupBinding.loadProgressBar.progressBar.visibility = View.GONE
                    activitySignupBinding.loadProgressBar.pbText.visibility = View.GONE
                    startActivity(intent)
                }
                else{
                    activitySignupBinding.loadProgressBar.progressBar.visibility = View.GONE
                    activitySignupBinding.loadProgressBar.pbText.visibility = View.GONE
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}