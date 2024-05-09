package com.example.elaboratomobile.ui.composables

import android.widget.RatingBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBarNoClick(
    rating: Double
) {
    var isHalfStar = (rating % 1) != 0.0

    Row() {
        for (index in 1..5) {
            Icon(
                modifier = Modifier.size(25.dp),
                contentDescription = null,
                tint = Color.Blue,
                imageVector = if (index <= rating) Icons.Rounded.Star
                else {
                    if (isHalfStar) {
                        isHalfStar = false
                        Icons.Rounded.StarHalf
                    } else {
                        Icons.Rounded.StarOutline
                    }
                }
            )
        }
    }
}

@Composable
fun RatingBar(rating: Double = 0.0, onRatingChange: (Double) -> Unit) {

    Row() {
        for (index in 1..5) {
            Icon(
                modifier = Modifier.size(45.dp).clickable{onRatingChange(index.toDouble())},
                contentDescription = null,
                tint = Color.Blue,
                imageVector = when {
                    index <= rating -> Icons.Rounded.Star
                    index - 0.5 == rating -> Icons.Rounded.StarHalf
                    else -> Icons.Rounded.StarOutline
                }
            )
        }
    }
}