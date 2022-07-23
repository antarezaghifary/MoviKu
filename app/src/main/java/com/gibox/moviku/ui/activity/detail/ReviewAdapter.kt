package com.gibox.moviku.ui.activity.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gibox.moviku.data.model.review.ResultsItem
import com.gibox.moviku.databinding.ItemReviewBinding
import com.gibox.moviku.util.loadImage

class ReviewAdapter(
    val artikels: ArrayList<ResultsItem>
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikes = artikels[position]
        holder.binding.tvName.text = artikes.authorDetails!!.username
        holder.binding.tvContent.text = artikes.content
        val url = artikes.authorDetails.avatarPath
        val remove = 1
        url?.substring(remove)?.let { loadImage(holder.binding.ivImage, it) }
    }

    override fun getItemCount(): Int {
        return when (artikels.size > 3) {
            true -> 3
            false -> artikels.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: List<ResultsItem>) {
        artikels.clear()
        artikels.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        artikels.clear()
        notifyDataSetChanged()
    }

}