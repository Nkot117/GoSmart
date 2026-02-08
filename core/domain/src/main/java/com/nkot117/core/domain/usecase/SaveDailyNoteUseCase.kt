package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import java.time.LocalDate
import javax.inject.Inject

class SaveDailyNoteUseCase @Inject constructor(
    private val dailyNoteRepository: DailyNoteRepository,
) {
    suspend operator fun invoke(
        date: LocalDate, rawText: String,
    ) {
        val text = rawText.trim()

        if (text.isBlank()) {
            dailyNoteRepository.deleteByDate(date)
            return
        }

        val note = DailyNote(
            date = date,
            text = text,
        )

        dailyNoteRepository.update(note)
    }
}