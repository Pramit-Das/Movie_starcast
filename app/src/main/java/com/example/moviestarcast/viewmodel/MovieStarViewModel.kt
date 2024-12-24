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


    fun loadPopularPeople(page: Int) = viewModelScope.launch {
        val response = repository.getPopularPeople(page)
        _popularPeople.value = response.results
    }

}