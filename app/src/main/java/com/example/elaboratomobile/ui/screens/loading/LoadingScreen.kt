package com.example.elaboratomobile.ui.screens.loading


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaboratomobile.R

@Composable
fun LoadingScreen(
    loading: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "BookShare",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            "App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxSize(0.6f)

        )
    }
    loading()
}