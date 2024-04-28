package com.example.elaboratomobile.ui.screens.modificaUsername

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun ModificaUsernameScreen(navHostController: NavHostController){
Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(12.dp)
        .fillMaxSize()
) {
    Spacer(modifier = Modifier.size(20.dp))
    OutlinedTextField(
        value = "Username Corrente",
        onValueChange = {/*TODO*/ },
        label = { Text(text = "Username") }
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