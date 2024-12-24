package com.example.moviestarcast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestarcast.model.Actor
import com.example.moviestarcast.model.ActorDetailsResponse
import com.example.moviestarcast.model.ImageProfile
import com.example.moviestarcast.repository.MovieStarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieStarViewModel @Inject constructor(private val repository: MovieStarRepository) : ViewModel() {
    private val _popularPeople = MutableLiveData<List<Actor>>()
    val popularPeople: LiveData<List<Actor>> = _popularPeople

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currentPage = MutableLiveData(1)
    val currentPage: LiveData<Int> = _currentPage

    private var isLastPage = false


    fun loadPopularPeople(page: Int) {
        if (_isLoading.value == true || isLastPage) return // Prevent multiple concurrent requests
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getPopularPeople(page)
                if (response.results.isNotEmpty()) {
                    _popularPeople.value = (_popularPeople.value ?: emptyList()) + response.results
                    _currentPage.value = response.page
                }
                if (response.page >= response.total_pages) {
                    isLastPage = true
                }
            } catch (e: Exception) {
                // Handle exception
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetPeople() {
        _popularPeople.value = emptyList()
        _currentPage.value = 1
    }

    fun searchPeople(query: String, page: Int) {
        if (_isLoading.value == true) return // Prevent multiple concurrent requests
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.searchPeople(query, page)
                if (page == 1) {
                    _popularPeople.value = response.results
                } else {
                    _popularPeople.value = (_popularPeople.value ?: emptyList()) + response.results
                }
                if (response.page >= response.total_pages) {
                    isLastPage = true
                }
            } catch (e: Exception) {
                // Handle exception
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setSearchQuery(query: String) {
        if (_searchQuery.value != query) {
            _searchQuery.value = query
            _popularPeople.value = emptyList() // Reset the people list
            isLastPage = false
            loadPopularPeople(1) // Reset and reload
        }
    }

}