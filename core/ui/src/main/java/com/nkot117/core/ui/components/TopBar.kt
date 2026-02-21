package com.nkot117.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nkot117.core.ui.theme.SmartGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    onAction: (() -> Unit)? = null,
    actionIcon: ImageVector? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                }
            }
        },
        actions = {
            if (onAction != null && actionIcon != null) {
                IconButton(onClick = onAction) {
                    Icon(actionIcon, contentDescription = "アクション")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview_NoBack() {
    SmartGoTheme {
        AppTopBar(title = "ホーム")
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview_WithBack() {
    SmartGoTheme {
        AppTopBar(title = "チェックリスト", onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview_WithAction() {
    SmartGoTheme {
        AppTopBar(
            title = "チェックリスト",
            onBack = {},
            onAction = {},
            actionIcon = Icons.Default.Settings
        )
    }
}
