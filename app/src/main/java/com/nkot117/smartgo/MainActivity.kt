package com.nkot117.smartgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.feature.checklist.ChecklistScreenRoute
import com.nkot117.feature.home.HomeScreenRoute
import com.nkot117.navigation.AppNavKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    SmartGoTheme {
        var topBar by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
        var fab by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
        val backStack = remember { mutableListOf<AppNavKey>(AppNavKey.Home) }

        Scaffold(
            topBar = { topBar?.invoke() },
            floatingActionButton = { fab?.invoke() },
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry(AppNavKey.Home) {
                        HomeScreenRoute(
                            contentPadding = innerPadding,
                            setTopBar = { topBar = it },
                            setFab = { fab = it }
                        )
                    }
                    entry<AppNavKey.Checklist> {
                        ChecklistScreenRoute(
                            contentPadding = innerPadding,
                            setTopBar = { topBar = it },
                            setFab = { fab = it }
                        )
                    }
                })
        }
    }
}

