package com.nkot117.core.domain.model

import java.time.LocalDate

data class DailyNote(
    val date: LocalDate,
    val text: String,
)