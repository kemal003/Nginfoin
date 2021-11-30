package com.example.nginfoin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ListArticleFragment : Fragment() {

    private lateinit var databaseRef : DatabaseReference
    private lateinit var listArticle: ArrayList<Article>
    private lateinit var articleRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listArticle = arrayListOf()
        articleRecyclerView = view.findViewById(R.id.recycle_view_articles)
        getListArticle()
        articleRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        println(listArticle.get(0).title)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_article_activity, container, false)
        return view
    }

    private fun getListArticle(){
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