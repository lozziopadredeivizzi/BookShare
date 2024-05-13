package com.example.elaboratomobile.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingGraph(data: List<Pair<Int, Int>>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        data.forEach { (label, value) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                repeat(5) { index ->
                    if (index < label) {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = "Stella",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Blue
                        )
                    } else {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = "Stella",
                            modifier = Modifier.size(15.dp).alpha(0.0f) // Imposta l'opacità a 0 per nascondere le icone non utilizzate
                        )
                    }
                }
                Canvas(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(20.dp)
                        .fillMaxWidth(0.9f) // Percentuale dello spazio rimanente
                ) {
                    drawIntoCanvas {
                        val barHeight = size.height * 0.5f
                        val barLength = (value.toFloat() / getMaxValue(data).toFloat()) * size.width

                        // Disegna il rettangolo grigio di base
                        it.drawRect(
                            left = 0f,
                            top = 0f,
                            right = size.width,
                            bottom = barHeight,
                            paint = Paint().apply { color = Color.LightGray }
                        )
                        // Disegna la parte blu proporzionale alla quantità di voti
                        it.drawRoundRect(
                            left = 0f,
                            top = 0f,
                            right = barLength,
                            bottom = barHeight,
                            radiusX = 8f,
                            radiusY = 8f,
                            paint = Paint().apply { color = Color.Blue }
                        )
                    }
                }
                Text(
                    text = value.toString(),
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Funzione ausiliaria per ottenere il valore massimo dall'elenco di dati
private fun getMaxValue(data: List<Pair<Int, Int>>): Int {
    return data.maxByOrNull { it.second }?.second ?: 1
}
