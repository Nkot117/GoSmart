package com.nkot117.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nkot117.core.ui.theme.SmartGoTheme

@Composable
fun ChecklistRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = { Checkbox(checked = checked, onCheckedChange = onCheckedChange) },
        modifier = modifier
    )
    Divider()
}

@Preview(showBackground = true)
@Composable
private fun ChecklistRowPreview() {
    SmartGoTheme {
        ChecklistRow(
            title = "家の鍵",
            checked = false,
            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChecklistRowPreview_Checked() {
    SmartGoTheme {
        ChecklistRow(
            title = "財布",
            checked = true,
            onCheckedChange = {}
        )
    }
}