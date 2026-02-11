package com.nkot117.core.domain

import com.nkot117.core.domain.model.DailyNote
import com.nkot117.core.domain.usecase.dailynote.GetDailyNoteUseCase
import com.nkot117.core.test.fake.FakeDailyNoteRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.time.LocalDate

class GetDailyNoteUseCaseTest : FunSpec({
    test("登録済みの日付を指定すると、その日のデイリーノートを1件返す") {
        runTest {
            // Arrange
            val dailyNoteRepository = FakeDailyNoteRepository()
            val useCase = GetDailyNoteUseCase(dailyNoteRepository)
            dailyNoteRepository.setInitialData(
                DailyNote(
                    date = LocalDate.of(2026, 1, 1),
                    text = "今日は晴れでした。"
                ),
                DailyNote(
                    date = LocalDate.of(2026, 2, 1),
                    text = "今日は雨でした。"
                ),
            )

            // Act
            val dailyNote = useCase(LocalDate.of(2026, 1, 1)).first()

            // Assert
            dailyNote?.date shouldBe LocalDate.of(2026, 1, 1)
            dailyNote?.text shouldBe "今日は晴れでした。"
        }
    }

    test("未登録の日付を指定すると、nullを返す") {
        runTest {
            // Arrange
            val dailyNoteRepository = FakeDailyNoteRepository()
            val useCase = GetDailyNoteUseCase(dailyNoteRepository)
            dailyNoteRepository.setInitialData(
                DailyNote(
                    date = LocalDate.of(2026, 1, 1),
                    text = "今日は晴れでした。"
                ),
            )

            // Act
            val dailyNote = useCase(LocalDate.of(2026, 3, 1)).first()

            // Assert
            dailyNote shouldBe null
        }
    }
})