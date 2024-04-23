package com.example.elaboratomobile.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun LoginScreen(navController: NavHostController) {
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
            onClick = { navController.navigate(BookShareRoute.HomeBooks.route) },
            modifier = Modifier.width(150.dp),
            border = BorderStroke(1.dp, Color.Blue)
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
            ClickableText(
                text = AnnotatedString("Registrati"),
                onClick = { navController.navigate(BookShareRoute.Registrazione.route) },
                style = TextStyle(
                    Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp
                )
            )
        }


    }
}