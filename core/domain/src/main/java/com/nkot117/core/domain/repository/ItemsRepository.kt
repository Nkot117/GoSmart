package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import kotlinx.coroutines.flow.Flow


interface ItemsRepository {
    fun getAllItems(): Flow<List<Item>>
    suspend fun saveItem(item: Item): Long
    suspend fun deleteItem(id: Long)
    fun getRegisteredItemsByCategory(query: RegisteredItemsQuery.ByCategory): Flow<List<Item>>
}