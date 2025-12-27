package com.nkot117.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.ui.theme.BorderLine
import com.nkot117.core.ui.theme.Primary100
import com.nkot117.core.ui.theme.Primary500
import com.nkot117.core.ui.theme.TextSub

data class SegmentOption<T>(
    val value: T,
    val label: String,
)

@Composable
fun <T> TwoOptionSegment(
    left: SegmentOption<T>,
    right: SegmentOption<T>,
    selected: T,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)

    Row(
        modifier = modifier
            .height(44.dp)
            .clip(shape)
            .border(1.dp, BorderLine, shape)
    ) {
        val leftSelected = selected == left.value
        val rightSelected = selected == right.value

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(if (leftSelected) Primary500 else Color.White)
                .clickable(!leftSelected) { onSelectedChange(left.value) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = left.label,
                color = if (leftSelected) Primary100 else TextSub,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(if (rightSelected) Primary500 else Color.White)
                .clickable(!rightSelected) { onSelectedChange(right.value) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = right.label,
                color = if (rightSelected) Primary100 else TextSub,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private enum class PreviewType {
    Work,
    Holiday,
}

@Preview(showBackground = true, name = "Left Selected")
@Composable
private fun TwoOptionSegmentLeftSelectedPreview() {
    TwoOptionSegment(
        left = SegmentOption(PreviewType.Work, "Work"),
        right = SegmentOption(PreviewType.Holiday, "Holiday"),
        selected = PreviewType.Work,
        onSelectedChange = {},
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true, name = "Right Selected")
@Composable
private fun TwoOptionSegmentRightSelectedPreview() {
    TwoOptionSegment(
        left = SegmentOption(PreviewType.Work, "Work"),
        right = SegmentOption(PreviewType.Holiday, "Holiday"),
        selected = PreviewType.Holiday,
        onSelectedChange = {},
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}