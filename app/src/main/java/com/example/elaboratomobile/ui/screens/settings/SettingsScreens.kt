package com.example.elaboratomobile.ui.screens.settings

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxSize()
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors()
        ) {
            Row {
                Text(
                    "Tema dell'applicazione",
                    style = TextStyle(
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .width(300.dp)
                )
                Icon(
                    Icons.Outlined.ArrowForwardIos,
                    "goToAspetto",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RectangleShape
        ) {
            Row {
                Text(
                    "Modifica profilo",
                    style = TextStyle(
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .width(300.dp)
                )
                Icon(
                    Icons.Outlined.ArrowForwardIos,
                    "goToModificaProfilo",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(430.dp))
        Row (
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ){
            Icon(
                Icons.Outlined.ExitToApp,
                "logout",
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ClickableText(
                text = AnnotatedString("Logout"),
                onClick = {
                    navHostController.navigate(
                        BookShareRoute.Login.route
                    )
                },
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .padding(vertical = 3.dp)
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
        Row (
            modifier = Modifier
                .padding(horizontal = 22.dp)
        ){
            Text(
                "Versione",
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .width(280.dp)
            )
            Text(
                "1.0.0",
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
        
    }
}