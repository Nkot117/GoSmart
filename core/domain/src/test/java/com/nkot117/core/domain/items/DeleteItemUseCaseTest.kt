package com.nkot117.core.domain.items

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.usecase.items.DeleteItemUseCase
import com.nkot117.core.test.fake.FakeItemsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class DeleteItemUseCaseTest :
    FunSpec({
        test("追加されているアイテムを削除する") {
            runTest {
                // Arrange
                val itemsRepository =
                    FakeItemsRepository().apply {
                        nextId = 1L
                    }

                itemsRepository.seedItems(
                    Item(id = 1, name = "財布", category = ItemCategory.ALWAYS),
                    Item(id = 2, name = "鍵", category = ItemCategory.ALWAYS),
                    Item(id = 3, name = "傘", category = ItemCategory.RAINY)
                )

                val useCase = DeleteItemUseCase(itemsRepository)

                // Act
                useCase(2L)

                // Assert
                itemsRepository.deletedItemIds shouldBe listOf(2L)
                itemsRepository.getAllItems().first().map { it.id } shouldBe listOf(1L, 3L)
            }
        }
    })
