package com.gibox.moviku.ui.activity.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibox.moviku.data.model.review.ReviewResponse
import com.gibox.moviku.data.network.MovieRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module
import kotlin.math.ceil


val reviewViewModel = module {
    factory {
        ReviewViewModel(get())
    }
}

class ReviewViewModel(
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

    var page = 1
    var total = 1

    fun getDataReview(movieID: Int) {
        if (page > 1) {
            loadMore.value = true
        } else {
            loading.value = true
        }

        viewModelScope.launch {
            try {
                val response = repository.getReviewAll(movieID, page)
                review.value = response
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
}