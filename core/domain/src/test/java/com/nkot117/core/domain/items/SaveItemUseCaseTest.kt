package com.nkot117.core.domain.items

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.usecase.items.SaveItemUseCase
import com.nkot117.core.test.fake.FakeItemsRepository
import com.nkot117.core.test.fake.FakeSpecialItemDateRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import java.time.LocalDate

class SaveItemUseCaseTest : FunSpec({
    lateinit var itemsRepository: FakeItemsRepository
    lateinit var specialItemDateRepository: FakeSpecialItemDateRepository
    lateinit var useCase: SaveItemUseCase

    beforeTest {
        itemsRepository = FakeItemsRepository()
        specialItemDateRepository = FakeSpecialItemDateRepository()
        useCase = SaveItemUseCase(
            itemsRepository = itemsRepository,
            specialItemDateRepository = specialItemDateRepository,
        )
    }

    test("通常カテゴリのアイテムを登録できること") {
        runTest {
            // Arrange
            itemsRepository.nextId = 1L

            // Act
            val resultId = useCase(
                Item(
                    name = "財布",
                    category = ItemCategory.ALWAYS,
                )
            )

            // Assert
            resultId shouldBe 1L
            itemsRepository.savedItems shouldHaveSize 1
            itemsRepository.savedItems.first() shouldBe Item(
                id = 1L,
                name = "財布",
                category = ItemCategory.ALWAYS,
            )

            // 特定日付は保存されないこと
            specialItemDateRepository.savedDates shouldHaveSize 0
        }
    }

    test("DATE_SPECIFICカテゴリのアイテムを日付付きで登録できること") {
        runTest {
            // Arrange
            itemsRepository.nextId = 2L

            // Act
            val resultId = useCase(
                Item(
                    name = "プレゼント",
                    category = ItemCategory.DATE_SPECIFIC,
                ),
                date = LocalDate.of(2026, 2, 14)
            )

            // Assert
            resultId shouldBe 2L
            itemsRepository.savedItems shouldHaveSize 1
            itemsRepository.savedItems.first() shouldBe Item(
                id = 2L,
                name = "プレゼント",
                category = ItemCategory.DATE_SPECIFIC,
            )

            // 特定日付も保存されること
            specialItemDateRepository.savedDates shouldHaveSize 1
            specialItemDateRepository.savedDates.first() shouldBe Pair(
                2L,
                LocalDate.of(2026, 2, 14)
            )
        }
    }

    test("DATE_SPECIFICカテゴリで日付がnullの場合は例外が発生すること") {
        runTest {
            // Act & Assert
            val exception = shouldThrow<IllegalArgumentException> {
                useCase(
                    Item(
                        name = "日付なしアイテム",
                        category = ItemCategory.DATE_SPECIFIC,
                    ),
                    date = null
                )
            }

            exception.message shouldBe "DATE_SPECIFICカテゴリの場合は日付が必要です"
        }
    }
})


