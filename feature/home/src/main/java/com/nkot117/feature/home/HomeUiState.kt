package com.nkot117.feature.home

import com.nkot117.core.domain.model.Item
import java.time.LocalDate

data class HomeUiState(
    val isWorkday: Boolean = true,
    val isRain: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val checklist: List<Item> = emptyList()
)
