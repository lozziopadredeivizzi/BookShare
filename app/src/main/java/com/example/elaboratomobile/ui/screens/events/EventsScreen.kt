package com.example.elaboratomobile.ui.screens.events

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.elaboratomobile.utils.aggiungiEventoAlCalendario
import com.example.elaboratomobile.utils.rememberPermission

@Composable
fun EventScreen(
    navHostController: NavHostController,
    list: List<EventState>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
        modifier = Modifier.padding()
    ) {
        items(list) { event ->
            EventCard(event = event)
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


@Composable
fun EventCard(event: EventState) {
    var dialogOpen = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    //CALENDARIO
    val calendarPermission = rememberPermission(Manifest.permission.READ_CALENDAR) { status ->
        if (status.isGranted) {
            aggiungiEventoAlCalendario(context, event)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun addCalendar() {
        if (calendarPermission.status.isGranted) {
            aggiungiEventoAlCalendario(context, event)
        } else {
            calendarPermission.launchPermissionRequest()
        }
    }

    val scrollState = rememberScrollState()
    val colorCard = MaterialTheme.colorScheme.onPrimary
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .height(270.dp),
        colors = CardDefaults.cardColors(containerColor = colorCard),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = event.nomeBiblioteca,
                style = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    if (event.immagineBiblio != null) {
                        event.immagineBiblio.let { nonNullBitmap ->
                            val imageBitmap: ImageBitmap = nonNullBitmap.asImageBitmap()
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(140.dp)
                                    .padding(horizontal = 10.dp)
                                    .fillMaxHeight(0.3f)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AccountBox,
                            contentDescription = "Icona del profilo",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                Button(
                    onClick = ::addCalendar,
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
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "Aggiungi al calendario", style = TextStyle(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface,
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
                    Text(text = "Evento:", color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = event.titolo)
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = "Data:", color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = event.data)
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = "Ora:", color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = event.ora)
                }
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    Text(text = "Presso:", color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = event.indirizzoBiblio)
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
                            event.titolo,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        if (event.immagineBiblio != null) {
                            event.immagineBiblio.let { nonNullBitmap ->
                                val imageBitmap: ImageBitmap = nonNullBitmap.asImageBitmap()
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(140.dp)
                                        .padding(horizontal = 10.dp)
                                        .fillMaxHeight(0.3f)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AccountBox,
                                contentDescription = "Icona del profilo",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                        Column(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 5.dp)
                        ) {
                            Row {
                                Text(text = "Aula:", color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    text = event.aula ?: run { "/" },
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Row {
                                Text(
                                    text = "Descrizione Evento:",
                                    modifier = Modifier.width(75.dp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Box(
                                    modifier = Modifier
                                        .verticalScroll(scrollState)
                                        .fillMaxHeight(0.1f)
                                ) {
                                    Text(
                                        text = event.descrizione,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}