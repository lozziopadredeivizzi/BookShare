package com.example.elaboratomobile.ui.screens.modificaPassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun ModificaPasswordScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = "Password Attuale",
            onValueChange = {/*TODO*/ },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text(text = "Password Attuale") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = "Nuova Password",
            onValueChange = {/*TODO*/ },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text(text = "Nuova Password") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = { navHostController.navigate(BookShareRoute.ModificaProfilo.route) },
            modifier = Modifier
                .width(150.dp),
            border = BorderStroke(1.dp, Color.Blue)
        ) {
            Text(
                text = "Modifica",
                color = Color.Black
            )
        }
    }
}