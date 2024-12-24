package com.example.moviestarcast.model

data class ActorDetailsResponse(val id: Int, val name: String, val biography: String,
                                val birthday: String?,val profile_path: String, val place_of_birth: String?)

