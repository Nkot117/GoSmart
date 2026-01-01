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
import com.nkot117.feature.done.DoneScreenRoute
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
    val scaffoldSpec = scaffoldSpecForNavKey(navigator.current, { navigator.pop() })

    Scaffold(
        topBar = scaffoldSpec.topBar,
        floatingActionButton = scaffoldSpec.fab
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { navigator.pop() },
            entryProvider = entryProvider {
                // HOME画面
                entry(AppNavKey.Home) {
                    HomeScreenRoute(
                        contentPadding = innerPadding,
                        onTapCheckList = { checklistScreenTransitionParams ->
                            navigator.push(
                                AppNavKey.Checklist(
                                    params = checklistScreenTransitionParams
                                )
                            )
                        }

                    )
                }

                // チェックリスト画面
                entry<AppNavKey.Checklist> { key ->
                    val params = key.params
                    ChecklistScreenRoute(
                        contentPadding = innerPadding,
                        params = params,
                        onTapDone = { doneScreenTransitionParams ->
                            navigator.push(
                                AppNavKey.Done(params = doneScreenTransitionParams)
                            )
                        }
                    )
                }

                // 完了画面
                entry<AppNavKey.Done> { key ->
                    val params = key.params
                    DoneScreenRoute(
                        contentPadding = innerPadding,
                        params = params
                    )
                }
            })
    }
}

private fun scaffoldSpecForNavKey(appNavKey: AppNavKey, onBack: () -> Unit): ScaffoldSpec {
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

        is AppNavKey.Checklist -> ScaffoldSpec(
            topBar = { AppTopBar(title = "チェックリスト", onBack = onBack) }
        )

        is AppNavKey.Done -> ScaffoldSpec(
            topBar = { AppTopBar(title = "出発準備完了！", onBack = onBack) }
        )
    }
}
