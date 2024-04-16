package com.umar.tajlifee.categoridetal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.R
import com.umar.tajlifee.categori.dbCategori.entity.Entity_Categori_Detal


class Categori_Detal_Adapter(private val listener: Listener) : RecyclerView.Adapter<Categori_Detal_Adapter.ViewHolder>() {

    private val items = ArrayList<Entity_Categori_Detal>()

    @SuppressLint("notifyDataSetChanged")
    fun updateItems(items: List<Entity_Categori_Detal>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTvDetal)
        private val avatarImageView = itemView.findViewById<ImageView>(R.id.imageViewDetal)

        fun bind(item: Entity_Categori_Detal, listener: Listener) {
            titleTextView.text = item.detal_name
            avatarImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    item.detal_imageResId
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
            LayoutInflater.from(parent.context).inflate(R.layout.categori_detal_iteam, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    interface Listener {
        fun onClick(item: Entity_Categori_Detal)
    }


}