package com.example.moviestarcast.model

data class PopularActorsResponse(val page: Int, val results: List<Actor>, val total_pages: Int, val total_results: Int)
