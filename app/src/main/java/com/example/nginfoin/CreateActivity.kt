package com.example.nginfoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
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
}