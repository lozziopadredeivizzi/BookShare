package com.example.elaboratomobile.ui.screens.modificaProfilo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun ModificaProfiloScreen(
    email: String,
    navHostController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navHostController.navigate(BookShareRoute.ModificaPassword.route) }
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Column {
                Row {
                    Text(
                        "Password",
                        style = TextStyle(
                            fontSize = 19.sp,
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .width(300.dp)
                    )
                    Icon(
                        Icons.Outlined.Edit,
                        "goToAspetto",
                        modifier = Modifier
                            .size(21.dp)
                    )
                }
                Text(
                    "Modifica la tua password attuale",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier
                        .width(250.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navHostController.navigate(BookShareRoute.ModificaEmail.route) }
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Column {
                Row {
                    Text(
                        "E-mail",
                        style = TextStyle(
                            fontSize = 19.sp,
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .width(300.dp)
                    )
                    Icon(
                        Icons.Outlined.Edit,
                        "goToAspetto",
                        modifier = Modifier
                            .size(21.dp)
                    )
                }
                Text(
                    email,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier
                        .width(250.dp)
                )
            }
        }
    }
}