package com.nkot117.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Two(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.padding(top = 5.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(Color.White),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 65.dp, vertical = 15.dp)
                        .background(color = Color.White),
                ) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        SegmentedButton(
                            label = { Text("晴れ") },
                            selected = false,
                            onClick = {},
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 0,
                                count = 1
                            )
                        )

                        Spacer(modifier = Modifier.padding(start = 50.dp))

                        SegmentedButton(
                            label = { Text("雨") },
                            selected = false,
                            onClick = {},
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 1,
                                count = 1
                            )
                        )
                    }
                }
            }
        }
    }
}