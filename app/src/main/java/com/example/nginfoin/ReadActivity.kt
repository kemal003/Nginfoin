package com.example.nginfoin

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.nginfoin.databinding.ActivityReadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReadActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReadBinding
    private lateinit var databaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val writer = intent.getStringExtra("writer").toString()
        databaseRef = Firebase.database.reference.child("Users").child(writer)
        println(databaseRef)

        databaseRef.child("nama").get().addOnSuccessListener {
            val trueWriter = it.value.toString()
            println(trueWriter)
            binding.articleWriter.text = "Penulis: $trueWriter"
        }

        binding.articleTitle.text = title
        binding.articleContent.text = content

        val btn_read = binding.includeAppbarRead.backRead
        btn_read.setOnClickListener{
            finish()
        }
    }
}