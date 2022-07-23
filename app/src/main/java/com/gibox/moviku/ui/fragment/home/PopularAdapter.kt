package com.gibox.moviku.ui.fragment.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gibox.moviku.data.model.popular.ResultsItem
import com.gibox.moviku.databinding.ItemPopularBinding
import com.gibox.moviku.util.dateFormat
import com.gibox.moviku.util.loadImage

class PopularAdapter(
    val artikels: ArrayList<ResultsItem>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnAdapterListener {
        fun onClick(artikel: ResultsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPopularBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikes = artikels[position]
        loadImage(
            holder.binding.ivImage,
            "https://image.tmdb.org/t/p/w185" + artikes.posterPath
        )
        holder.binding.tvTitle.text = artikes.title
        holder.binding.tvSubtitle.dateFormat(
            artikes.releaseDate!!,
            "yyyy-MM-dd",
            "dd MMMM yyyy"
        )
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