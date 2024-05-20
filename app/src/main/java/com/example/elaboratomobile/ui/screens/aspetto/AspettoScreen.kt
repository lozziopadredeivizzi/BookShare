package com.example.elaboratomobile.ui.screens.aspetto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elaboratomobile.data.models.Theme

@Composable
fun AspettoScreen(
    state: ThemeState,
    onThemeSelected: (theme: Theme) -> Unit,
    navHostController: NavHostController
) {
    Column(Modifier.selectableGroup()) {
        Spacer(modifier = Modifier.size(10.dp))
        Theme.entries.forEach { theme ->
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(33.dp)
                        .selectable(
                            selected = (theme == state.theme),
                            onClick = { onThemeSelected(theme) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = theme.toString(),
                        style = TextStyle(
                            fontSize = 19.sp
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(100.dp)
                    )
                    Spacer(modifier = Modifier.width(170.dp))
                    RadioButton(
                        selected = (theme == state.theme),
                        onClick = null,
                    )
                }
                if (theme == Theme.Chiaro) {
                    Text(
                        "Imposta l’aspetto dell’applicazione in modalità chiara",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .width(250.dp)
                    )
                } else if (theme == Theme.Scuro) {
                    Text(
                        "Imposta l’aspetto dell’applicazione in modalità scura",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .width(250.dp)
                    )
                } else if (theme == Theme.Sistema) {
                    Text(
                        "Imposta l’aspetto dell’applicazione in base alle impostazioni di sistema",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .width(250.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}