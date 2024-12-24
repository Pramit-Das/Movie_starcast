package com.example.moviestarcast.network

import com.example.moviestarcast.model.ActorDetailsResponse
import com.example.moviestarcast.model.ActorImagesResponse
import com.example.moviestarcast.model.PopularActorsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieStarApi {
    @GET("person/popular")
    suspend fun getPopularPeople(@Query("page") page: Int): PopularActorsResponse

    @GET("search/person")
    suspend fun searchPeople(@Query("query") query: String, @Query("page") page: Int): PopularActorsResponse

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") id: Int): ActorDetailsResponse

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(@Path("person_id") id: Int): ActorImagesResponse
}