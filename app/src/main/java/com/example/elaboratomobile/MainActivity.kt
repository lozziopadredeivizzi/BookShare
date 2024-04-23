package com.example.elaboratomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.elaboratomobile.ui.BookShareNavGraph
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.ui.composables.AppBar
import com.example.elaboratomobile.ui.theme.ElaboratoMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElaboratoMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute by remember {
                        derivedStateOf {
                            BookShareRoute.routes.find {
                                it.route == backStackEntry?.destination?.route
                            } ?: BookShareRoute.Login
                        }
                    }
                    Scaffold (
                        topBar = { AppBar(navController = navController, currentRoute = currentRoute)}
                    ) {contentPadding ->
                        BookShareNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(contentPadding)
                        )
                    }


                }
            }
        }
    }
}
