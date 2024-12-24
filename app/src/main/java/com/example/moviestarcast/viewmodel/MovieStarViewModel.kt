package com.example.moviestarcast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestarcast.model.Actor
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

    fun loadPopularPeople(page: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getPopularPeople(page)
                _popularPeople.value = (_popularPeople.value ?: emptyList()) + response.results
                _currentPage.value = page
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetPeople() {
        _popularPeople.value = emptyList()
        _currentPage.value = 1
    }

    fun searchPeople(query: String, page: Int) = viewModelScope.launch {
        val response = repository.searchPeople(query, page)
        _popularPeople.value = response.results
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}