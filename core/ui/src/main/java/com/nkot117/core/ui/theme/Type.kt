package com.nkot117.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nkot117.core.ui.R

private val AppFontFamily = FontFamily(
    Font(R.font.noto_sans_regular, FontWeight.Normal),
    Font(R.font.noto_sans_medium, FontWeight.Medium),
    Font(R.font.noto_sans_bold, FontWeight.Bold),
)

val AppTypography = Typography(
    bodyLarge = Typography().bodyLarge.copy(fontFamily = AppFontFamily),
    titleLarge = Typography().titleLarge.copy(fontFamily = AppFontFamily),
    labelLarge = Typography().labelLarge.copy(fontFamily = AppFontFamily)
)