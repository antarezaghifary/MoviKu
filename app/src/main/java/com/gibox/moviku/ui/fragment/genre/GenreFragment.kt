package com.gibox.moviku.ui.fragment.genre

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gibox.moviku.R
import com.gibox.moviku.data.model.movie.ResultsItem
import com.gibox.moviku.databinding.CustomToolbarGenreBinding
import com.gibox.moviku.databinding.FragmentGenreBinding
import com.gibox.moviku.databinding.ItemBottomsheetBinding
import com.gibox.moviku.ui.activity.detail.DetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val genreModule = module {
    factory { GenreFragment() }
}

class GenreFragment : Fragment() {

    private lateinit var binding: FragmentGenreBinding

    private lateinit var bindingToolbar: CustomToolbarGenreBinding

    private lateinit var bottomSheet: ItemBottomsheetBinding

    private val viewModel: GenreViewModel by viewModel()

    private fun firstLoad() {
        binding.scroll.scrollTo(0, 0)
        viewModel.page = 1
        viewModel.total = 1
        viewModel.getDataMovie(28)
    }

    private val genreAdapter by lazy {
        GenreApdater(arrayListOf(), object : GenreApdater.OnAdapterListener {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingToolbar = binding.toolbar
        binding.viewModel = viewModel
        firstLoad()
        getDataMovie()
        viewModel.pesan.observe(viewLifecycleOwner) {
            it?.let {
                Log.e("TAG", "Pesan: $it")
            }
        }

        bindingToolbar.filterGenre.setOnClickListener {
            Log.e("TAG", "Klik filter: ")
            val dialog = BottomSheetDialog(requireContext(), R.style.BaseBottomSheetDialog)
            bottomSheet.close.setOnClickListener {
                dialog.dismiss()
            }

            val data =
                arrayOf(
                    "Maluku Utara",
                    "Maluku",
                    "Sulawesi Barat"
                )
            bottomSheet.genrePicker.minValue = 1
            bottomSheet.genrePicker.maxValue = data.size
            bottomSheet.genrePicker.displayedValues = data
            bottomSheet.genrePicker.value = data.size

        }
    }

    @SuppressLint("LogNotTimber")
    private fun getDataMovie() {
        binding.rvFilm.also {
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.rvFilm.adapter = genreAdapter

        viewModel.movie.observe(viewLifecycleOwner) {
            Log.e("TAG", "data movie: ${it.results}")
            if (viewModel.page == 1) genreAdapter.clear()
            binding.imageAlert.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.textAlert.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            binding.progressTop.visibility = if (it.results.isEmpty()) View.VISIBLE else View.GONE
            genreAdapter.addData(it.results)
        }
        binding.scroll.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false)
                    viewModel.getDataMovie(28) else viewModel.loadMore.value == true
            }
        }
    }
}