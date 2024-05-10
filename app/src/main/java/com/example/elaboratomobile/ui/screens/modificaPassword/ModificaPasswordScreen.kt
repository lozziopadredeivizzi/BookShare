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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute

@Composable
fun ModificaPasswordScreen(
    state: EditPasswordState,
    action: EditPasswordAction,
    onSubmit: () -> Unit,
    navHostController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        if (state.message != null) {
            Text(text = state.message!!, color = Color.Red, style = TextStyle(fontSize = 15.sp))
            Spacer(modifier = Modifier.size(2.dp))
        }

        OutlinedTextField(
            value = state.oldPassword,
            onValueChange = action::setOldPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text(text = "Password Attuale") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = state.newPassword,
            onValueChange = action::setNewPassword,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text(text = "Nuova Password") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                if (!state.canSubmit) return@Button
                onSubmit()
                if (state.success == true) {
                    navHostController.navigate(BookShareRoute.ModificaProfilo.route)
                }

            },
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