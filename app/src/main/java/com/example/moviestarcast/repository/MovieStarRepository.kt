package com.example.moviestarcast.repository

import com.example.moviestarcast.network.MovieStarApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieStarRepository @Inject constructor(private val api: MovieStarApi) {
    suspend fun getPopularPeople(page: Int) = api.getPopularActors(page)
    suspend fun searchPeople(query: String, page: Int) = api.searchActor(query, page)
    suspend fun getPersonDetails(id: Int) = api.getPersonDetails(id)
    suspend fun getPersonImages(id: Int) = api.getPersonImages(id)
}
