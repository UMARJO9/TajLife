package com.umar.tajlifee.categoridetailadpter
import android.annotation.SuppressLint
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

class CategoriDetailAdapter(private val listener: Listener) :
    RecyclerView.Adapter<CategoriDetailAdapter.ViewHolder>() {

    private val items = ArrayList<EntityCategoriModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<EntityCategoriModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextViewdetail: TextView = itemView.findViewById(R.id.titleTvDetal)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.imageViewDetal)

        fun bind(item: EntityCategoriModel, listener: Listener, context: Context) {
            titleTextViewdetail.text = item.title

            val imageUrl = "file:///android_asset/${item.image_url}"

            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.eror)
                .into(avatarImageView)

            // Получаем detailId элемента и передаем его в метод onClickDetail интерфейса Listener
            val detailId = item.detail_id
            itemView.setOnClickListener { listener.onClickDetail(detailId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categori_detal_iteam, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener, holder.itemView.context)
    }

    interface Listener {
        fun onClickDetail(detailId: Int)
    }
}