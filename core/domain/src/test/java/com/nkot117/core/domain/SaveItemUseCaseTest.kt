package com.nkot117.core.domain

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.usecase.items.SaveItemUseCase
import com.nkot117.core.test.fake.FakeItemsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class SaveItemUseCaseTest : FunSpec({
    test("新規アイテムを保存すると、採番されたIDを返す") {
        runTest {
            // Arrange
            val itemsRepository = FakeItemsRepository().apply {
                nextId = 1L
            }
            val useCase = SaveItemUseCase(itemsRepository)

            val newItem = Item(
                name = "財布",
                category = ItemCategory.ALWAYS
            )

            // Act
            val id = useCase(newItem)

            // Assert
            id shouldBe 1L
            itemsRepository.savedItems shouldHaveSize 1
            itemsRepository.savedItems.first().id shouldBe 1L
        }
    }
})