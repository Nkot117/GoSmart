package com.nkot117.feature.home

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nkot117.core.common.toEpochMillis
import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.model.WeatherType
import com.nkot117.core.ui.components.AppTopBar
import com.nkot117.core.ui.components.ChecklistPreviewRow
import com.nkot117.core.ui.components.DatePickerField
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.components.SegmentOption
import com.nkot117.core.ui.components.TwoOptionSegment
import com.nkot117.core.ui.theme.BgHolidayBottom
import com.nkot117.core.ui.theme.BgHolidayTop
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.navigation.ChecklistScreenTransitionParams
import com.nkot117.navigation.toNav

@Composable
fun HomeScreenRoute(
    contentPadding: PaddingValues,
    onTapCheckList: (params: ChecklistScreenTransitionParams) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.dayType, state.weatherType, state.date) {
        viewModel.getChecklist()
    }

    HomeScreen(
        contentPadding = contentPadding,
        state = state,
        setDayType = viewModel::setDayType,
        setWeatherType = viewModel::setWeatherType,
        setDate = viewModel::setDate,
        onTapCheckList = onTapCheckList
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    state: HomeUiState,
    setDayType: (DayType) -> Unit,
    setWeatherType: (WeatherType) -> Unit,
    setDate: (Long) -> Unit,
    onTapCheckList: (params: ChecklistScreenTransitionParams) -> Unit,
) {
    val topColor by animateColorAsState(
        targetValue = if (state.dayType == DayType.WORKDAY) {
            BgWorkdayTop
        } else {
            BgHolidayTop
        },
        label = "bg_top"
    )

    val bottomColor by animateColorAsState(
        targetValue = if (state.dayType == DayType.WORKDAY) {
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
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                if (state.weatherType == WeatherType.SUNNY) com.nkot117.core.ui.R.raw.sunny else com.nkot117.core.ui.R.raw.rainy
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 41.dp, end = 41.dp, top = 16.dp,
                bottom = 88.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 日付
                    DatePickerField(
                        selectedDateMillis = state.date.toEpochMillis(),
                        onDateChange = {
                            setDate(it)
                        },
                        confirmButtonLabel = "OK",
                        cancelButtonLabel = "キャンセル",
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.padding(end = 20.dp))

                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(54.dp)
                    )


                }

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // Work / Holiday 選択
                TwoOptionSegment(
                    left = SegmentOption(DayType.WORKDAY, "仕事の日"),
                    right = SegmentOption(DayType.HOLIDAY, "休みの日"),
                    selected = state.dayType,
                    onSelectedChange = { setDayType(it) },
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // Sunny / Rainy 選択
                TwoOptionSegment(
                    left = SegmentOption(WeatherType.SUNNY, "晴れの日"),
                    right = SegmentOption(WeatherType.RAINY, "雨の日"),
                    selected = state.weatherType,
                    onSelectedChange = { setWeatherType(it) },
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                // 持ち物プレビュー
                ItemPreview(state.preview)

                Spacer(modifier = Modifier.padding(top = 30.dp))
            }
        }

        // チェックリスト遷移ボタン
        PrimaryButton(
            text = "チェックリストへ",
            onClick = {
                val params = ChecklistScreenTransitionParams(
                    dayType = state.dayType.toNav(),
                    weatherType = state.weatherType.toNav(),
                    date = state.date.toString()
                )
                onTapCheckList(
                    params
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .widthIn(max = 360.dp)
        )
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
                setDayType = {},
                setWeatherType = {},
                setDate = {},
                onTapCheckList = {}
            )
        }
    }
}
