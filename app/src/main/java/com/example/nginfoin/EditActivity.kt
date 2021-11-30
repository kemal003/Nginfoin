package com.example.nginfoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val include_edit = findViewById<View>(R.id.include_appbar_edit)
        val btn_edit = include_edit.findViewById<Button>(R.id.button_edit)
        btn_edit.setOnClickListener {
            Toast.makeText(this, "Artikel telah diedit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ListActivity::class.java))
        }
        val btn_back = findViewById<ImageView>(R.id.back_edit)
        btn_back.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}