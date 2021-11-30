package com.example.nginfoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.nginfoin.databinding.ActivityListBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private var doubleBackExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewPager = findViewById(R.id.viewPager) as ViewPager
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout

        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(ListArticleFragment(), "Articles")
        fragmentAdapter.addFragment(YourArticlesFragment(), "Your Articles")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (doubleBackExit){
            super.onBackPressed()
            backToLogin()
        }

        this.doubleBackExit = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackExit = false }, 1000)
    }

    private fun backToLogin(){
        Firebase.auth.signOut()
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
    }
 }
