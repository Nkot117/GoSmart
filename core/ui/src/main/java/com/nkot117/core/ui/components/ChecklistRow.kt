package com.nkot117.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.ui.theme.BorderLine
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.core.ui.theme.TextSub

@Composable
fun ChecklistRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(16.dp)
    Surface(
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, color = BorderLine),
        modifier = modifier
    ) {
        ListItem(
            headlineContent = {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSub
                )
            },
            trailingContent = {
                Checkbox(checked = checked, onCheckedChange = onCheckedChange)
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.clip(shape)
        )
    }
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