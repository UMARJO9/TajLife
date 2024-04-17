package com.umar.tajlifee.categori.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umar.tajlifee.R
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel

class ChatsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    private val items = ArrayList<EntityCategoriModel>()

    fun updateItems(items: List<EntityCategoriModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTvChat)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.imageViewChat)

        fun bind(item: EntityCategoriModel, listener: Listener, context: Context) {
            titleTextView.text = item.title

            val imageUrl = "file:///android_asset/${item.image_url}.png"

            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.eror)
                .into(avatarImageView)


            itemView.setOnClickListener { listener.onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.categori_iteam, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener, holder.itemView.context)
    }

    interface Listener {
        fun onClick(item: EntityCategoriModel)
    }
}
