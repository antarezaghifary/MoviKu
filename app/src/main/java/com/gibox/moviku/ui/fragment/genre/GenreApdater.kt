package com.gibox.moviku.ui.fragment.genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gibox.moviku.data.model.movie.ResultsItem
import com.gibox.moviku.databinding.ItemGenreBinding
import com.gibox.moviku.util.dateFormat
import com.gibox.moviku.util.loadImage

class GenreApdater(
    val artikels: ArrayList<ResultsItem>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<GenreApdater.ViewHolder>() {
    class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnAdapterListener {
        fun onClick(artikel: ResultsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikes = artikels[position]
        holder.binding.tvTitle.text = artikes.title
        if (artikes.releaseDate?.isEmpty() == true) {
            holder.binding.tvSubtitle.text = "No Date"
        } else {
            holder.binding.tvSubtitle.dateFormat(
                artikes.releaseDate!!,
                "yyyy-MM-dd",
                "dd MMMM yyyy"
            )
        }

        loadImage(holder.binding.ivImage, "https://image.tmdb.org/t/p/w185" + artikes.backdropPath)
        holder.itemView.setOnClickListener {
            listener.onClick(artikes)
        }
    }

    override fun getItemCount() = artikels.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: List<ResultsItem>) {
        artikels.addAll(data)
        notifyItemRangeInserted((artikels.size - data.size), data.size)
    }

    fun clear() {
        artikels.clear()
        notifyDataSetChanged()
    }

}