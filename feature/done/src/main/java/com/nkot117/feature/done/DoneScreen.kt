package com.nkot117.feature.done

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nkot117.core.domain.model.DayType
import com.nkot117.core.ui.components.PrimaryButton
import com.nkot117.core.ui.theme.BgHolidayBottom
import com.nkot117.core.ui.theme.BgHolidayTop
import com.nkot117.core.ui.theme.BgWorkdayBottom
import com.nkot117.core.ui.theme.BgWorkdayTop
import com.nkot117.core.ui.theme.SmartGoTheme
import com.nkot117.navigation.DoneScreenTransitionParams
import com.nkot117.navigation.toDomain

@Composable
fun DoneScreenRoute(
    contentPadding: PaddingValues,
    params: DoneScreenTransitionParams,
    onTapHome: () -> Unit,
    viewModel: DoneViewModel = hiltViewModel(),
) {
    DoneScreen(
        contentPadding = contentPadding,
        dayType = params.dayType.toDomain(),
        onTapHome = onTapHome
    )
}

@Composable
fun DoneScreen(
    contentPadding: PaddingValues,
    dayType: DayType,
    onTapHome: () -> Unit,
) {
    val topColor = if (dayType == DayType.WORKDAY) BgWorkdayTop else BgHolidayTop
    val bottomColor = if (dayType == DayType.WORKDAY) BgWorkdayBottom else BgHolidayBottom

    Box(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(
                brush = Brush.verticalGradient(colors = listOf(topColor, bottomColor))
            )
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(com.nkot117.core.ui.R.raw.celebration)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier = Modifier.size(140.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "忘れ物なし！",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "今日も安心していってらっしゃい",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(80.dp)) // 下のボタンと視覚的に競合しない余白
        }

        PrimaryButton(
            text = "ホーム画面へ戻る",
            onClick = onTapHome,
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
fun DoneScreenPreview_Workday() {
    SmartGoTheme {
        DoneScreen(
            contentPadding = PaddingValues(0.dp),
            dayType = DayType.WORKDAY,
            onTapHome = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DoneScreenPreview_Holiday() {
    SmartGoTheme {
        DoneScreen(
            contentPadding = PaddingValues(0.dp),
            dayType = DayType.HOLIDAY,
            onTapHome = {}
        )
    }
}