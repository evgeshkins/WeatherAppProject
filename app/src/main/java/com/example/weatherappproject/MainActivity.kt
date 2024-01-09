package com.example.weatherappproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherappproject.ui.theme.WeatherAppProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "base_screen") {
                composable("base_screen") {
                    BaseScreen(
                        onClick = { cityName ->
                            navController.navigate("main_screen/$cityName")
                        }
                    )
                }
                composable(
                    route = "main_screen/{cityName}",
                    arguments = listOf(navArgument("cityName") { type = NavType.StringType })
                ) { navBackStackEntry ->
                    val cityName = navBackStackEntry.arguments?.getString("cityName") ?: ""
                    MainScreen(onClick = {},cityName = cityName)
                }
            }
        }
    }
}
