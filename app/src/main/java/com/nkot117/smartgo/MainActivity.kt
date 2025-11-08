package com.nkot117.smartgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.feature.home.HomeScreenRoute
import com.nkot117.core.ui.theme.SmartGoTheme
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

        Scaffold(
            topBar = { topBar?.invoke() },
            floatingActionButton = { fab?.invoke() },
        ) { innerPadding ->
            HomeScreenRoute(
                contentPadding = innerPadding,
                setTopBar = { topBar = it },
                setFab = { fab = it }
            )
        }
    }
}

