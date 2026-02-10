package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.SpecialItemDateDao
import com.nkot117.core.data.db.entity.SpecialItemDatesEntity
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.data.mapper.toDomain
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class SpecialItemDateRepositoryImpl @Inject constructor(
    private val dao: SpecialItemDateDao,
    @param:IODispatcher private val io: CoroutineDispatcher,
) : SpecialItemDateRepository {
    override fun getItemIdsOnDate(date: LocalDate): Flow<List<Long>> {
        return dao.getItemIdsOnDate(date.toString()).flowOn(io)
    }

    override suspend fun saveDate(itemId: Long, date: LocalDate) {
        withContext(io) {
            dao.insert(
                SpecialItemDatesEntity(
                    itemId = itemId,
                    date = date.toString()
                )
            )
        }
    }

    override fun getRegisteredItemsByDate(query: RegisteredItemsQuery.BySpecificDate): Flow<List<Item>> {
        return dao.getItemsByDate(query.date.toString()).map { list -> list.map { it.toDomain() } }
            .flowOn(io)
    }
}