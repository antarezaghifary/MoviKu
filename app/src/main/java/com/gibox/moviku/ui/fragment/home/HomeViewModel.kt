package com.gibox.moviku.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibox.moviku.data.model.popular.PopularResponse
import com.gibox.moviku.data.model.top_rated.TopRatedResponse
import com.gibox.moviku.data.model.upcoming.UpcomingResponse
import com.gibox.moviku.data.network.MovieRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val homeViewModel = module {
    factory {
        HomeViewModel(get())
    }
}

class HomeViewModel(
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

    val upcoming by lazy {
        MutableLiveData<UpcomingResponse>()
    }

    val popular by lazy {
        MutableLiveData<PopularResponse>()
    }

    val top by lazy {
        MutableLiveData<TopRatedResponse>()
    }

    val error by lazy {
        MutableLiveData<Boolean>()
    }

    fun getDataUpcoming() {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getDataUpcoming()
                upcoming.value = response
                loading.value = false
            } catch (e: Throwable) {
                pesan.value = e.message.toString()
                error.value = true
            }
        }
    }


    fun getDataPopular() {
        viewModelScope.launch {
            try {
                val response = repository.getDataPopuar()
                popular.value = response
            } catch (e: Exception) {
                pesan.value = e.message.toString()
            }
        }
    }

    fun getDataTop() {
        viewModelScope.launch {
            try {
                val response = repository.getDataTop()
                top.value = response
            } catch (e: Exception) {
                pesan.value = e.message.toString()
            }
        }
    }

}