package com.example.elaboratomobile.ui.screens.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.data.database.Utente
import com.example.elaboratomobile.utils.rememberCameraLauncher
import com.example.elaboratomobile.utils.rememberPermission
import com.example.elaboratomobile.utils.saveImageToStorage
import com.example.elaboratomobile.utils.uriToBitmap

@Composable
fun ProfileScreen(
    user: Utente?,
    num: Int,
    editImage: (Bitmap) -> Unit,
    navHostController: NavHostController
) {
    val context = LocalContext.current

    fun Bitmap.resize(width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(this, width, height, true)
    }

    //CAMERA
    val cameraLauncher = rememberCameraLauncher { imageUri ->
        saveImageToStorage(imageUri, context.applicationContext.contentResolver)
        val bitmapImage = uriToBitmap(imageUri, context.applicationContext.contentResolver)
        val resizedBitmap = bitmapImage.resize(500, 500) // Imposta le dimensioni desiderate
        editImage(resizedBitmap)

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
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if(selectedImageUri != null){
                val bitmapImage = uriToBitmap(selectedImageUri, context.applicationContext.contentResolver)
                val resizedBitmap = bitmapImage.resize(500, 500) // Imposta le dimensioni desiderate
                editImage(resizedBitmap)
            }
        }
    }

    val galleryPermission = rememberPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { status ->
        if (status.isGranted) {
            galleryLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun pickImage() {
        if (galleryPermission.status.isGranted) {
            galleryLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
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
        Spacer(modifier = Modifier.size(20.dp))
        Box {
            if (user != null) {
                if (user.immagineProfilo != null) {
                    user.immagineProfilo?.let { nonNullBitmap ->
                        val imageBitmap: ImageBitmap = nonNullBitmap.asImageBitmap()
                        Image(
                            bitmap = imageBitmap,
                            contentDescription = null,
                            modifier = Modifier.size(130.dp).clip(CircleShape)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Outlined.AccountBox,
                        contentDescription = "Icona del profilo",
                        modifier = Modifier.size(130.dp)
                    )
                }
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

        Spacer(modifier = Modifier.size(10.dp))
        Text(
            user?.let { it.nome + " " + it.cognome }?: run { "/" },
            style = TextStyle(
                fontSize = 30.sp,
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
                        fontSize = 23.sp
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    user?.username ?: run { "/" },
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 19.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .width(400.dp)
                )
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row {
                Text(
                    "Data di nascita:",
                    style = TextStyle(
                        fontSize = 23.sp
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    user?.data_nascita ?: run { "/" },
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 19.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .width(400.dp)
                )
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row {
                Text(
                    "Libri presi in prestito:",
                    style = TextStyle(
                        fontSize = 23.sp
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    num.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 19.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .width(400.dp)
                )
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            onDismissRequest = { showDialog.value = false},
            title = { Text("Scegli la fonte dell'immagine", color = MaterialTheme.colorScheme.onSurface) },
            text = { Text("Da dove vuoi scegliere l'immagine?", color = MaterialTheme.colorScheme.onSurface) },
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
                    Text("Fotocamera", color = MaterialTheme.colorScheme.onSurface)
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
                    Text("Galleria", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        )
    }
}