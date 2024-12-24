package com.example.moviestarcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviestarcast.ui.popularpeople.PopularPeopleScreen
import com.example.moviestarcast.ui.splash.SplashScreen
import com.example.moviestarcast.ui.theme.MovieStarcastTheme
import com.example.moviestarcast.viewmodel.MovieStarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen { navController.navigate("main") }
                }
                composable("main") {
                    PopularPeopleScreen(viewModel = hiltViewModel()) { personId ->
                        navController.navigate("details/$personId")
                    }
                }

            }
        }
    }
}

