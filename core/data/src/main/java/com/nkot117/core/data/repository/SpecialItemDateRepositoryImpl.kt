package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.SpecialItemDateDao
import com.nkot117.core.data.db.entity.SpecialItemDatesEntity
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpecialItemDateRepositoryImpl @Inject constructor(
    private val dao: SpecialItemDateDao,

    ) : SpecialItemDateRepository {
    override fun getItemIdsOnDate(date: LocalDate): Flow<List<Long>> =
        dao.getItemIdsOnDate(date.toString())


    override fun getDatesForItem(itemId: Long): Flow<List<String>> = dao.getDatesForItem(itemId)

    override suspend fun saveDate(itemId: Long, date: LocalDate) {
        dao.insert(
            SpecialItemDatesEntity(
                itemId = itemId,
                date = date.toString()
            )
        )
    }

    override suspend fun deleteDate(itemId: Long, date: LocalDate) {
        dao.deleteByItemAndDate(itemId = itemId, date = date.toString())
    }

}