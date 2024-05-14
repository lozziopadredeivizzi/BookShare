package com.example.elaboratomobile.ui.screens.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NotificationScreen(list: List<Notification>) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
            modifier = Modifier.padding()
        ) {
            items(list) { notification ->
                NotificItem(notific = notification)
            }
        }
    }
}

@Composable
fun NotificItem(
    notific: Notification
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .width(70.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Il libro ${notific.titoloLibro}")
                Text(text = "preso da ${notific.nomeBiblioteca}")
                Text(text = "è in scadenza oggi")
            }
        }
    }
}