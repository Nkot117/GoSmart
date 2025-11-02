package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.ItemsDao
import com.nkot117.core.data.mapper.toDomain
import com.nkot117.core.data.mapper.toEntity
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepositoryImpl @Inject constructor(
    private val dao: ItemsDao
) : ItemsRepository {

    override fun getAllItems(): Flow<List<Item>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun saveItem(item: Item): Long =  dao.insert(item.toEntity())

    override suspend fun deleteItem(id: String) {
        dao.deleteById(id)
    }
}