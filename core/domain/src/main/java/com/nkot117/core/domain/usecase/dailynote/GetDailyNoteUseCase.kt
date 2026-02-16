package com.nkot117.core.domain.usecase.dailynote

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetDailyNoteUseCase @Inject constructor(
    private val dailyNoteRepository: DailyNoteRepository,
) {
    /**
     * 指定された日付に対応するデイリーノートを取得するユースケース
     */
    operator fun invoke(date: LocalDate): Flow<DailyNote?> = dailyNoteRepository.getByDate(date)
}