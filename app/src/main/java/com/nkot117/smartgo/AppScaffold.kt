package com.nkot117.smartgo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.feature.checklist.ChecklistScreenRoute
import com.nkot117.feature.home.HomeScreenRoute
import com.nkot117.navigation.AppNavKey
import com.nkot117.navigation.Navigator

data class ScaffoldSpec(
    val topBar: @Composable () -> Unit = {},
    val fab: @Composable () -> Unit = {},
)

@Composable
fun AppScaffold() {
    val navigator = remember { Navigator() }
    val backStack = navigator.backStack
    val scaffoldSpec = scaffoldSpecForNavKey(navigator.current)

    Scaffold(
        topBar = scaffoldSpec.topBar,
        floatingActionButton = scaffoldSpec.fab
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { navigator.pop() },
            entryProvider = entryProvider {
                entry(AppNavKey.Home) {
                    HomeScreenRoute(
                        contentPadding = innerPadding,
                        onTapCheckList = { navigator.push(AppNavKey.Checklist) }
                    )
                }
                entry<AppNavKey.Checklist> {
                    ChecklistScreenRoute(
                        contentPadding = innerPadding,
                    )
                }
            })
    }
}

private fun scaffoldSpecForNavKey(appNavKey: AppNavKey): ScaffoldSpec {
    return when (appNavKey) {
        AppNavKey.Home -> ScaffoldSpec(
            topBar = { AppTopBar(title = "ホーム") },
            fab = {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        Icons.Default.Add,
                        null
                    )
                }
            }
        )

        AppNavKey.Checklist -> ScaffoldSpec(
            topBar = { AppTopBar(title = "チェックリスト") },
        )
    }
}
