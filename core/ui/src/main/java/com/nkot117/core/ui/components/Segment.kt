package com.nkot117.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.ui.theme.BorderLine
import com.nkot117.core.ui.theme.Primary100
import com.nkot117.core.ui.theme.Primary500
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.core.ui.theme.TextSub

@Composable
fun TwoOptionSegment(
    leftLabel: String,
    rightLabel: String,
    selectedLeft: Boolean,
    onSelect: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val primary500 = Primary500
    val primary100 = Primary100
    val border = BorderLine
    val textSub = TextSub

    Row(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, border, RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(if (selectedLeft) primary500 else Color.Transparent)
                .clickable { onSelect(true) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = leftLabel,
                color = if (selectedLeft) primary100 else textSub,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(if (!selectedLeft) Primary500 else Color.Transparent)
                .clickable { onSelect(false) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rightLabel,
                color = if (!selectedLeft) primary100 else textSub,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TwoOptionSegmentPreview() {
    SmartGoTheme {
        var selected by remember { mutableStateOf(true) }

        TwoOptionSegment(
            leftLabel = "Work",
            rightLabel = "Holiday",
            selectedLeft = selected,
            onSelect = { selected = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}