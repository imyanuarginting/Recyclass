package com.example.recyclass.ui.listarticle

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclass.data.dataclass.Article
import com.example.recyclass.databinding.LayoutItemBinding

class Adapter : PagingDataAdapter<Article, Adapter.ViewHolder>(diffCallback = diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        Glide.with(holder.itemView.context)
            .load(data?.img_url)
            .into(holder.imageView)
        with(holder) {
            title.text = data?.title
            desc.text = data?.author
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data!!)
            }
        }
    }

    class ViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imageViewArticleLayoutItem
        val title: TextView = binding.textViewArticleTitleLayoutItem
        val desc: TextView = binding.textViewArticleDescLayoutItem
    }

    lateinit var onItemClickCallback: OnItemClickCallback

    fun onItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(item: Article)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url && oldItem.img_url == newItem.img_url
            }
        }
    }
}