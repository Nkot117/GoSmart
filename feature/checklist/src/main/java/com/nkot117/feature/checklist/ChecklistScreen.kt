package com.nkot117.feature.checklist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nkot117.core.domain.model.DayType
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.core.ui.components.ChecklistRow
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.components.SecondaryButton
import com.nkot117.core.ui.theme.BgHolidayBottom
import com.nkot117.core.ui.theme.BgHolidayTop
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.core.ui.theme.TextSub
import com.nkot117.navigation.ChecklistScreenTransitionParams
import com.nkot117.navigation.toDomain
import java.time.LocalDate

@Composable
fun ChecklistScreenRoute(
    contentPadding: PaddingValues,
    params: ChecklistScreenTransitionParams,
    viewModel: ChecklistViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isAllChecked = viewModel.isAllChecked

    LaunchedEffect(Unit) {
        viewModel.getChecklist(
            dayType = params.dayType.toDomain(),
            weatherType = params.weatherType.toDomain(),
            date = LocalDate.parse(params.date)
        )
    }

    ChecklistScreen(
        contentPadding = contentPadding,
        dayType = params.dayType.toDomain(),
        checklist = state.checklist,
        toggleChecklistItem = viewModel::toggleChecklistItem,
        onCheckAll = viewModel::checkAllItems,
        isAllChecked = isAllChecked
    )
}

@Composable
fun ChecklistScreen(
    contentPadding: PaddingValues,
    dayType: DayType,
    checklist: List<ChecklistItem>,
    toggleChecklistItem: (id: Long, checked: Boolean) -> Unit,
    onCheckAll: () -> Unit,
    isAllChecked: Boolean,
) {
    val topColor by animateColorAsState(
        targetValue = if (dayType == DayType.WORKDAY) {
            BgWorkdayTop
        } else {
            BgHolidayTop
        },
        label = "bg_top"
    )

    val bottomColor by animateColorAsState(
        targetValue = if (dayType == DayType.WORKDAY) {
            BgWorkdayBottom
        } else {
            BgHolidayBottom
        },
        label = "bg_bottom"
    )

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
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 41.dp, end = 41.dp, top = 16.dp,
                bottom = 88.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                val checkedCount = checklist.count { it.checked }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ChecklistProgressHeader(
                        checkedCount = checkedCount,
                        totalCount = checklist.size
                    )

                    Spacer(Modifier.height(8.dp))

                    SecondaryButton(
                        text = "すべてチェック",
                        onClick = onCheckAll,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                Spacer(Modifier.height(16.dp))
            }

            items(checklist) { item ->
                ChecklistRow(
                    title = item.title,
                    checked = item.checked,
                    onToggle = {
                        toggleChecklistItem(
                            item.id,
                            !item.checked
                        )
                    },
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))
            }
        }

        // チェックリスト遷移ボタン
        PrimaryButton(
            text = "出発する",
            onClick = {},
            enabled = isAllChecked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .height(56.dp)
                .width(260.dp)
        )
    }
}

@Composable
fun ChecklistProgressHeader(
    checkedCount: Int,
    totalCount: Int,
) {
    val progress =
        if (totalCount == 0) 0f
        else checkedCount / totalCount.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // 進捗テキスト
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "進捗",
                style = MaterialTheme.typography.titleSmall,
                color = TextSub
            )

            Text(
                text = "$checkedCount / $totalCount 完了",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSub
            )
        }

        Spacer(Modifier.height(8.dp))

        // プログレスバー
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview(showBackground = true, name = "Workday")
@Composable
private fun ChecklistScreenPreview_Workday() {
    SmartGoTheme {
        Scaffold(
            topBar = { AppTopBar("チェックリスト") }
        ) { inner ->
            ChecklistScreen(
                contentPadding = inner,
                dayType = DayType.WORKDAY,
                checklist = listOf(
                    ChecklistItem(id = 1, title = "家の鍵", checked = true),
                    ChecklistItem(id = 2, title = "財布", checked = false),
                    ChecklistItem(id = 3, title = "スマートフォン", checked = true),
                    ChecklistItem(id = 4, title = "社員証", checked = false),
                    ChecklistItem(id = 5, title = "折りたたみ傘", checked = false),
                ),
                toggleChecklistItem = { _, _ -> },
                onCheckAll = {},
                isAllChecked = false
            )
        }
    }
}

@Preview(showBackground = true, name = "Holiday")
@Composable
private fun ChecklistScreenPreview_Holiday() {
    SmartGoTheme {
        Scaffold(
            topBar = { AppTopBar("チェックリスト") }
        ) { inner ->
            ChecklistScreen(
                contentPadding = inner,
                dayType = DayType.HOLIDAY,
                checklist = listOf(
                    ChecklistItem(id = 1, title = "チケット", checked = true),
                    ChecklistItem(id = 2, title = "イヤホン", checked = false),
                    ChecklistItem(id = 3, title = "モバイルバッテリー", checked = false),
                ),
                toggleChecklistItem = { _, _ -> },
                onCheckAll = {},
                isAllChecked = false
            )
        }
    }
}