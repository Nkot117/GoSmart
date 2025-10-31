package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.Item
import kotlinx.coroutines.flow.Flow


interface ItemRepository {
    fun getAllItems(): Flow<List<Item>>
    suspend fun saveItem(item: Item)
    suspend fun deleteItem(id: String)
}