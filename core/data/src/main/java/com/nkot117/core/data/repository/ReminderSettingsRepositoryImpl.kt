package com.nkot117.core.data.repository

import com.nkot117.core.data.datastore.ReminderSettingsDataSource
import com.nkot117.core.domain.model.ReminderTime
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderSettingsRepositoryImpl @Inject constructor(
    private val dataSource: ReminderSettingsDataSource,
) : ReminderSettingsRepository {
    override fun observeTime(): Flow<ReminderTime> {
        return dataSource.observeTime()
    }

    override suspend fun getTime(): ReminderTime {
        return dataSource.getTime()
    }

    override suspend fun saveTime(time: ReminderTime) {
        dataSource.saveTime(time)
    }
}