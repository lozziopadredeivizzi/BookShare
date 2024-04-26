package com.example.elaboratomobile.ui.screens.profile

import android.graphics.Paint.Style
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Box {
            Image(
                Icons.Outlined.AccountBox,
                "pfp",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(130.dp)
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

        Spacer(modifier = Modifier.size(10.dp))
        Text(
            "Nome Cognome",
            style = TextStyle(
                fontSize = 30.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.size(40.dp))
        Column (
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 15.dp)
        ) {
            Row {
                Text(
                    "Username:",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 23.sp
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "Username attuale",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 19.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier.padding(vertical = 3.dp).width(400.dp)
                )
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row {
                Text(
                    "Data di nascita:",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 23.sp
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "DD-MM-YYYY",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 19.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier.padding(vertical = 3.dp).width(400.dp)
                )
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row {
                Text(
                    "Libri presi in prestito:",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 23.sp
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    "n",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 19.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier.padding(vertical = 3.dp).width(400.dp)
                )
            }
        }
    }
}