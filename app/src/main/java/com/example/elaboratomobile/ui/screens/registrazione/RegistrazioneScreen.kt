package com.example.elaboratomobile.ui.screens.registrazione

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.utils.rememberCameraLauncher
import com.example.elaboratomobile.utils.rememberPermission
import com.example.elaboratomobile.utils.saveImageToStorage
import com.example.elaboratomobile.utils.uriToBitmap

@Composable
fun RegistrazioneScreen(
    state: AddUserState,
    actions: AddUserActions,
    onSubmit: () -> Unit,
    navController: NavHostController,
    anyoneState: Boolean
) {
    val context = LocalContext.current
    var isChecked by remember {
        mutableStateOf(false)
    }

    fun Bitmap.resize(width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(this, width, height, true)
    }

    //CAMERA
    val cameraLauncher = rememberCameraLauncher { imageUri ->
        saveImageToStorage(imageUri, context.applicationContext.contentResolver)
        val bitmapImage = uriToBitmap(imageUri, context.applicationContext.contentResolver)
        val resizedBitmap = bitmapImage.resize(500, 500) // Imposta le dimensioni desiderate
        actions.setPfp(resizedBitmap)
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
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    val bitmapImage =
                        uriToBitmap(selectedImageUri, context.applicationContext.contentResolver)
                    val resizedBitmap =
                        bitmapImage.resize(500, 500) // Imposta le dimensioni desiderate
                    actions.setPfp(resizedBitmap)
                }
            }
        }

    val galleryPermission =
        rememberPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { status ->
            if (status.isGranted) {
                galleryLauncher.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    fun pickImage() {
        if (galleryPermission.status.isGranted) {
            galleryLauncher.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            )
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
        Spacer(modifier = Modifier.size(15.dp))
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
            if (state.immagineProfilo != null) {
                state.immagineProfilo.let { nonNullBitmap ->
                    val imageBitmap: ImageBitmap = nonNullBitmap.asImageBitmap()
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Outlined.AccountBox,
                    contentDescription = "Icona del profilo",
                    modifier = Modifier.size(100.dp)
                )
            }
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
        Spacer(modifier = Modifier.size(4.dp))
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
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done // Impedisce il ritorno a capo
            ),
            label = { Text("Password") }
        )

        if (anyoneState) {
            Spacer(modifier = Modifier.size(2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(9.dp))
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        actions.setImpronta(if (isChecked) 1 else 0)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Attiva autenticazione biometrica")
            }
        }

        if(anyoneState) Spacer(modifier = Modifier.size(3.dp))
        else Spacer(modifier = Modifier.size(28.dp))
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