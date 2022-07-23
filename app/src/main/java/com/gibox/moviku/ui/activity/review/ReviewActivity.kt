package com.gibox.moviku.ui.activity.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.gibox.moviku.databinding.ActivityReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val reviewModule = module {
    factory { ReviewActivity() }
}

class ReviewActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityReviewBinding.inflate(layoutInflater)
    }
    private val viewModel: ReviewViewModel by viewModel()

    private val reviewAdapter by lazy {
        ReviewAdapter(arrayListOf())
    }

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getIntExtra("id", 0)

        viewModel.pesan.observe(this) {
            it?.let {
                Log.e("TAG", "Pesan: $it")
            }
        }

        firstLoad()
    }

    private fun firstLoad() {
        binding.scroll.scrollTo(0, 0)
        viewModel.page = 1
        viewModel.total = 1
        viewModel.getDataReview(id!!)
        getDataMovie()
    }

    @SuppressLint("LogNotTimber")
    private fun getDataMovie() {
        binding.rvReview.also {
            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.VERTICAL
            it.layoutManager = llm
        }
        binding.rvReview.adapter = reviewAdapter
        viewModel.review.observe(this) {
            Log.e("TAG", "data movie: ${it.results}")
            if (viewModel.page == 1) reviewAdapter.clear()
            reviewAdapter.addData(it.results)
        }
        binding.scroll.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false)
                    viewModel.getDataReview(id!!) else viewModel.loadMore.value == true
            }
        }
    }
}