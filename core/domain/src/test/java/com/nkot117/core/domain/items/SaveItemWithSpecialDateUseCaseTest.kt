package com.nkot117.core.domain.items

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.usecase.items.SaveItemSpecialDateUseCase
import com.nkot117.core.domain.usecase.items.SaveItemUseCase
import com.nkot117.core.domain.usecase.items.SaveItemWithSpecialDateUseCase
import com.nkot117.core.test.fake.FakeItemsRepository
import com.nkot117.core.test.fake.FakeSpecialItemDateRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import java.time.LocalDate

class SaveItemWithSpecialDateUseCaseTest : FunSpec({
    lateinit var itemsRepository: FakeItemsRepository
    lateinit var specialItemDateRepository: FakeSpecialItemDateRepository
    lateinit var useCase: SaveItemWithSpecialDateUseCase

    beforeTest {
        itemsRepository = FakeItemsRepository()
        specialItemDateRepository = FakeSpecialItemDateRepository()
        useCase = SaveItemWithSpecialDateUseCase(
            saveItem = SaveItemUseCase(itemsRepository),
            addSpecialItemDate = SaveItemSpecialDateUseCase(specialItemDateRepository),
        )
    }

    test("特定日付のアイテムを保存できること") {
        runTest {
            // Arrange
            itemsRepository.nextId = 5L

            // Act
            useCase(
                Item(
                    name = "借りていた傘",
                    category = ItemCategory.DATE_SPECIFIC,
                ),
                specialDate = java.time.LocalDate.of(2026, 2, 1)
            )

            // Assert
            // アイテムが保存されていること
            itemsRepository.savedItems shouldHaveSize 1
            itemsRepository.savedItems.first() shouldBe Item(
                id = 5L,
                name = "借りていた傘",
                category = ItemCategory.DATE_SPECIFIC,
            )

            // 特定日付が保存されていること
            specialItemDateRepository.savedDates shouldHaveSize 1
            specialItemDateRepository.savedDates.first() shouldBe Pair(
                5L,
                java.time.LocalDate.of(2026, 2, 1)
            )

        }
    }

    test("複数の特定日付のアイテムを保存できること") {
        runTest {
            // Arrange
            itemsRepository.nextId = 1L

            // Act
            useCase(
                Item(
                    name = "借りていた傘",
                    category = ItemCategory.DATE_SPECIFIC,
                ),
                specialDate = LocalDate.of(2026, 2, 1)
            )

            useCase(
                Item(
                    name = "返す予定の本",
                    category = ItemCategory.DATE_SPECIFIC,
                ),
                specialDate = LocalDate.of(2026, 2, 2)
            )

            // Assert
            // アイテムが保存されていること
            itemsRepository.savedItems shouldHaveSize 2
            itemsRepository.savedItems[0] shouldBe Item(
                id = 1L,
                name = "借りていた傘",
                category = ItemCategory.DATE_SPECIFIC,
            )
            itemsRepository.savedItems[1] shouldBe Item(
                id = 2L,
                name = "返す予定の本",
                category = ItemCategory.DATE_SPECIFIC,
            )

            // 特定日付が保存されていること
            specialItemDateRepository.savedDates shouldHaveSize 2
            specialItemDateRepository.savedDates[0] shouldBe Pair(
                1L,
                LocalDate.of(2026, 2, 1)
            )
            specialItemDateRepository.savedDates[1] shouldBe Pair(
                2L,
                LocalDate.of(2026, 2, 2)
            )
        }
    }
})