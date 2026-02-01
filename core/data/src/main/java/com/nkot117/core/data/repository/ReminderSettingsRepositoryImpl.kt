package com.nkot117.core.data.repository

import com.nkot117.core.data.datastore.ReminderSettingsDataSource
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReminderSettingsRepositoryImpl @Inject constructor(
    private val dataSource: ReminderSettingsDataSource,
    @param:IODispatcher private val io: CoroutineDispatcher,
) : ReminderSettingsRepository {
    override fun observeTime(): Flow<Reminder> {
        return dataSource.observeTime().flowOn(io)
    }

    override suspend fun getTime(): Reminder {
        return withContext(io) {
            dataSource.getTime()
        }
    }

    override suspend fun saveTime(time: Reminder) {
        withContext(io) {
            dataSource.saveTime(time)
        }
    }
}