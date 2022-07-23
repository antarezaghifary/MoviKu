package com.gibox.moviku.ui.activity.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gibox.moviku.data.model.trailer.ResultsItem
import com.gibox.moviku.databinding.ItemTrailerBinding
import com.gibox.moviku.util.loadImage

class TrailerAdapter(
    val artikels: ArrayList<ResultsItem>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnAdapterListener {
        fun onClick(artikel: ResultsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTrailerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikes = artikels[position]
        loadImage(
            holder.binding.ivImage, "http://img.youtube.com/vi/" + artikes.key + "/0.jpg"
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