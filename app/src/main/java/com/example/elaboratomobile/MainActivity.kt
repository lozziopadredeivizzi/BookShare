package com.example.elaboratomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.elaboratomobile.data.models.Theme
import com.example.elaboratomobile.ui.BookShareNavGraph
import com.example.elaboratomobile.ui.BookShareRoute
import com.example.elaboratomobile.ui.composables.AppBar
import com.example.elaboratomobile.ui.composables.BottomBar
import com.example.elaboratomobile.ui.screens.aspetto.AspettoViewModel
import com.example.elaboratomobile.ui.screens.notification.NotificationViewModel
import com.example.elaboratomobile.ui.theme.ElaboratoMobileTheme
import com.example.elaboratomobile.utils.LocationService
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private lateinit var locationService: LocationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationService = get<LocationService>()

        setContent {
            val themeViewModel = koinViewModel<AspettoViewModel>()
            val themeState by themeViewModel.state.collectAsStateWithLifecycle()
            val notificationVm = koinViewModel<NotificationViewModel>()
            val notificCount by notificationVm.unreadNotificationsCount.collectAsStateWithLifecycle()

            ElaboratoMobileTheme(
                darkTheme = when (themeState.theme) {
                    Theme.Chiaro -> false
                    Theme.Scuro -> true
                    Theme.Sistema -> isSystemInDarkTheme()
                }
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute by remember {
                        derivedStateOf {
                            BookShareRoute.routes.find {
                                it.route == backStackEntry?.destination?.route
                            } ?: BookShareRoute.Loading
                        }
                    }
                    Scaffold(
                        topBar = {
                            AppBar(
                                navController = navController,
                                currentRoute = currentRoute,
                                getCountNotific = { notificationVm.updataNotification() },
                                countNotific = notificCount
                            )
                        },
                        bottomBar = {
                            BottomBar(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    ) { contentPadding ->
                        BookShareNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(contentPadding),
                            themeState,
                            themeViewModel::changeTheme
                        )
                    }


                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        locationService.pauseLocationRequest()
    }

    override fun onResume() {
        super.onResume()
        locationService.resumeLocationRequest()
    }
}
