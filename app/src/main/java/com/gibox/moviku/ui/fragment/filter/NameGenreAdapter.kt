package com.gibox.moviku.ui.fragment.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gibox.moviku.data.model.genre.GenresItem
import com.gibox.moviku.databinding.ItemModalBinding

class NameGenreAdapter(
    val artikels: ArrayList<GenresItem>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<NameGenreAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemModalBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnAdapterListener {
        fun onClick(artikel: GenresItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemModalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikes = artikels[position]
        holder.binding.tvItem.text = artikes.name
        holder.itemView.setOnClickListener {
            listener.onClick(artikes)
        }
    }

    override fun getItemCount() = artikels.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: List<GenresItem>) {
        artikels.clear()
        artikels.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        artikels.clear()
        notifyDataSetChanged()
    }

}