package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDailyNoteUseCase @Inject constructor(
    private val dailyNoteRepository: DailyNoteRepository,
) {

    suspend operator fun invoke(date: LocalDate): DailyNote? = dailyNoteRepository.getByDate(date)
}