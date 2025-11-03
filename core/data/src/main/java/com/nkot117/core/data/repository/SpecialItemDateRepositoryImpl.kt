package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.SpecialItemDateDao
import com.nkot117.core.data.db.entity.SpecialItemDatesEntity
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class SpecialItemDateRepositoryImpl @Inject constructor(
    private val dao: SpecialItemDateDao,
    @IODispatcher private val io: CoroutineDispatcher,
) : SpecialItemDateRepository {
    override fun getItemIdsOnDate(date: LocalDate): Flow<List<Long>> {
        return dao.getItemIdsOnDate(date.toString()).flowOn(io)
    }

    override fun getDatesForItem(itemId: Long): Flow<List<String>> {
        return dao.getDatesForItem(itemId).flowOn(io)
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

    override suspend fun replaceDate(itemId: Long, date: LocalDate) {
        withContext(io) {
            dao.clear(itemId)
            dao.insert(
                SpecialItemDatesEntity(
                    itemId = itemId,
                    date = date.toString()
                )
            )
        }
    }
}