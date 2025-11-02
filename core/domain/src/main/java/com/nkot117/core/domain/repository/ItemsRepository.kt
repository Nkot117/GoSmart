package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.Item
import kotlinx.coroutines.flow.Flow


interface ItemsRepository {
    fun getAllItems(): Flow<List<Item>>
    suspend fun saveItem(item: Item): Long
    suspend fun deleteItem(id: Long)
}