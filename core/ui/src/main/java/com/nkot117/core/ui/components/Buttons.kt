package com.nkot117.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nkot117.core.ui.theme.Primary100
import com.nkot117.core.ui.theme.Primary500
import com.nkot117.core.ui.theme.SmartGoTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary500,
            contentColor = Color.White,
            disabledContainerColor = Primary100
        )
    ) {
        Text(text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Primary500,
            disabledContentColor = Primary500.copy(alpha = 0.4f)
        ),
        border = BorderStroke(2.dp, Primary500)
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtons() {
    SmartGoTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(text = "プライマリー", onClick = {})

            PrimaryButton(text = "プライマリー", onClick = {}, enabled = false)

            Divider()

            SecondaryButton(text = "セカンダリー", onClick = {})

            SecondaryButton(text = "セカンダリー", onClick = {}, enabled = false)
        }
    }
}
