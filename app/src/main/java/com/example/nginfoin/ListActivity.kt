package com.example.nginfoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.nginfoin.databinding.ActivityListBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ListActivity : AppCompatActivity() {

    private lateinit var databaseRef : DatabaseReference
    private lateinit var listArticle: ArrayList<Article>
    private lateinit var binding: ActivityListBinding
    private lateinit var articleRecyclerView : RecyclerView

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

//        listArticle = arrayListOf()
//        articleRecyclerView = binding.recyclerView2
//        articleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        databaseRef = FirebaseDatabase.getInstance().getReference("Article").child("4")
//        databaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.getValue<Article>()
//                println("==================$value======================")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
////        println("====================$article}=====================")
//        getListArticles()
    }


    private fun getListArticles(){
        databaseRef = FirebaseDatabase.getInstance().getReference("Article")
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(articleSnapshot in snapshot.children){
                        val article = articleSnapshot.getValue<Article>()
                        listArticle.add(article!!)
                    }
                    articleRecyclerView.adapter = ArticlesAdapter(listArticle)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
 }
