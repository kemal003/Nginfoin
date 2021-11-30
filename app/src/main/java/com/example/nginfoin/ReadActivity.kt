package com.example.nginfoin

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.nginfoin.databinding.ActivityReadBinding

class ReadActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val writer = intent.getStringExtra("writer")

        binding.articleTitle.text = title
        binding.articleContent.text = content
        binding.articleWriter.text = "Writer: $writer"

        val btn_read = binding.includeAppbarRead.backRead
        btn_read.setOnClickListener{
            finish()
        }
    }
}