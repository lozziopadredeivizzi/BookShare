package com.example.elaboratomobile.ui.screens.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val colorCard = MaterialTheme.colorScheme.onPrimary
    Card(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .width(70.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorCard
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 8.dp, 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        tint = Color.Black,
                        contentDescription = "Warning",
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Il tuo prestito è in scadenza oggi",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 8.dp, 8.dp)
                ){
                    Text(text = "Libro:", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(text = notific.titoloLibro, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.size(3.dp))
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 8.dp, 8.dp)
                ){
                    Text(text = "Presso:", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(text = notific.nomeBiblioteca, color = MaterialTheme.colorScheme.onSurface)
                }

            }
        }
    }
}