package com.nkot117.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * epoch millis(UTC基準)を端末のタイムゾーンでLocalDateに変換する
 */
fun Long.toLocalDate(
    zoneId: ZoneId = ZoneId.systemDefault(),
): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDate()

/**
 * LocalDateをepoch millisに変換する（その日の00:00で変換する）
 */
fun LocalDate.toEpochMillis(
    zoneId: ZoneId = ZoneId.systemDefault(),
): Long =
    atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()

/**
 * LocalDateをyyyy/MM/dd形式にフォーマットする
 * ※DB保存用途には使用しないこと
 */
fun LocalDate.toDisplayYmdSlash(): String =
    "%04d/%02d/%02d".format(year, monthValue, dayOfMonth)

/**
 * DLocalDateをyyyy-MM-dd形式にフォーマットする
 */
fun LocalDate.toIsoDateString(): String = toString()

/**
 * 文字列型のyyyy-MM-ddをLocalDateに変換する
 */
fun String.toLocalDateIso(): LocalDate = LocalDate.parse(this)