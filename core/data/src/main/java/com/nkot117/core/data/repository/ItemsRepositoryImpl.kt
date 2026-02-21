package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.ItemsDao
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.data.mapper.toDomain
import com.nkot117.core.data.mapper.toEntity
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.repository.ItemsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ItemsRepositoryImpl
@Inject
constructor(
    private val dao: ItemsDao,
    @param:IODispatcher private val io: CoroutineDispatcher
) : ItemsRepository {
    override fun getAllItems(): Flow<List<Item>> = dao.getAll().map { list ->
        list.map { it.toDomain() }
    }.flowOn(io)

    override suspend fun saveItem(item: Item): Long = withContext(io) {
        dao.insert(item.toEntity())
    }

    override suspend fun deleteItem(id: Long) {
        withContext(io) {
            dao.deleteById(id)
        }
    }

    override fun getRegisteredItemsByCategory(
        query: RegisteredItemsQuery.ByCategory
    ): Flow<List<Item>> {
        val result =
            dao
                .getByCategory(category = query.category.name)
                .map { list -> list.map { it.toDomain() } }
                .flowOn(io)
        return result
    }
}
