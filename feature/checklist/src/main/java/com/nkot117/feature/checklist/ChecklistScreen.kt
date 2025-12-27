package com.nkot117.feature.checklist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.nkot117.core.ui.components.AppTopBar

@Composable
fun ChecklistScreenRoute(
    contentPadding: PaddingValues,
    setTopBar: (@Composable () -> Unit) -> Unit,
    setFab: (@Composable () -> Unit) -> Unit,
    viewModel: ChecklistViewModel = hiltViewModel(),
) {
    DisposableEffect(Unit) {
        setTopBar { AppTopBar(title = "チェックリスト") }
        setFab { }

        onDispose {
            setTopBar { }
        }
    }
}