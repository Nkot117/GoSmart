package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ItemDateRepository {
    fun getItemIdsOnDate(date: LocalDate): Flow<List<Long>>
    suspend fun saveDate(itemId: Long, date: LocalDate)
    fun getRegisteredItemsByDate(query: RegisteredItemsQuery.BySpecificDate): Flow<List<Item>>
}