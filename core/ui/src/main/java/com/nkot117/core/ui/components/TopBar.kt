package com.nkot117.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nkot117.core.ui.theme.SmartGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                }
            }
        },
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