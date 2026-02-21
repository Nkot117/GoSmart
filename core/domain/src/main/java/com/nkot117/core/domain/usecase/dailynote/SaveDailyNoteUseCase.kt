package com.nkot117.core.domain.usecase.dailynote

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.repository.DailyNoteRepository
import java.time.LocalDate
import javax.inject.Inject

class SaveDailyNoteUseCase
@Inject
constructor(
    private val dailyNoteRepository: DailyNoteRepository
) {
    /**
     * デイリーノートを保存するユースケース
     *
     * 指定された日付に対応するデイリーノートを保存する。
     * 引数：rawTextが空白または空文字列の場合、該当する日記を削除する。
     *
     * @param date 日付
     * @param rawText デイリーノートの内容
     */
    suspend operator fun invoke(date: LocalDate, rawText: String) {
        val text = rawText.trim()

        if (text.isBlank()) {
            dailyNoteRepository.deleteByDate(date)
            return
        }

        val note =
            DailyNote(
                date = date,
                text = text
            )

        dailyNoteRepository.update(note)
    }
}
