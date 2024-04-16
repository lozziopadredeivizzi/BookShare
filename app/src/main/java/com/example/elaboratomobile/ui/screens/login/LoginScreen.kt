package com.example.elaboratomobile.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {
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
                fontSize = 34.sp,
            )
        )
        Spacer(modifier = Modifier.size(36.dp))

        Image(
            Icons.Outlined.Image,
            "App Logo",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(150.dp)

        )
        Spacer(modifier = Modifier.size(36.dp))

        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.size(36.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Password") }

        )
        Spacer(modifier = Modifier.size(65.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.width(150.dp)


        ) {
            Text(
                "Accedi",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.size(36.dp))

        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = "Non sei ancora registrato?",
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "Registrati",
                textDecoration = TextDecoration.Underline,
                color = Color.Blue,
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp
            )
        }


    }
}