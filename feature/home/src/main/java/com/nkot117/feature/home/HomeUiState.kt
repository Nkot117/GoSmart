package com.nkot117.feature.home

import com.nkot117.core.domain.model.Item
import java.time.LocalDate

data class HomeUiState(
    val dateLabel: String = LocalDate.now().toString(),
    val isWorkday: Boolean = true,
    val isRain: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val preview: List<Item> = emptyList()
)
