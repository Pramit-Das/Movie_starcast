package com.example.moviestarcast.model

data class Actor(val id: Int, val name: String, val known_for_department: String, val profile_path: String?, val known_for: List<KnownFor>)
