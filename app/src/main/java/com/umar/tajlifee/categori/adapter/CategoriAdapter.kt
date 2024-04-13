package com.umar.tajlifee.categori.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.R
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel


class ChatsAdapter(val listener: Listener) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    private val items = ArrayList<EntityCategoriModel>()

    @SuppressLint("notifyDataSetChanged")
    fun updateItems(items: List<EntityCategoriModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTvChat)
        private val avatarImageView = itemView.findViewById<ImageView>(R.id.imageViewChat)

        fun bind(item: EntityCategoriModel, listener: Listener) {
            titleTextView.text = item.name
            avatarImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    item.imageResId
                )
            )
            itemView.setOnLongClickListener {
                listener.onClick(item)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.categori_iteam, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    interface Listener {
        fun onClick(item: EntityCategoriModel)
    }


}