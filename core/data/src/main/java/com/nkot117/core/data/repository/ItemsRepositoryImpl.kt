package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.ItemsDao
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.data.mapper.toDomain
import com.nkot117.core.data.mapper.toEntity
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.repository.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(
    private val dao: ItemsDao,
    @IODispatcher private val io: CoroutineDispatcher,
) : ItemsRepository {

    override fun getAllItems(): Flow<List<Item>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }.flowOn(io)
    }

    override suspend fun saveItem(item: Item): Long {
        return withContext(io) {
            dao.insert(item.toEntity())
        }
    }

    override suspend fun deleteItem(id: Long) {
        withContext(io) {
            dao.deleteById(id)
        }
    }
}