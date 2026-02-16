package com.nkot117.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nkot117.core.domain.model.Reminder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.reminderDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "reminder_settings"
)

class ReminderSettingsDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private object Keys {
        val HOUR = intPreferencesKey("hour")
        val MINUTE = intPreferencesKey("minute")
        val ENABLED = booleanPreferencesKey("enabled")
    }

    fun observeTime(): Flow<Reminder> =
        context.reminderDataStore.data.map { pref ->
            Reminder(
                hour = pref[Keys.HOUR] ?: DEFAULT_HOUR,
                minute = pref[Keys.MINUTE] ?: DEFAULT_MINUTE,
                enabled = pref[Keys.ENABLED] ?: DEFAULT_ENABLED,
            )
        }

    suspend fun getTime(): Reminder =
        observeTime().first()

    suspend fun saveTime(time: Reminder) {
        context.reminderDataStore.edit { pref ->
            pref[Keys.HOUR] = time.hour
            pref[Keys.MINUTE] = time.minute
            pref[Keys.ENABLED] = time.enabled
        }
    }

    private companion object {
        const val DEFAULT_HOUR = 8
        const val DEFAULT_MINUTE = 0
        const val DEFAULT_ENABLED = false
    }
}