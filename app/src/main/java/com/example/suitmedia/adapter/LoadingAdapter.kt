package com.example.suitmediaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmedia.R
import com.example.suitmedia.databinding.ItemLoadingBinding

class LoadingAdapter (private val retry: () -> Unit) :
    LoadStateAdapter<LoadingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLoadingBinding.bind(itemView)
        init {
            binding.btnRefresh.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState) {
            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.btnRefresh.isVisible = loadState is LoadState.Error
            binding.tvError.isVisible = loadState is LoadState.Error
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false),
            retry
        )
    }
}