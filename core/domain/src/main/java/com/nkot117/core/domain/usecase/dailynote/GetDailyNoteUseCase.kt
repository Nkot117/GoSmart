package com.nkot117.core.domain.usecase.dailynote

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDailyNoteUseCase
@Inject
constructor(
    private val dailyNoteRepository: DailyNoteRepository
) {
    /**
     * 指定された日付に対応するデイリーノートを取得するユースケース
     */
    operator fun invoke(date: LocalDate): Flow<DailyNote?> = dailyNoteRepository.getByDate(date)
}
