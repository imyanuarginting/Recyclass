package com.example.recyclass.ui.listarticle

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclass.databinding.LayoutItemLoadingBinding

class LoadingAdapter(private val reload: () -> Unit) : LoadStateAdapter<LoadingAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.textView.isVisible = loadState is LoadState.Error
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.button.isVisible = loadState is LoadState.Error
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = LayoutItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, reload)
    }

    class ViewHolder(binding: LayoutItemLoadingBinding, reload: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textViewLayoutItemLoading
        val progressBar: ProgressBar = binding.progressBarLayoutItemLoading
        val button: Button = binding.btnReload
        init {
            button.setOnClickListener { reload.invoke() }
        }
    }
}