package com.nkot117.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * epoch millisを指定されたタイムゾーンでLocalDateに変換する。
 *
 */
fun Long.toLocalDate(zoneId: ZoneId = ZoneId.systemDefault()): LocalDate = Instant
    .ofEpochMilli(this)
    .atZone(zoneId)
    .toLocalDate()

/**
 * LocalDateをepoch millisに変換する（その日の 00:00 を基準に変換する）。
 *
 */
fun LocalDate.toEpochMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long = atStartOfDay(zoneId)
    .toInstant()
    .toEpochMilli()

/**
 * epoch millisを指定されたタイムゾーンでLocalDateTimeに変換する。
 *
 */
fun Long.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime = Instant
    .ofEpochMilli(this)
    .atZone(zoneId)
    .toLocalDateTime()

/**
 * LocalDateTimeをepoch millisに変換する。
 *
 */
fun LocalDateTime.toEpochMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long = atZone(zoneId)
    .toInstant()
    .toEpochMilli()

/**
 * LocalDateを`yyyy/MM/dd` 形式にフォーマットする。
 *
 * ※ 表示用途のみ。DB保存用途には使用しないこと。
 */
fun LocalDate.toDisplayYmdSlash(): String =
    String.format(Locale.US, "%04d/%02d/%02d", year, monthValue, dayOfMonth)

/**
 * LocalDateをISO-8601（`yyyy-MM-dd`）形式にフォーマットする。
 */
fun LocalDate.toIsoDateString(): String = format(DateTimeFormatter.ISO_LOCAL_DATE)

/**
 * ISO-8601（`yyyy-MM-dd`）形式の文字列をLocalDateに変換する。
 */
fun String.toLocalDateIso(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
