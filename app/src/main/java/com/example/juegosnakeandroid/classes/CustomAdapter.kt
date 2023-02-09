package com.example.juegosnakeandroid.classes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.juegosnakeandroid.R

class CustomAdapter(private val context: Context, private var playerList: MutableList<Player>?): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt: TextView = itemView.findViewById(R.id.textViewRowItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playerList?.size ?: -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (playerList != null) {
            val item = Item(playerList!![position].toString())
            holder.txt.text = item.txtPlayer
        }
    }

}