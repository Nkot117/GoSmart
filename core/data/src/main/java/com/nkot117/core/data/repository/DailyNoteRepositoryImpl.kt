package com.nkot117.core.data.repository

import com.nkot117.core.data.db.dao.DailyNoteDao
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.data.mapper.toDomain
import com.nkot117.core.data.mapper.toEntity
import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DailyNoteRepositoryImpl
@Inject
constructor(
    private val dailyNoteDao: DailyNoteDao,
    @param:IODispatcher private val io: CoroutineDispatcher
) : DailyNoteRepository {
    override fun getByDate(date: LocalDate): Flow<DailyNote?> =
        dailyNoteDao.getByDate(date.toString()).map { it?.toDomain() }.flowOn(io)

    override suspend fun deleteByDate(date: LocalDate) {
        withContext(io) {
            dailyNoteDao.deleteByDate(date.toString())
        }
    }

    override suspend fun update(note: DailyNote) {
        withContext(io) {
            dailyNoteDao.update(
                note.toEntity()
            )
        }
    }
}
