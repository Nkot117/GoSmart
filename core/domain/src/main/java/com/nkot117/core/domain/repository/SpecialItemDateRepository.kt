package com.nkot117.core.domain.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface SpecialItemDateRepository {
    fun getItemIdsOnDate(date: LocalDate): Flow<List<Long>>
    fun getDatesForItem(itemId: Long): Flow<List<String>>
    suspend fun saveDate(itemId: Long, date: LocalDate)
    suspend fun replaceDate(itemId: Long, date: LocalDate)
}