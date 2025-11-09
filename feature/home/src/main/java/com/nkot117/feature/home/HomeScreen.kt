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
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.core.ui.components.ChecklistRow
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.components.TwoOptionSegment
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.core.ui.theme.TextSub

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

    LaunchedEffect(state.isWorkday, state.isRain, state.date) {
        viewModel.getChecklist()
    }

    HomeScreen(contentPadding)
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BgWorkdayTop,
                        BgWorkdayBottom
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
                    "2025/01/15(WEB)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSub
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // Work / Holiday 選択
                TwoOptionSegment(
                    rightLabel = "Holiday",
                    leftLabel = "Work",
                    selectedLeft = true,
                    onSelect = {},
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // 天気選択カード
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

                Spacer(modifier = Modifier.padding(top = 30.dp))

                // 持ち物プレビュー
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

                        ChecklistRow(
                            title = "財布",
                            checked = false,
                            onCheckedChange = {},
                        )

                        Spacer(modifier = Modifier.padding(top = 15.dp))

                        ChecklistRow(
                            title = "スマホ",
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier
                                .width(320.dp)
                                .height(58.dp)
                        )
                        Spacer(modifier = Modifier.padding(top = 15.dp))

                        ChecklistRow(
                            title = "家の鍵",
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier
                                .width(320.dp)
                                .height(58.dp)
                        )

                        Spacer(modifier = Modifier.padding(top = 15.dp))

                        ChecklistRow(
                            title = "財布",
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier
                                .width(320.dp)
                                .height(58.dp)
                        )
                        Spacer(modifier = Modifier.padding(top = 15.dp))
                    }
                }

                // チェックリスト遷移ボタン
                Spacer(modifier = Modifier.padding(top = 30.dp))

            }
        }

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
                contentPadding = inner
            )
        }
    }
}
