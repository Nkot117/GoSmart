package com.nkot117.core.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtil {
    fun nowLocalDate(): LocalDate =
        LocalDate.now(ZoneId.systemDefault())

    fun nowLocalDateTime(): LocalDateTime =
        LocalDateTime.now(ZoneId.systemDefault())
}