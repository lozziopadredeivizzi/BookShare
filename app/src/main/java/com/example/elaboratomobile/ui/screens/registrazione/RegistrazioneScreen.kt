package com.example.elaboratomobile.ui.screens.registrazione

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.ui.BookShareRoute
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.PhotoCamera
import com.example.elaboratomobile.utils.rememberCameraLauncher
import com.example.elaboratomobile.utils.rememberGalleryLauncher
import com.example.elaboratomobile.utils.rememberPermission
import com.example.elaboratomobile.utils.saveImageToStorage

@Composable
fun RegistrazioneScreen(
    state: AddUserState,
    actions: AddUserActions,
    onSubmit: () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current

    //CAMERA
    val cameraLauncher = rememberCameraLauncher { imageUri ->
        saveImageToStorage(imageUri, context.applicationContext.contentResolver)
    }

    val cameraPermission = rememberPermission(Manifest.permission.CAMERA) { status ->
        if (status.isGranted) {
            cameraLauncher.captureImage()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun takePicture() {
        if (cameraPermission.status.isGranted) {
            cameraLauncher.captureImage()
        } else {
            cameraPermission.launchPermissionRequest()
        }
    }

    //GALLERIA
    val galleryLauncher = rememberGalleryLauncher { imageUri ->
        saveImageToStorage(imageUri, context.applicationContext.contentResolver)
    }

    val galleryPermission = rememberPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { status ->
        if (status.isGranted) {
            galleryLauncher.pickImage()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun pickImage() {
        if (galleryPermission.status.isGranted) {
            galleryLauncher.pickImage()
        } else {
            galleryPermission.launchPermissionRequest()
        }
    }

    val showDialog = remember { mutableStateOf(false) }


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
                fontSize = 34.sp
            )
        )
        Spacer(modifier = Modifier.size(6.dp))

        if (state.errorMessage != null) {
            Text(text = state.errorMessage, color = Color.Red, style = TextStyle(fontSize = 15.sp))
            Spacer(modifier = Modifier.size(2.dp))
        }

        Box {
            Image(
                Icons.Outlined.AccountBox,
                "pfp",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
            )
            IconButton(
                onClick = { showDialog.value = true },
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
        Spacer(modifier = Modifier.size(6.dp))
        OutlinedTextField(
            value = state.nome,
            onValueChange = actions::setName,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("Nome") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = state.cognome,
            onValueChange = actions::setSurname,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text(text = "Cognome") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = state.data_nascita,
            onValueChange = actions::setDate,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            label = { Text("Data Di Nascita") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = actions::setEmail,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("E-mail") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = state.username,
            onValueChange = actions::setUsername,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.size(2.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = actions::setPassword,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.size(28.dp))
        Button(
            onClick = {
                if (!state.canSubmit) return@Button
                onSubmit()
            },
            modifier = Modifier
                .width(150.dp),
            border = BorderStroke(1.dp, Color.Blue)
        ) {
            Text(
                text = "Registrati",
                color = Color.Black
            )
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Scegli la fonte dell'immagine") },
            text = { Text("Da dove vuoi scegliere l'immagine?") },
            confirmButton = {
                Button(
                    onClick = {
                        takePicture()
                        showDialog.value = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = "fotocamera",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Fotocamera", color = Color.Black)
                }
            },
            dismissButton = {
                Button(onClick = {
                    pickImage()
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Photo,
                        contentDescription = "gallery",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Galleria", color = Color.Black)
                }
            }
        )
    }
}