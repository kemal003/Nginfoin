package com.example.nginfoin

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.nginfoin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var doubleBackExit = false
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mAuth = Firebase.auth

        binding.emailUser.addTextChangedListener(textWatcher)
        binding.passwordUser.addTextChangedListener(textWatcher)
    }

    override fun onStart() {
        super.onStart()
//        val currentUser = mAuth.currentUser
//        if (currentUser != null){
//            goHome(currentUser)
//        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailUser.text.toString()
            val password = binding.passwordUser.text.toString()
            login(email, password)
        }

        binding.signUp.setOnClickListener{
            goRegister()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            val usernameInput = binding.emailUser.text.toString().trim()
            val passwordInput = binding.passwordUser.text.toString()

            if (!usernameInput.isEmpty() && !passwordInput.isEmpty()){
                binding.loginButton.isEnabled = true
            }
        }
    }

    private fun login(email: String, password: String){
        binding.progressBarLogin.visibility = View.VISIBLE
        try {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = mAuth.currentUser
                        binding.progressBarLogin.visibility = View.GONE
                        binding.emailUser.text.clear()
                        binding.passwordUser.text.clear()
                        goHome(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        binding.progressBarLogin.visibility = View.GONE
                        binding.passwordUser.text.clear()
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception){
            Toast.makeText(this, "Email dan Password tidak valid", Toast.LENGTH_SHORT).show()
            binding.progressBarLogin.visibility = View.GONE
        }
    }

    private fun goHome(user: FirebaseUser?) {
        val readActivity = Intent(this, ReadActivity::class.java)
        startActivity(readActivity)
    }

    private fun goRegister(){
        val registerActivity = Intent(this, RegisterActivity::class.java)
        startActivity(registerActivity)
    }

    override fun onBackPressed() {
        if (doubleBackExit){
            super.onBackPressed()
            onDestroy()
        }

        this.doubleBackExit = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackExit = false }, 1000)
    }
}