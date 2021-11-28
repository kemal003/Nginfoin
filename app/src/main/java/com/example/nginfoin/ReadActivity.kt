package com.example.nginfoin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class ReadActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        val toolbar : Toolbar = findViewById<Toolbar>(R.id.appbar_read)
        setSupportActionBar(toolbar)
        val supportBar = supportActionBar
        supportBar?.setDisplayHomeAsUpEnabled(true)
        val article_title : TextView = findViewById<TextView>(R.id.article_title)
        val article_content : TextView = findViewById<TextView>(R.id.article_content)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        article_title.text = title
        article_content.text = content
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}