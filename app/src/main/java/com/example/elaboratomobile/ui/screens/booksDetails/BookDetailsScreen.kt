package com.example.elaboratomobile.ui.screens.booksDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.R
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.ui.composables.RatingBarNoClick

@Composable
fun BookDetailsScreen(navController: NavHostController) {
    val lista = (1..10).toList()
    var selectedItem by remember { mutableStateOf<Int?>(null) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp, vertical = 16.dp
                )
                .fillMaxSize(), colors = CardDefaults.cardColors(
                containerColor = Color.White
            ), border = BorderStroke(1.dp, Color.Gray)
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
                        .padding(end = 30.dp)
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
                    RatingBarNoClick(rating = 3.5)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .padding(16.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                Text(
                    fontSize = 13.sp,
                    text = "Lorem ipsum dolor sit amet consectetur adipisicing elit.  " + "Et odit optio reprehend quas nihil."
                            + "Lorem ipsum dolor sit amet consectetur adipisicing elit.  " + "Et odit optio reprehend quas nihil."
                            + "Lorem ipsum dolor sit amet consectetur adipisicing elit.  " + "Et odit optio reprehend quas nihil."
                )

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Scegli una Biblioteca:", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.size(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Padding around the LazyColumn
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)) // Border for the LazyColumn
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 8.dp),
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .padding()
                ) {
                    items(lista) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedItem = it }
                                .background(if (it == selectedItem) Color.Cyan else Color.Transparent) // Change color if selected
                        ) {
                            Text(text = it.toString(), modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }

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
                    text = "Ritirare il libro entro una settimana dalla richiesta di prestito",
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate(BookShareRoute.HomeBooks.route) },
                    modifier = Modifier.width(200.dp),
                    border = BorderStroke(1.dp, Color.Blue)
                ) {
                    Text(
                        "Richiedi Prestito", color = Color.Black
                    )
                }
            }
        }
    }
}