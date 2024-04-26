package com.example.elaboratomobile.ui.screens.registrazione

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun RegistrazioneScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(36.dp))
        Text(
            text = "BookShare",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp
            )
        )
        Spacer(modifier = Modifier.size(6.dp))
        Box {
            Image(
                Icons.Outlined.AccountBox,
                "pfp",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.BottomEnd) // Posiziona l'IconButton nell'angolo in basso a destra
                    .background(Color.White, shape = CircleShape)
                    .padding(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ModeEdit,
                    contentDescription = "modifica pfp",
                )
            }
        }
        Spacer(modifier = Modifier.size(6.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Nome") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {/*TODO*/ },
            label = { Text(text = "Cognome") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Data Di Nascita") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("E-mail") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.size(36.dp))
        Button(
            onClick = { navController.navigate(BookShareRoute.HomeBooks.route) },
            modifier = Modifier
                .width(150.dp),
            border = BorderStroke(1.dp, Color.Blue)
        ) {
            Text(
                text = "Registrati",
                color = Color.Black
            )
        }
    }
}