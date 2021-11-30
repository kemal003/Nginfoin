package com.example.nginfoin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class YourArticlesFragment : Fragment() {
    private lateinit var databaseRef : DatabaseReference
    private lateinit var listArticle: ArrayList<Article>
    private lateinit var articleAdapter: ArticlesAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var currentUser: FirebaseUser
    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        mAuth = Firebase.auth
        currentUser = mAuth.currentUser!!
        listArticle = arrayListOf()
        articleRecyclerView = view.findViewById(R.id.recycle_view_yourArticles)
        articleAdapter = ArticlesAdapter(requireContext())
        getListArticle()
        articleRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        println(listArticle.get(0).title)
        articleRecyclerView.adapter = articleAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_articles, container, false)
    }

    private fun getListArticle(){
        databaseRef = FirebaseDatabase.getInstance().getReference("Article")
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(articleSnapshot in snapshot.children){
                        val article = articleSnapshot.getValue<Article>()
                        if (article!!.writer.equals(currentUser.uid)){
                            listArticle.add(article)
                        }
                    }
                    articleAdapter.setAllData(listArticle)
                    listArticle.clear()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}