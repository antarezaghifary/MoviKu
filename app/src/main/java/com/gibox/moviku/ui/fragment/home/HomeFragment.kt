package com.gibox.moviku.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gibox.moviku.data.model.upcoming.ResultsItem
import com.gibox.moviku.databinding.CustomToolbarBinding
import com.gibox.moviku.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module


val homeModule = module {
    factory { HomeFragment() }
}

private val upcomingAdapter by lazy {
    UpcomingAdapter(arrayListOf(), object : UpcomingAdapter.OnAdapterListener {
        @SuppressLint("LogNotTimber")
        override fun onClick(articleModel: ResultsItem) {

        }
    })
}

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var toolbar: CustomToolbarBinding

    private val viewModel: HomeViewModel by viewModel()

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

        viewModel.getData()

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvUpcoming.layoutManager = llm
        binding.rvUpcoming.adapter = upcomingAdapter
        viewModel.upcoming.observe(viewLifecycleOwner) {
            Log.e("TAG", "data berita: ${it.results}")
            upcomingAdapter.clear()
            upcomingAdapter.addData(it.results)
        }


        viewModel.pesan.observe(viewLifecycleOwner) {
            it?.let {
                Log.e("TAG", "Pesan: ${it}")
            }
        }


    }
}