package com.nkot117.feature.home

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.core.ui.components.ChecklistPreviewRow
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.components.SegmentOption
import com.nkot117.core.ui.components.TwoOptionSegment
import com.nkot117.core.ui.theme.BgHolidayBottom
import com.nkot117.core.ui.theme.BgHolidayTop
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.core.ui.theme.TextSub

private enum class WeatherUi(
    val label: String,
) {
    SUNNY(
        label = "晴れ",
    ),
    RAINY(
        label = "雨"
    )
}

@Composable
fun HomeScreenRoute(
    contentPadding: PaddingValues,
    setTopBar: (@Composable () -> Unit) -> Unit,
    setFab: (@Composable () -> Unit) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setTopBar { AppTopBar(title = "ホーム") }
        setFab { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null) } }
    }

    LaunchedEffect(state.dayType, state.weatherType, state.date) {
        viewModel.getChecklist()
    }

    HomeScreen(
        contentPadding = contentPadding,
        state = state,
        setDayType = viewModel::setDayType,
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    state: HomeUiState,
    setDayType: (DayType) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(
                brush = Brush.verticalGradient(
                    colors = if (state.dayType == DayType.WORKDAY) listOf(
                        BgWorkdayTop,
                        BgWorkdayBottom
                    ) else listOf(
                        BgHolidayTop,
                        BgHolidayBottom
                    )
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
            // 日付
            item {
                Text(
                    text = state.date.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSub
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // Work / Holiday 選択
                TwoOptionSegment(
                    left = SegmentOption(DayType.WORKDAY, "Work"),
                    right = SegmentOption(DayType.HOLIDAY, "Holiday"),
                    selected = state.dayType,
                    onSelectedChange = { setDayType(it) },
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // FIXME: 選択した側を有効にするように変更が必要
                // 天気選択カード
                WeatherSelectorCard()

                Spacer(modifier = Modifier.padding(top = 30.dp))

                // 持ち物プレビュー
                ItemPreview(state.preview)

                Spacer(modifier = Modifier.padding(top = 30.dp))
            }
        }

        // チェックリスト遷移ボタン
        PrimaryButton(
            text = "チェックリストへ",
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .height(56.dp)
                .width(260.dp)
        )
    }
}

@Composable
fun WeatherSelectorCard() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                "今日の天気は？",
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
                        WeatherUi.entries.forEachIndexed { index, weather ->
                            SegmentedButton(
                                label = { Text(weather.label) },
                                selected = false,
                                onClick = {},
                                shape = SegmentedButtonDefaults.itemShape(
                                    index,
                                    WeatherUi.entries.size
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemPreview(
    previewList: List<Item>,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "持ち物プレビュー",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 5.dp))

            previewList.forEach {
                ChecklistPreviewRow(
                    title = it.name
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    SmartGoTheme {
        Scaffold(
            topBar = { AppTopBar("ホーム") },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        ) { inner ->
            HomeScreen(
                contentPadding = inner,
                state = HomeUiState(
                    preview = listOf(
                        Item(
                            name = "家の鍵",
                            category = ItemCategory.ALWAYS
                        ),
                        Item(
                            name = "財布",
                            category = ItemCategory.ALWAYS
                        ),
                        Item(
                            name = "スマートフォン",
                            category = ItemCategory.ALWAYS
                        ),
                        Item(
                            name = "社員証",
                            category = ItemCategory.WORKDAY
                        ),
                        Item(
                            name = "折りたたみ傘",
                            category = ItemCategory.RAINY
                        )
                    )
                ),
                setDayType = {}
            )
        }
    }
}
