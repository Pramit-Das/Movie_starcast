package com.example.moviestarcast.ui.actordetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviestarcast.viewmodel.MovieStarViewModel
import com.example.moviestarcast.viewmodel.PersonDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreen(viewModel: PersonDetailsViewModel) {
    val details by viewModel.selectedPersonDetails.observeAsState()
    val images by viewModel.personImages.observeAsState(emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(details?.name ?: "Details", color = MaterialTheme.colorScheme.onPrimary) },
                Modifier.background(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick = { /* Add back navigation logic */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                details?.let {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Name: "+ it.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        it.birthday?.let { it1 ->
                            Text(
                                text = "Birth Date: " + it1,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Biography: "+it.biography,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(8.dp)) {
                    items(images) { image ->
                        Card(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${image.file_path}",
                                contentDescription = null,
                                modifier = Modifier.width(200.dp).height(200.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}