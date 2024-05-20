package com.example.elaboratomobile.ui.screens.chronology

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.ui.composables.RatingBarNoClick

@Composable
fun ChronologyBookScreen(
    navController: NavHostController,
    list: List<BookChrono>
) {
    Column(modifier = Modifier.fillMaxSize()) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
            modifier = Modifier.padding()
        ) {
            items(list) { bookChrono ->
                BookItem(
                    book = bookChrono,
                    onClick = {
                        navController.navigate(
                            BookShareRoute.ChronologyDetails.buildRoute(
                                bookChrono.idPrestito
                            )
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    book: BookChrono,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .width(70.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) { // Box che riempie l'intera area disponibile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (book.copertina != null) {
                    book.copertina.let { nonNullBitmap ->
                        val imageBitmap: ImageBitmap = nonNullBitmap.asImageBitmap()
                        Image(
                            bitmap = imageBitmap,
                            contentDescription = null,
                            modifier = Modifier
                                .height(130.dp)
                                .padding(end = 30.dp)
                                .fillMaxWidth(0.4f)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Outlined.AccountBox,
                        contentDescription = "Icona del profilo",
                        modifier = Modifier.size(130.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = book.titolo)
                    Text(text = book.autore)
                    Text(text = book.genereNome)
                    RatingBarNoClick(rating = book.recensione)
                }
            }
        }

    }
}
