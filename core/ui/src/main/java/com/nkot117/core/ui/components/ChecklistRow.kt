package com.nkot117.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.ui.theme.BorderLine
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.core.ui.theme.TextSub


@Composable
fun ChecklistPreviewRow(
    title: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(16.dp)
    Surface(
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, BorderLine),
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
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.clip(shape)
        )
    }
}

@Composable
fun ChecklistActionRow(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconColor: Color = TextSub,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(16.dp)

    Surface(
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, BorderLine),
        modifier = modifier
            .clip(shape)
    ) {
        ListItem(
            trailingContent = icon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.clickable(onClick = onClick)
                    )
                }
            },
            headlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSub
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ChecklistRow(
    title: String,
    checked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(16.dp)
    Surface(
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, color = BorderLine),
        modifier = modifier.clickable { onToggle() }
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
                Checkbox(checked = checked, onCheckedChange = null)
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
private fun ChecklistPreviewRowPreview() {
    SmartGoTheme {
        ChecklistPreviewRow(
            title = "家の鍵",
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
            onToggle = {}
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
            onToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChecklistActionRowPreview_NoIcon() {
    SmartGoTheme {
        ChecklistActionRow(
            title = "財布",
            icon = Icons.Filled.Delete,
            iconColor = Color.Red,
            onClick = {}
        )
    }
}



