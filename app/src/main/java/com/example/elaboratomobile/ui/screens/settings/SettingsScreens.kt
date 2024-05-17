package com.example.elaboratomobile.ui.screens.settings

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    logOut: () -> Unit,
    hasBiometric: Boolean,
    anyoneBiometric: Boolean,
    addBiometric: () -> Unit,
    removeBiometric: () -> Unit
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
                .clickable { navHostController.navigate(BookShareRoute.Aspetto.route) }
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row {
                Text(
                    "Tema dell'applicazione",
                    style = TextStyle(
                        fontSize = 19.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .width(300.dp)
                )
                Icon(
                    Icons.Outlined.ArrowForwardIos,
                    "goToAspetto",
                    modifier = Modifier
                        .size(21.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navHostController.navigate(BookShareRoute.ModificaProfilo.route) }
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row {
                Text(
                    "Modifica profilo",
                    style = TextStyle(
                        fontSize = 19.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .width(300.dp)
                )
                Icon(
                    Icons.Outlined.ArrowForwardIos,
                    "goToModificaProfilo",
                    modifier = Modifier
                        .size(21.dp)
                )
            }
        }
        if (hasBiometric) {
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        removeBiometric()
                        navHostController.navigate(BookShareRoute.Settings.route)
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row {
                    Text(
                        "Disabilita login Biometrico",
                        style = TextStyle(
                            fontSize = 19.sp,
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .width(300.dp)
                    )
                    Icon(
                        Icons.Outlined.Fingerprint,
                        "biometrico",
                        modifier = Modifier
                            .size(21.dp)
                    )
                }
            }
        }
        if (anyoneBiometric) {
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        addBiometric()
                        navHostController.navigate(BookShareRoute.Settings.route)
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row {
                    Text(
                        "Abilita login Biometrico",
                        style = TextStyle(
                            fontSize = 19.sp,
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .width(300.dp)
                    )
                    Icon(
                        Icons.Outlined.Fingerprint,
                        "biometrico",
                        modifier = Modifier
                            .size(21.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(430.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
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
                    logOut()
                },
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .padding(vertical = 3.dp)
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp)
        ) {
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