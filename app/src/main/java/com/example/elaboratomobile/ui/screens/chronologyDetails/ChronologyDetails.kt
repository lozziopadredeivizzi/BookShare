package com.example.elaboratomobile.ui.screens.chronologyDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.R
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.ui.composables.RatingBar

@Composable
fun ChronologyDetails(navController: NavHostController) {
    var rating by remember {
        mutableDoubleStateOf(0.0)
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),

    ) {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp, vertical = 16.dp
                )
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.copertina),
                    contentDescription = "Cover",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(130.dp)
                        .padding(end = 30.dp, start = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Titolo")
                    Text(text = "Autore")
                    Text(text = "Genere")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .padding(16.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Dettagli prestito", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.size(15.dp))
                    Row {
                        Text(text = "Presso:    ", fontWeight = FontWeight.Bold)
                        Text(text = "Biblioteca Malatestiana")
                    }
                    Spacer(modifier = Modifier.size(15.dp))
                    Row {
                        Text(text = "Data Inizio:   ", fontWeight = FontWeight.Bold)
                        Text(text = "27/04/2024")
                    }
                    Spacer(modifier = Modifier.size(15.dp))
                    Row {
                        Text(text = "Data Fine:    ", fontWeight = FontWeight.Bold)
                        Text(text = "27/05/2024")
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                RatingBar(rating = rating) {
                    rating = it
                }
                Spacer(modifier = Modifier.size(20.dp))
                // Esiste il bottone solo se in quel libro non l'ho mai valutato
                Button(
                    onClick = { navController.navigate(BookShareRoute.Chronology.route) },
                    modifier = Modifier.width(100.dp),
                    border = BorderStroke(1.dp, Color.Blue)
                ) {
                    Text(
                        "Valuta", color = Color.Black
                    )
                }
            }

        }
    }
}