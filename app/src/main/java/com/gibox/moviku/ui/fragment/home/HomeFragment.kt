package com.gibox.moviku.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gibox.moviku.R
import com.gibox.moviku.data.model.upcoming.ResultsItem
import com.gibox.moviku.databinding.CustomToolbarBinding
import com.gibox.moviku.databinding.FragmentHomeBinding
import com.gibox.moviku.ui.activity.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module


val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var toolbar: CustomToolbarBinding

    private val viewModel: HomeViewModel by viewModel()

    private val upcomingAdapter by lazy {
        UpcomingAdapter(arrayListOf(), object : UpcomingAdapter.OnAdapterListener {
            @SuppressLint("LogNotTimber")
            override fun onClick(articleModel: ResultsItem) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", articleModel.id)
                intent.putExtra("title", articleModel.title)
                intent.putExtra("original_title", articleModel.originalTitle)
                intent.putExtra(
                    "image",
                    "https://image.tmdb.org/t/p/w185" + articleModel.backdropPath
                )
                intent.putExtra(
                    "poster",
                    "https://image.tmdb.org/t/p/w185" + articleModel.posterPath
                )
                intent.putExtra("overview", articleModel.overview)
                intent.putExtra("rilis", articleModel.releaseDate)
                intent.putExtra("vote", articleModel.voteAverage)
                startActivity(intent)
            }
        })
    }

    private val popularAdapter by lazy {
        PopularAdapter(arrayListOf(), object : PopularAdapter.OnAdapterListener {
            @SuppressLint("LogNotTimber")
            override fun onClick(articleModel: com.gibox.moviku.data.model.popular.ResultsItem) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", articleModel.id)
                intent.putExtra("title", articleModel.title)
                intent.putExtra("original_title", articleModel.originalTitle)
                intent.putExtra(
                    "image",
                    "https://image.tmdb.org/t/p/w185" + articleModel.backdropPath
                )
                intent.putExtra(
                    "poster",
                    "https://image.tmdb.org/t/p/w185" + articleModel.posterPath
                )
                intent.putExtra("overview", articleModel.overview)
                intent.putExtra("rilis", articleModel.releaseDate)
                intent.putExtra("vote", articleModel.voteAverage)
                startActivity(intent)
            }
        })
    }

    private val topAdapter by lazy {
        TopAdapter(arrayListOf(), object : TopAdapter.OnAdapterListener {
            @SuppressLint("LogNotTimber")
            override fun onClick(articleModel: com.gibox.moviku.data.model.top_rated.ResultsItem) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", articleModel.id)
                intent.putExtra("title", articleModel.title)
                intent.putExtra("original_title", articleModel.originalTitle)
                intent.putExtra(
                    "image",
                    "https://image.tmdb.org/t/p/w185" + articleModel.backdropPath
                )
                intent.putExtra(
                    "poster",
                    "https://image.tmdb.org/t/p/w185" + articleModel.posterPath
                )
                intent.putExtra("overview", articleModel.overview)
                intent.putExtra("rilis", articleModel.releaseDate)
                intent.putExtra("vote", articleModel.voteAverage)
                startActivity(intent)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        return binding.root
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.tvTitle.text = "Home"

        viewModel.getDataUpcoming()
        viewModel.getDataPopular()
        viewModel.getDataTop()

        getDataUpcoming()
        getDataPopular()
        getDataTop()

        viewModel.pesan.observe(viewLifecycleOwner) {
            it?.let {
                Log.e("TAG", "Pesan: ${it}")

            }
        }

        binding.tvEmpty.text =
            String.format(
                getString(R.string.placeholder_empty_cs),
                "Coming Soon"
            )

        binding.tvEmptyPopular.text =
            String.format(
                getString(R.string.placeholder_empty_pm),
                "Popular"
            )

        binding.tvEmptyTop.text =
            String.format(
                getString(R.string.placeholder_empty_tm),
                "Top"
            )
    }


    @SuppressLint("LogNotTimber")
    private fun getDataUpcoming() {
        binding.rvUpcoming.also {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            it.layoutManager = llm
        }

        binding.rvUpcoming.adapter = upcomingAdapter
        viewModel.upcoming.observe(viewLifecycleOwner) {
            Log.e("TAG", "data upcoming: ${it.results}")
            binding.progressTop.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.tvEmpty.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.ltReviewEmpty.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            upcomingAdapter.clear()
            upcomingAdapter.addData(it.results)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (error) {
                    Log.e("TAG", "getDataUpcoming Eror: $error")
                }
            }
        }
    }


    private fun getDataPopular() {
        binding.rvPopular.also {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            it.layoutManager = llm
        }

        binding.rvPopular.adapter = popularAdapter
        viewModel.popular.observe(viewLifecycleOwner) {
            binding.tvEmptyPopular.visibility =
                if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.ltReviewEmptyPopular.visibility =
                if (it.results.isEmpty()) View.VISIBLE else View.GONE
            popularAdapter.clear()
            popularAdapter.addData(it.results)
        }
    }

    private fun getDataTop() {
        binding.rvTop.also {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            it.layoutManager = llm
        }

        binding.rvTop.adapter = topAdapter
        viewModel.top.observe(viewLifecycleOwner) {
            binding.tvEmptyTop.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.ltReviewEmptyTop.visibility =
                if (it.results.isEmpty()) View.VISIBLE else View.GONE
            topAdapter.clear()
            topAdapter.addData(it.results)
        }
    }
}