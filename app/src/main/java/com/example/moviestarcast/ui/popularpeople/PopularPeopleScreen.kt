package com.example.moviestarcast.ui.popularpeople

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviestarcast.viewmodel.MovieStarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularPeopleScreen(viewModel: MovieStarViewModel, onPersonClick: (Int) -> Unit) {
    val people by viewModel.popularPeople.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val isLoading by viewModel.isLoading.observeAsState(false)
    val currentPage by viewModel.currentPage.observeAsState(1)

    Scaffold(topBar = {
        TopAppBar(title = { Text("Popular People") })
    }) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(padding)) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    viewModel.setSearchQuery(it)
                    if (it.isNotEmpty()) {
                        viewModel.searchPeople(it, 1)
                    } else {
                        viewModel.resetPeople()
                        viewModel.loadPopularPeople(1)
                    }
                },
                placeholder = { Text("Search People") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                if(people.isEmpty()){
                    viewModel.loadPopularPeople(1)
                }
                items(people) { actor ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPersonClick(actor.id) }
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${actor.profile_path}",
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = actor.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

            }

            LaunchedEffect(Unit) {
                viewModel.loadPopularPeople(currentPage)
            }

            val lazyListState = rememberLazyListState()
            LazyColumn(state = lazyListState) {
                itemsIndexed(people) { index, _ ->
                    if (index == people.lastIndex && !isLoading) {
                        viewModel.loadPopularPeople(currentPage + 1)
                    }
                }
            }
        }
    }
}