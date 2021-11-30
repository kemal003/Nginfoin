package com.example.nginfoin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticlesAdapter(private val listArticle: ArrayList<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ListViewHolder>(){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titles : TextView = itemView.findViewById(R.id.nameTitles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_articles, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesAdapter.ListViewHolder, position: Int) {
        val (id, title, content, writer) = listArticle[position]
        holder.titles.text = title
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }
}