package com.gibox.moviku.ui.fragment.genre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibox.moviku.data.model.genre.GenreResponse
import com.gibox.moviku.data.model.movie.MovieResponse
import com.gibox.moviku.data.network.MovieRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module
import kotlin.math.ceil


val genreViewModel = module {
    factory {
        GenreViewModel(get())
    }
}

class GenreViewModel(

    val repository: MovieRepository

) : ViewModel() {

    val pesan by lazy {
        MutableLiveData<String>()
    }

    val loading by lazy {
        MutableLiveData<Boolean>()
    }

    val loadMore by lazy {
        MutableLiveData<Boolean>()
    }

    val movie by lazy {
        MutableLiveData<MovieResponse>()
    }

    val genre by lazy {
        MutableLiveData<GenreResponse>()
    }

    var page = 1
    var total = 1

    fun getDataMovie(withGenre: Int) {
        if (page > 1) {
            loadMore.value = true
        } else {
            loading.value = true
        }

        viewModelScope.launch {
            try {
                val response = repository.getMovie(page, withGenre)
                movie.value = response
                val totalResult: Double = response.totalResults!! / 20.0
                total = ceil(totalResult).toInt()
                page++
                loading.value = false
                loadMore.value = false
            } catch (e: Exception) {
                pesan.value = e.message.toString()
            }
        }
    }

    fun getGenre() {
        viewModelScope.launch {
            try {
                val response = repository.getGenre()
                genre.value = response
            } catch (e: Exception) {
                pesan.value = e.message.toString()
            }
        }
    }
}