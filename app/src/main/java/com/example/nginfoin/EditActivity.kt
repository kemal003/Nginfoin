package com.example.nginfoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.nginfoin.databinding.ActivityEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        try {
            currentUser = Firebase.auth.currentUser!!
        } catch (e: Exception){

        }

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        binding.etTitleEdit.setText(title)
        binding.textInputEdit.setText(content)

        val include_edit = findViewById<View>(R.id.include_appbar_edit)
        val btn_edit = include_edit.findViewById<Button>(R.id.button_edit)
        btn_edit.setOnClickListener {
            Toast.makeText(this, "Artikel telah diedit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ListActivity::class.java))
        }
        val btn_back = findViewById<ImageView>(R.id.back_edit)
        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.includeAppbarEdit.buttonEdit.setOnClickListener {
            val newTitle = binding.etTitleEdit.text.toString()
            val newContent = binding.textInputEdit.text.toString()
            editArticle(newTitle, newContent)
        }
    }

    private fun editArticle(title: String, content: String){
        val newId = intent.getIntExtra("id", 0)
        databaseRef = Firebase.database.getReference("Article")
        val newArticle = Article(newId, title, content, currentUser.uid)
        databaseRef.child(newId.toString()).setValue(newArticle).addOnSuccessListener {
            Toast.makeText(this, "Article Edited", Toast.LENGTH_SHORT).show()
            goHome()
        }
    }

    private fun goHome() {
        val home = Intent(this, ListActivity::class.java)
        startActivity(home)
        finish()
    }

    private fun goLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}