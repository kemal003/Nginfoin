package com.example.nginfoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.nginfoin.databinding.ActivityCreateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateActivity : AppCompatActivity() {

    private lateinit var databaseRef : DatabaseReference
    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val include_create = findViewById<View>(R.id.include_appbar_create)
        val btn_create = include_create.findViewById<Button>(R.id.button_create)
        btn_create.setOnClickListener {
            Toast.makeText(this, "Artikel telah dibuat", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ListActivity::class.java))
        }
        val btn_back = findViewById<ImageView>(R.id.back_create)
        btn_back.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        binding.includeAppbarCreate.buttonCreate.setOnClickListener {
            val newTitle = binding.etTitleCreate.text.toString()
            val newContent = binding.textInputCreate.text.toString()
            createArticle(newTitle, newContent)
        }
    }

    private fun createArticle(title: String, content: String){
        databaseRef = Firebase.database.getReference("Article")
        val newArticle = Article(7, title, content, "BaeSuzy")
        databaseRef.child("7").setValue(newArticle).addOnSuccessListener {
            Toast.makeText(this, "Article created", Toast.LENGTH_SHORT).show()
        }
    }
}