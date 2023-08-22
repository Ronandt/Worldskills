package com.example.worldskills

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.ui.theme.WorldskillsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var navController = rememberNavController()

            WorldskillsTheme {
                Scaffold {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(navController = navController, startDestination = "login" ) {
                            composable("login") {
                               LoginScreen({navController.navigate("home")}, context = applicationContext)
                            }
                            composable("home") {
                                HomeScreen({navController.navigate("feedback")}, {navController.navigate("checkout")}, context = applicationContext)
                            }
                            composable("feedback") {
                                FeedbackScreen({navController.navigateUp()})
                            }
                            composable("checkout") {
                                CheckoutScreen({navController.navigateUp()}, LocalContext.current, navigateOrder = {navController.navigate("home")})
                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier,
        color = Color.Red
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorldskillsTheme {
        Greeting("Andrfoid")
    }
}