package com.example.elaboratomobile.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    currentRoute: BookShareRoute
) {
    if(!BookShareRoute.noAppBar.contains(currentRoute)) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    currentRoute.title,
                    fontWeight = FontWeight.Medium,
                )
            },
            navigationIcon = {
                if (currentRoute.route == BookShareRoute.BookDetails.route ||
                    currentRoute.route == BookShareRoute.FavoriteBooks.route) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            },
            actions = {
                      if (currentRoute.route == BookShareRoute.HomeBooks.route) {
                          IconButton(onClick = { navController.navigate(BookShareRoute.FavoriteBooks.route) }) {
                              Icon(contentDescription = "Preferiti",
                                  tint = Color.Red,
                                  imageVector = Icons.Outlined.Favorite,
                                  modifier = Modifier.size(30.dp)
                              )
                          }
                      }

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}