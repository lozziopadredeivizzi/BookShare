package com.example.elaboratomobile.ui.screens.books

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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.elaboratomobile.data.database.Genere
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.ui.composables.RatingBarNoClick
import com.example.elaboratomobile.utils.rememberPermission
import com.example.elaboratomobile.utils.uriToBitmap

@Composable
fun HomeBooksScreen(
    navController: NavHostController,
    list: List<BookLike>,
    listGeneri: List<Genere>,
    currentIdGenere: Int,
    like: (Int) -> Unit,
    comboAction: (Int) -> Unit,
    editImage: (Int, Bitmap) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {

        ComboBox(comboAction, listGeneri, currentIdGenere)

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
            modifier = Modifier.padding()
        ) {
            items(list) { bookLike ->
                BookItem(
                    book = bookLike,
                    onClick = {
                        navController.navigate(BookShareRoute.BookDetails.buildRoute(bookLike.id_libro))
                    }, onLikeClicked = like,
                    editImage
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    book: BookLike,
    onClick: () -> Unit,
    onLikeClicked: (Int) -> Unit,
    editImage: (Int, Bitmap) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    fun Bitmap.resize(width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(this, width, height, true)
    }
    fun Bitmap.resizeToHeight(fixedHeight: Int): Bitmap {
        val aspectRatio: Float = this.width.toFloat() / this.height.toFloat()
        val width: Int = (fixedHeight * aspectRatio).toInt()
        return Bitmap.createScaledBitmap(this, width, fixedHeight, true)
    }
    //GALLERIA
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if(selectedImageUri != null){
                val bitmapImage = uriToBitmap(selectedImageUri, context.applicationContext.contentResolver)
                val resizedBitmap = bitmapImage.resizeToHeight(130)
                editImage(10, resizedBitmap)
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
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Scegli la fonte dell'immagine") },
            text = { Text("Da dove vuoi scegliere l'immagine?") },
            confirmButton = {
                Button(
                    onClick = {
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
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .width(70.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) { // Box che riempie l'intera area disponibile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box {
                    if (book.copertina != null) {
                        book.copertina.let { nonNullBitmap ->
                            val imageBitmap: ImageBitmap = nonNullBitmap.asImageBitmap()
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(130.dp)
                                    .padding(end = 30.dp)
                                    .fillMaxWidth()
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
                        .background(Color.White, shape = CircleShape)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ModeEdit,
                        contentDescription = "modifica pfp",
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = book.titolo)
                Text(text = book.autore)
                Text(text = book.genereNome)
                RatingBarNoClick(rating = book.recensione)
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        // Posizionamento dell'IconButton
        IconButton(
            onClick = { onLikeClicked(book.id_libro) },
            modifier = Modifier
                .size(25.dp)

        ) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Preferito",
                tint = if (book.isLiked) Color.Red else Color.Gray
            )
        }
    }

}


@Composable
fun ComboBox(comboAction: (Int) -> Unit, listGeneri: List<Genere>, currentIdGenere: Int) {
    var expanded by remember { mutableStateOf(false) }
    var selectedGenreId by remember { mutableStateOf<Int?>(currentIdGenere) }

    val options = listOf(Genere(0, "Tutti i generi")) + listGeneri

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(8.dp)
                .clickable { expanded = !expanded }
                .border(color = Color.Black, width = 1.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Text(

                text = options.first { it.id_genere == selectedGenreId }.nome,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown",
                modifier = Modifier.align(alignment = Alignment.CenterEnd)
            )
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false)
        ) {
            options.forEach { genere ->
                DropdownMenuItem(
                    text = { Text(genere.nome) },
                    onClick = {
                        selectedGenreId = genere.id_genere
                        comboAction(genere.id_genere)
                        expanded = false
                    }
                )
            }
        }
    }
}



