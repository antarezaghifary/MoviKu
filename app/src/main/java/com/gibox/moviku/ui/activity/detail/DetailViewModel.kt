package com.gibox.moviku.ui.activity.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibox.moviku.data.model.review.ReviewResponse
import com.gibox.moviku.data.model.trailer.TrailerResponse
import com.gibox.moviku.data.network.MovieRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val detailViewModel = module {
    factory {
        DetailViewModel(get())
    }
}

class DetailViewModel(
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

    val review by lazy {
        MutableLiveData<ReviewResponse>()
    }

    val trailer by lazy {
        MutableLiveData<TrailerResponse>()
    }

    fun getDataReview(movieID: Int) {

        loading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getReview(movieID)
                review.value = response
                loading.value = false
            } catch (e: Exception) {
                pesan.value = e.message.toString()
            }
        }
    }

    fun getDataVideoTrailer(movieID: Int) {

        loading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getVideoTrailer(movieID)
                trailer.value = response
                loading.value = false
            } catch (e: Exception) {
                pesan.value = e.message.toString()
            }
        }
    }

}