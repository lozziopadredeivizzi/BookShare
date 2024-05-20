package com.example.elaboratomobile.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
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
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    currentRoute: BookShareRoute,
    getCountNotific: () -> Unit,
    countNotific: Int
) {
    if(currentRoute.route == BookShareRoute.HomeBooks.route) getCountNotific()
    if (!BookShareRoute.noAppBar.contains(currentRoute)) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    currentRoute.title,
                    fontWeight = FontWeight.Medium,
                )
            },
            navigationIcon = {
                if (currentRoute.route == BookShareRoute.BookDetails.route ||
                    currentRoute.route == BookShareRoute.FavoriteBooks.route ||
                    currentRoute.route == BookShareRoute.Settings.route ||
                    currentRoute.route == BookShareRoute.Aspetto.route ||
                    currentRoute.route == BookShareRoute.Chronology.route ||
                    currentRoute.route == BookShareRoute.ChronologyDetails.route ||
                    currentRoute.route == BookShareRoute.ModificaProfilo.route ||
                    currentRoute.route == BookShareRoute.ModificaPassword.route ||
                    currentRoute.route == BookShareRoute.ModificaEmail.route ||
                    currentRoute.route == BookShareRoute.Notification.route
                ) {
                    IconButton(onClick = {
                        if (currentRoute.route == BookShareRoute.Chronology.route) navController.navigate(BookShareRoute.Profile.route)
                        else if(currentRoute.route == BookShareRoute.ModificaProfilo.route) navController.navigate(BookShareRoute.Settings.route)
                        else if(currentRoute.route == BookShareRoute.Settings.route) navController.navigate(BookShareRoute.Profile.route)
                        else navController.navigateUp()

                    }) {
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
                        Icon(
                            contentDescription = "Preferiti",
                            tint = MaterialTheme.colorScheme.tertiary,
                            imageVector = Icons.Outlined.Favorite,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate(BookShareRoute.Notification.route) }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Icona Notifiche")

                        if (countNotific > 0) {
                            Badge(
                                content = { Text(text = "$countNotific", color = Color.White) },
                                containerColor = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.padding(start = 17.dp, bottom = 6.dp)
                            )
                        }
                    }
                }
                if (currentRoute.route == BookShareRoute.Profile.route) {
                    IconButton(onClick = { navController.navigate(BookShareRoute.Chronology.route) }) {
                        Icon(
                            contentDescription = "Cronologia",
                            tint = Color.Black,
                            imageVector = Icons.Outlined.AccessTime,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate(BookShareRoute.Settings.route) }) {
                        Icon(
                            contentDescription = "Impostazioni",
                            tint = Color.Black,
                            imageVector = Icons.Outlined.Settings,
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