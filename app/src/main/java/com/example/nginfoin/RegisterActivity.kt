package com.example.nginfoin

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.nginfoin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.emailBaru.addTextChangedListener(textWatcher)
        binding.passwordBaru.addTextChangedListener(textWatcher)
        binding.passwordBaruConfirm.addTextChangedListener(textWatcher)
        binding.emailBaru.addTextChangedListener(textWatcher)
        binding.namaBaru.addTextChangedListener(textWatcher)
    }

    override fun onStart() {
        super.onStart()
        binding.registerButton.setOnClickListener {
            println("=================TESTING =====================")
            val username = binding.usernameBaru.text.toString()
            val email = binding.emailBaru.text.toString()
            val nama = binding.namaBaru.text.toString()
            val password = binding.passwordBaru.text.toString()
            val passwordConfirm = binding.passwordBaruConfirm.text.toString()
            if (password.equals(passwordConfirm)){
                register(email, password, nama, username)
            } else {
                binding.notMatch.visibility = View.VISIBLE
            }
        }
    }

    private fun register(
        email: String,
        password: String,
        nama: String,
        username: String
    ){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            binding.progressBarRegister.visibility = View.VISIBLE
            if (task.isSuccessful){
                val user = mAuth.currentUser
                databaseRef = Firebase.database.reference.child("Users").child(username)
                val newUser = Users(username, email, nama)
                databaseRef.setValue(newUser)
                binding.progressBarRegister.visibility = View.INVISIBLE
                goHome(user)
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                binding.progressBarRegister.visibility = View.INVISIBLE
                Toast.makeText(this, "Account Register Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goHome(user: FirebaseUser?) {
        val listActivity = Intent(this, ListActivity::class.java)
        startActivity(listActivity)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.notMatch.visibility = View.INVISIBLE
            val usernameInput = binding.emailBaru.text.toString().trim()
            val passwordInput = binding.passwordBaru.text.toString()
            val passwordConfirm = binding.passwordBaruConfirm.text.toString()
            val emailInput = binding.emailBaru.text.toString().trim()
            val namaLengkap = binding.namaBaru.text.toString()

            if (
                usernameInput.isEmpty() || passwordInput.isEmpty() || passwordConfirm.isEmpty() || emailInput.isEmpty() || namaLengkap.isEmpty()
            ){
                binding.registerButton.isEnabled = false
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            val usernameInput = binding.emailBaru.text.toString().trim()
            val passwordInput = binding.passwordBaru.text.toString()
            val passwordConfirm = binding.passwordBaruConfirm.text.toString()
            val emailInput = binding.emailBaru.text.toString().trim()
            val namaLengkap = binding.namaBaru.text.toString()

            if (
                !usernameInput.isEmpty() && !passwordInput.isEmpty() && !passwordConfirm.isEmpty() && !emailInput.isEmpty() && !namaLengkap.isEmpty()
            ){
                binding.registerButton.isEnabled = true
            }
        }
    }
}