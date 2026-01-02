package com.nkot117.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.ui.components.ChecklistPreviewRow
import com.nkot117.core.ui.components.SecondaryButton
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.BorderLine
import com.nkot117.core.ui.theme.Primary100
import com.nkot117.core.ui.theme.Primary500
import com.nkot117.core.ui.theme.TextSub

@Composable
fun ItemsScreenRoute(
    contentPadding: PaddingValues,
    viewModel: ItemsViewModel = hiltViewModel(),
) {
    ItemsScreen(contentPadding = contentPadding,
        registeredItems = listOf(
            Item(id = 1L, name = "財布", category = ItemCategory.WORKDAY),
            Item(id = 2L, name = "家の鍵", category = ItemCategory.ALWAYS),
            Item(id = 3L, name = "スマホ", category = ItemCategory.RAINY),
            Item(id = 4L, name = "ハンカチ", category = ItemCategory.SUNNY),
            Item(id = 5L, name = "ティッシュ", category = ItemCategory.DATE_SPECIFIC),
        )
        )
}

@Composable
fun ItemsScreen(
    contentPadding: PaddingValues,
    registeredItems: List<Item>,
) {
    val topColor = BgWorkdayTop
    val bottomColor = BgWorkdayBottom

    Box(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        start = 41.dp, end = 41.dp, top = 16.dp,
                        bottom = 88.dp
                    )
                )
        ) {
            val shape = RoundedCornerShape(18.dp)
            val scroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surface)
                    .horizontalScroll(scroll)
                    .border(1.dp, BorderLine, shape)
            ) {
                ItemCategory.entries.forEachIndexed { index, tag ->
                    val isSelected = tag == ItemCategory.WORKDAY
                    val bg =
                        if (isSelected) Primary500 else Color.White
                    val fg =
                        if (isSelected) Primary100 else TextSub

                    TextButton(
                        onClick = { },
                        modifier = Modifier
                            .background(bg)
                            .border(0.5.dp, BorderLine)
                    ) {
                        Text(tag.name, color = fg, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("追加する持ち物") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                )

                SecondaryButton(
                    text = "＋ 追加",
                    onClick = {},
                    modifier = Modifier.height(56.dp),
                )
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "登録されている持ち物リスト",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(registeredItems) { item ->
                    var expanded by remember { mutableStateOf(false) }

                    ChecklistPreviewRow(title = item.name)

                    Spacer(modifier = Modifier.padding(top = 15.dp))
                }


            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsScreenPreview() {
    ItemsScreen(
        contentPadding = PaddingValues(0.dp),
        registeredItems = previewRegisteredItems()
    )
}

private fun previewRegisteredItems(): List<Item> = listOf(
    Item(id = 1L, name = "財布", category = ItemCategory.WORKDAY),
    Item(id = 2L, name = "家の鍵", category = ItemCategory.ALWAYS),
    Item(id = 3L, name = "スマホ", category = ItemCategory.RAINY),
    Item(id = 4L, name = "ハンカチ", category = ItemCategory.SUNNY),
    Item(id = 5L, name = "ティッシュ", category = ItemCategory.DATE_SPECIFIC),
)