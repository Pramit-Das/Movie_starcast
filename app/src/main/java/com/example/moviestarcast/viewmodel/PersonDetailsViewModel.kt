package com.example.moviestarcast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestarcast.model.ActorDetailsResponse
import com.example.moviestarcast.model.ImageProfile
import com.example.moviestarcast.repository.MovieStarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel  @Inject constructor(private val repository: MovieStarRepository) : ViewModel(){
    private val _selectedPersonDetails = MutableLiveData<ActorDetailsResponse>()
    val selectedPersonDetails: LiveData<ActorDetailsResponse> = _selectedPersonDetails

    private val _personImages = MutableLiveData<List<ImageProfile>>()
    val personImages: LiveData<List<ImageProfile>> = _personImages

    fun loadPersonDetails(id: Int) {
        viewModelScope.launch {
            _selectedPersonDetails.value = repository.getPersonDetails(id)
            loadPersonImages(id) // Load images for the person
        }
    }

    fun loadPersonImages(id: Int) {
        viewModelScope.launch {
            try {
                val imagesResponse = repository.getPersonImages(id)
                _personImages.value = imagesResponse.profiles
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

}