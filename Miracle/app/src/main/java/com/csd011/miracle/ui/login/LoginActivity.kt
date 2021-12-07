package com.csd011.miracle.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.csd011.miracle.R
import com.csd011.miracle.databinding.ActivityLoginBinding
import com.csd011.miracle.ui.main.MainActivity
import com.csd011.miracle.ui.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private  lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        auth = FirebaseAuth.getInstance()

        activityLoginBinding.tvSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        activityLoginBinding.btnSignin.setOnClickListener {
            val email: String = activityLoginBinding.etEmailEt.text.toString().trim()
            val password: String = activityLoginBinding.etPassword.text.toString().trim()

            if (email.isEmpty()){
                activityLoginBinding.etEmailEt.error = "Email belum terisi"
                activityLoginBinding.etEmailEt.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                activityLoginBinding.etEmailEt.error = "Email tidak valid"
                activityLoginBinding.etEmailEt.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 8){
                activityLoginBinding.etPassword.error = "Password belum terisi"
                activityLoginBinding.etPassword.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        activityLoginBinding.loadProgressBar.progressBar.visibility = View.VISIBLE
        activityLoginBinding.loadProgressBar.pbText.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activityLoginBinding.loadProgressBar.progressBar.visibility = View.GONE
                    activityLoginBinding.loadProgressBar.pbText.visibility = View.GONE
                    startActivity(intent)
                }
                else {
                    activityLoginBinding.loadProgressBar.progressBar.visibility = View.GONE
                    activityLoginBinding.loadProgressBar.pbText.visibility = View.GONE
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}