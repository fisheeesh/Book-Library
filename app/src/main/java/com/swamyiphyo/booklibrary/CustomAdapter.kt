package com.swamyiphyo.booklibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val context : Context,
    private val bookId : ArrayList<String>,
    private val bookTitle : ArrayList<String>,
    private val bookAuthor : ArrayList<String>,
    private val bookPages : ArrayList<String>
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.list_books, parent, false))

    override fun getItemCount(): Int = bookId.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.id.text = bookId[position]
        holder.title.text = bookTitle[position]
        holder.author.text = bookAuthor[position]
        holder.pages.text = bookPages[position]
    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id : TextView = itemView.findViewById(R.id.textView_book_id)
        val title : TextView = itemView.findViewById(R.id.textView_book_title)
        val author : TextView = itemView.findViewById(R.id.textView_book_author)
        val pages : TextView = itemView.findViewById(R.id.textView_book_pages)
    }
}