package com.gibox.moviku.ui.activity.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gibox.moviku.databinding.FragmentReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReviewFragment : Fragment() {

    private lateinit var binding: FragmentReviewBinding

    private val viewModel: ReviewViewModel by viewModel()

    private val reviewAdapter by lazy {
        ReviewAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pesan.observe(viewLifecycleOwner) {
            it?.let {
                Log.e("TAG", "Pesan: $it")
            }
        }
    }

    private fun firstLoad() {
        binding.scroll.scrollTo(0, 0)
        viewModel.page = 1
        viewModel.total = 1
        viewModel.getDataReview(28)
    }

    @SuppressLint("LogNotTimber")
    private fun getDataMovie() {
        binding.rvReview.also {
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.rvReview.adapter = reviewAdapter

        viewModel.review.observe(viewLifecycleOwner) {
            Log.e("TAG", "data movie: ${it.results}")
            if (viewModel.page == 1) reviewAdapter.clear()
            reviewAdapter.addData(it.results)
        }
        binding.scroll.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false)
                    viewModel.getDataReview(28) else viewModel.loadMore.value == true
            }
        }
    }

}