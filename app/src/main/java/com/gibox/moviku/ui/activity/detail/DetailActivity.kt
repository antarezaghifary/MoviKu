package com.gibox.moviku.ui.activity.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gibox.moviku.R
import com.gibox.moviku.data.model.trailer.ResultsItem
import com.gibox.moviku.databinding.ActivityDetailBinding
import com.gibox.moviku.ui.activity.review.ReviewActivity
import com.gibox.moviku.ui.activity.trailer.TrailerActivity
import com.gibox.moviku.util.dateFormat
import com.gibox.moviku.util.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module


val detailModule = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModel()

    private val reviewAdapter by lazy {
        ReviewAdapter(arrayListOf())
    }

    private val trailerAdapter by lazy {
        TrailerAdapter(arrayListOf(), object : TrailerAdapter.OnAdapterListener {
            @SuppressLint("LogNotTimber")
            override fun onClick(articleModel: ResultsItem) {
                val intent = Intent(this@DetailActivity, TrailerActivity::class.java)
                intent.putExtra("id", articleModel.key)
                intent.putExtra("name", articleModel.name)
                startActivity(intent)
            }
        })
    }

    private
    var id: Int? = null

    private
    var title: String? = null

    private
    var original_title: String? = null

    private
    var image: String? = null

    private
    var poster: String? = null

    private
    var overview: String? = null

    private
    var rilis: String? = null

    private
    var vote: Double? = null

    @SuppressLint("LogNotTimber")
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )
        setContentView(binding.root)


        id = intent.getIntExtra("id", 0)

        title =
            intent.getStringExtra(
                "title"
            )
        image =
            intent.getStringExtra(
                "image"
            )
        poster =
            intent.getStringExtra(
                "poster"
            )
        overview =
            intent.getStringExtra(
                "overview"
            )
        rilis =
            intent.getStringExtra(
                "rilis"
            )
        vote = intent.getDoubleExtra("vote", 0.0)

        Log.e("TAG", "VOTE: $vote")

        with(binding) {
            setupToolbar()
        }


        viewModel.pesan.observe(this) {
            it?.let {
                Log.e("TAG", "Pesan: $it")
            }
        }

        viewModel.getDataReview(id!!)
        viewModel.getDataVideoTrailer(id!!)
        getDataReview()
        getDataVideoTrailer()
    }

    private fun setupToolbar() {
        with(binding) {
            ivClose.setOnClickListener { onBackPressed() }
            ivBack.setOnClickListener { onBackPressed() }
            tvToolbar.text =
                title
            tvEmpty.text =
                String.format(
                    getString(R.string.placeholder_empty_review),
                    title
                )
            tvEmptyTrailer.text =
                String.format(
                    getString(R.string.placeholder_empty_trailer),
                    title
                )
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    }
                }
            } else {
                window.setDecorFitsSystemWindows(
                    true
                )
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                nsContent.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                    if (scrollY > 250) {
                        toolbar.isVisible =
                            true
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_YES -> {
                                    window.decorView.systemUiVisibility =
                                        View.SYSTEM_UI_FLAG_VISIBLE
                                }
                                Configuration.UI_MODE_NIGHT_NO -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                                }
                            }
                        } else {
                            window.setDecorFitsSystemWindows(
                                true
                            )
                        }
                    } else {
                        toolbar.isVisible =
                            false
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_YES -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                                }
                                Configuration.UI_MODE_NIGHT_NO -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                                }
                            }
                        } else {
                            window.setDecorFitsSystemWindows(
                                false
                            )
                        }
                    }
                }
            }

            tvShowAll.setOnClickListener {
                val intent = Intent(this@DetailActivity, ReviewActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("title", title)
                startActivity(intent)
            }
        }
        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            loadImage(
                ivBackground,
                image!!
            )
            loadImage(
                ivImage,
                poster!!
            )

            tvTitle.text = title
            if (rilis!!.isNotEmpty()) {
                tvDate.dateFormat(
                    rilis!!,
                    "yyyy-MM-dd",
                    "dd MMMM yyyy"
                )
            } else {
                tvDate.text =
                    getString(R.string.title_unknown)
            }

            tvOverview.text =
                if (overview!!.isNotEmpty()) {
                    overview
                } else {
                    getString(R.string.title_unknown)
                }

            val newVote = vote?.toFloat()
            rbRatting.rating = (newVote!! / 2)
            tvRatting.text = (newVote / 2).toString()
        }
    }

    @SuppressLint("LogNotTimber", "SetTextI18n")
    private fun getDataReview() {
        binding.rvReview.also {
            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.VERTICAL
            it.layoutManager = llm
        }
        binding.rvReview.adapter = reviewAdapter
        viewModel.review.observe(this) {
            Log.e("TAG", "data movie: ${it.results}")
            binding.tvShowAll.visibility = if (it.results.isEmpty()) View.GONE else View.VISIBLE
            binding.tvEmpty.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.ltReviewEmpty.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            reviewAdapter.addData(it.results)
            binding.tvUserReview.text = "User Reviews (${it.results.size})"
        }
    }

    private fun getDataVideoTrailer() {
        binding.rvVideo.also {
            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            it.layoutManager = llm
        }

        binding.rvVideo.adapter = trailerAdapter
        viewModel.trailer.observe(this) {
            binding.tvEmptyTrailer.visibility =
                if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.ltReviewEmptyTrailer.visibility =
                if (it.results.isEmpty()) View.VISIBLE else View.GONE
            //binding.tvTrailer.visibility = if (it.results.isEmpty()) View.GONE else View.VISIBLE
            trailerAdapter.addData(it.results)
        }
    }
}