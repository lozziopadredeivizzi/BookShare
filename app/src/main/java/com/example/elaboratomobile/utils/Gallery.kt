package com.example.elaboratomobile.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

interface GalleryLauncher {
    fun pickImage()
}

@Composable
fun rememberGalleryLauncher(onImagePicked: (imageUri: Uri) -> Unit = {}): GalleryLauncher {
    val ctx = LocalContext.current
    var selectedImageUri by remember { mutableStateOf(Uri.EMPTY) }
    val galleryActivityLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { selectedImageUriFromGallery ->
            if (selectedImageUriFromGallery != null) {
                selectedImageUri = selectedImageUriFromGallery
                onImagePicked(selectedImageUri)
            }
        }

    val galleryLauncher by remember {
        derivedStateOf {
            object : GalleryLauncher {
                override fun pickImage() {
                    val galleryIntent = Intent(Intent.ACTION_PICK)
                    galleryIntent.type = "image/*"
                    galleryActivityLauncher.launch(galleryIntent.toString())
                }
            }
        }
    }
    return galleryLauncher
}
