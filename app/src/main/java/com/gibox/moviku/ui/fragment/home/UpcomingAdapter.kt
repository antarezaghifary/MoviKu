package com.gibox.moviku.ui.fragment.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gibox.moviku.data.model.upcoming.ResultsItem
import com.gibox.moviku.databinding.ItemUpcomingBinding
import com.gibox.moviku.util.loadImage

class UpcomingAdapter(
    val artikels: ArrayList<ResultsItem>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUpcomingBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnAdapterListener {
        fun onClick(artikel: ResultsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemUpcomingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikes = artikels[position]
        loadImage(
            holder.binding.imgUpcoming,
            "https://image.tmdb.org/t/p/w185" + artikes.backdropPath
        )
        holder.binding.tvTitle.text = artikes.title
        holder.itemView.setOnClickListener {
            listener.onClick(artikes)
        }
    }

    override fun getItemCount() = artikels.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: List<ResultsItem>) {
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