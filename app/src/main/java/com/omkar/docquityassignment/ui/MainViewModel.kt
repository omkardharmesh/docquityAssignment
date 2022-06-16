package com.omkar.docquityassignment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkar.docquityassignment.model.Post
import com.omkar.docquityassignment.repository.Repository
import com.omkar.docquityassignment.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(val repository: Repository) : ViewModel() {

    val postItem: MutableLiveData<Resource<Post>> = MutableLiveData()


    fun getPostDetails(postId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val rawResponse = repository.getPost(postId)
        postItem.postValue(handleResponse(rawResponse))
    }

    private fun handleResponse(rawResponse: Response<Post>): Resource<Post> {
        if (rawResponse.isSuccessful) {
            rawResponse.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(rawResponse.message())
    }

}