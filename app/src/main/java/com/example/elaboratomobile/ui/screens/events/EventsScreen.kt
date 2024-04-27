package com.example.elaboratomobile.ui.screens.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.elaboratomobile.R

@Composable
fun EventScreen(navHostController: NavHostController) {
    val lista = (1..10).toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
        modifier = Modifier.padding()
    ) {
        items(lista) {
            EventCard(item = it)
        }
    }
}

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .height(300.dp)
                .width(350.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            content() // Mostra il contenuto passato come parametro
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(item: Int) {
    var dialogOpen = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Biblioteca Malatestiana",
                style = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify
                )
            )
            Spacer(modifier = Modifier.size(15.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_malatestiana_moderna_1),
                    "logo Biblioteca",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(140.dp)
                        .padding(horizontal = 10.dp)
                )
                Button(
                    onClick = {/*TODO*/ },
                    modifier = Modifier
                        .width(130.dp)
                        .height(45.dp)
                        .padding(horizontal = 10.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add, contentDescription = "aggiungi",
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "Aggiungi al calendario", style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }

            }
            Spacer(modifier = Modifier.size(15.dp))
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 15.dp)
            ) {
                Row {
                    Text(text = "Evento:")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Titolo dell'evento")
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = "Data:")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "DD/MM/YYYY")
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = "Ora:")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "hh:mm")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "hh:mm")
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = "Presso:")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Luogo Biblioteca")
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                IconButton(
                    onClick = { dialogOpen.value = true },
                    modifier = Modifier
                        .size(55.dp)
                        .padding(horizontal = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "info"
                    )
                }
            }
            if (dialogOpen.value) {
                MinimalDialog(onDismissRequest = { dialogOpen.value = false }) {
                    // Contenuto del dialog
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            "Nome Evento",
                            style = TextStyle(
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        Image(
                            painter = painterResource(id = R.drawable.logo_malatestiana_moderna_1),
                            contentDescription = "logo biblioteca",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width(140.dp)
                                .padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        Column(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 5.dp)
                        ) {
                            Row {
                                Text(text = "Descrizione Evento:", modifier = Modifier.width(75.dp))
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(text = "descrizione...")
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Row {
                                Text(text = "Aula:")
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(text = "Aula dell'evento nella biblioteca")
                            }
                        }
                    }
                }
            }
        }
    }
}