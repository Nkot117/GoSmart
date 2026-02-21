package com.nkot117.core.test.fake

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeDailyNoteRepository : DailyNoteRepository {
    /**
     * FakeDailyNoteRepository 内部の状態管理
     */
    private val state = MutableStateFlow<List<DailyNote>>(emptyList())

    /**
     * 保存されたアイテムの履歴
     */
    val saveDailyNote = mutableListOf<DailyNote>()

    /**
     * テスト用の初期データ投入ヘルパー
     */
    fun seedDailyNotes(vararg dailyNote: DailyNote) {
        state.value = dailyNote.toList()
    }

    override fun getByDate(date: LocalDate): Flow<DailyNote?> = state.map { notes ->
        notes.find { it.date == date }
    }

    override suspend fun deleteByDate(date: LocalDate) {
        val current = state.value.toMutableList()
        current.removeAll { it.date == date }
        state.value = current
    }

    override suspend fun update(note: DailyNote) {
        saveDailyNote += note

        val current = state.value.toMutableList()
        current.removeAll { it.date == note.date }
        current.add(note)
        state.value = current
    }
}
