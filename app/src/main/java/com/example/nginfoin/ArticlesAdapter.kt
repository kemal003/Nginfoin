package com.example.nginfoin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ArticlesAdapter(
//    private val listArticle: ArrayList<Article>
    private var mContext : Context
) : RecyclerView.Adapter<ArticlesAdapter.ListViewHolder>(){

    val listArticle2: ArrayList<Article> = ArrayList()
    private lateinit var databaseRef: DatabaseReference

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titles : TextView = itemView.findViewById(R.id.nameTitles)
        val layoutItem : FrameLayout = itemView.findViewById(R.id.layoutItem)
    }

    fun setAllData(data: List<Article>){
        listArticle2.clear()
        listArticle2.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_articles, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesAdapter.ListViewHolder, position: Int) {
        val (id, title, content, writer) = listArticle2[position]
        holder.titles.text = title
        holder.layoutItem.setOnClickListener {
            databaseRef = FirebaseDatabase.getInstance().getReference("Article")
            val readActivity = Intent(mContext, ReadActivity::class.java)
            readActivity.putExtra("title", title)
            readActivity.putExtra("content", content)
            readActivity.putExtra("writer", writer)
            mContext.startActivity(readActivity)
        }
    }

    override fun getItemCount(): Int {
        return listArticle2.size
    }
}