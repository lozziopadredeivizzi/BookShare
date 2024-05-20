package com.example.elaboratomobile.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.R
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.utils.BiometricPromptManager

@Composable
fun LoginScreen(
    state: LoginState,
    actions: LoginActions,
    onSubmit: () -> Unit,
    navController: NavHostController,
    impronta: Boolean,
    onAuthentication: () -> Unit,
    openBiometric: () -> Unit,
    biometricResult: BiometricPromptManager.BiometricResult?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "BookShare",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp,
            )
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            "App Logo",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxSize(0.3f)

        )
        if (!impronta) Spacer(modifier = Modifier.size(10.dp))

        if (state.errorMessage != null) {
            Text(text = state.errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.size(10.dp))
        }
        biometricResult?.let { result ->
            when (result) {
                is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                    Text(text = "Autenticazione annullata")
                }

                BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                    Text(text = "Autenticazione Fallita")
                }

                BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                    Text(text = "Autenticazione non impostata")
                }

                BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                    onAuthentication()
                }

                BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                    Text(text = "Funzionalità non disonibile")
                }

                BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                    Text(text = "Hardaware non disponibile")
                }
            }
        }

        OutlinedTextField(
            value = state.username,
            onValueChange = actions::setUsername,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = state.password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = actions::setPassword,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("Password") }

        )
        if (impronta) Spacer(modifier = Modifier.size(30.dp))
        else Spacer(modifier = Modifier.size(60.dp))
        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                if (!state.canSubmit) return@Button
                onSubmit()
            },
            modifier = Modifier.width(150.dp),
            border = BorderStroke(1.dp, Color.Blue)
        ) {
            Text(
                "Accedi",
                color = Color.Black
            )
        }
        if (impronta) Spacer(modifier = Modifier.size(20.dp))
        else Spacer(modifier = Modifier.size(36.dp))
        if (impronta) {
            IconButton(
                onClick = {
                    openBiometric()
                },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(2.dp, Color.Blue), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Fingerprint",
                    tint = Color.Blue,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
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
