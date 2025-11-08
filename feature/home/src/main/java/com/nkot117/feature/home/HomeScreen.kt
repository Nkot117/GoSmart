package com.nkot117.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.core.ui.theme.SmartGoTheme

@Composable
fun HomeScreenRoute(
    contentPadding: PaddingValues,
    setTopBar: (@Composable () -> Unit) -> Unit,
    setFab: (@Composable () -> Unit) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setTopBar { AppTopBar(title = "ホーム") }
        setFab { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null) } }
    }

    LaunchedEffect(state.isWorkday, state.isRain, state.date) {
        viewModel.getChecklist()
    }

    HomeScreen(contentPadding)
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
) {

}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    SmartGoTheme {
        Scaffold(
            topBar = { AppTopBar("ホーム") },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        ) { inner ->
            HomeScreen(
                contentPadding = inner
            )
        }
    }
}
