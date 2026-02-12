package com.nkot117.core.domain.dailynote

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.usecase.dailynote.SaveDailyNoteUseCase
import com.nkot117.core.test.fake.FakeDailyNoteRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class SaveDailyNoteUseCaseTest : FunSpec({

    context("テキストが空の場合、該当する日付のデイリーノートを削除する") {
        withData(
            nameFn = { "テキスト: '$it'" },
            // 空文字
            "",
            // 半角空白
            " ",
            // 全角空白
            "　"
        ) { it ->
            test("テキストが'$it'の場合、該当する日付のデイリーノートを削除する") {
                runTest {
                    // Arrange
                    val dailyNoteRepository = FakeDailyNoteRepository()
                    val saveDailyNoteUseCase = SaveDailyNoteUseCase(dailyNoteRepository)
                    val targetDate = java.time.LocalDate.of(2026, 1, 1)
                    dailyNoteRepository.setInitialData(
                        DailyNote(
                            text = "既存のデイリーノート",
                            date = targetDate
                        )
                    )

                    // Act
                    saveDailyNoteUseCase(
                        date = targetDate,
                        rawText = it,
                    )

                    // Assert
                    val savedNote = dailyNoteRepository.getByDate(targetDate).first()
                    savedNote shouldBe null
                }
            }
        }
    }

    context("テキストが有効な場合、該当する日付のデイリーノートを保存または更新する") {
        test("新規にデイリーノートを保存する場合") {
            runTest {
                // Arrange
                val dailyNoteRepository = FakeDailyNoteRepository()
                val saveDailyNoteUseCase = SaveDailyNoteUseCase(dailyNoteRepository)
                val targetDate = java.time.LocalDate.of(2026, 2, 1)
                val noteText = "これは新しいデイリーノートです。"

                // Act
                saveDailyNoteUseCase(
                    date = targetDate,
                    rawText = noteText,
                )

                // Assert
                val savedNote = dailyNoteRepository.getByDate(targetDate).first()
                savedNote?.date shouldBe targetDate
                savedNote?.text shouldBe noteText
            }
        }

        test("既存のデイリーノートを更新する場合") {
            runTest {
                // Arrange
                val dailyNoteRepository = FakeDailyNoteRepository()
                val saveDailyNoteUseCase = SaveDailyNoteUseCase(dailyNoteRepository)
                val targetDate = java.time.LocalDate.of(2026, 3, 1)
                dailyNoteRepository.setInitialData(
                    DailyNote(
                        text = "古いデイリーノート",
                        date = targetDate
                    )
                )
                val updatedText = "これは更新されたデイリーノートです。"

                // Act
                saveDailyNoteUseCase(
                    date = targetDate,
                    rawText = updatedText,
                )

                // Assert
                val savedNote = dailyNoteRepository.getByDate(targetDate).first()
                savedNote?.date shouldBe targetDate
                savedNote?.text shouldBe updatedText
            }
        }
    }
})