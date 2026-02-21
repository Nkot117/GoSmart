package com.nkot117.core.domain.items

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.usecase.items.GetRegisteredItemsUseCase
import com.nkot117.core.test.fake.FakeItemDateRepository
import com.nkot117.core.test.fake.FakeItemsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.time.LocalDate

class GetRegisteredItemUseCaseTest :
    FunSpec({
        lateinit var itemsRepository: FakeItemsRepository
        lateinit var specialItemDateRepository: FakeItemDateRepository
        lateinit var useCase: GetRegisteredItemsUseCase

        beforeTest {
            itemsRepository = FakeItemsRepository()
            specialItemDateRepository = FakeItemDateRepository()
            useCase =
                GetRegisteredItemsUseCase(
                    itemsRepository = itemsRepository,
                    itemDateRepository = specialItemDateRepository
                )
        }

        test("指定したカテゴリーの登録アイテムを取得できること") {
            runTest {
                // Arrange
                itemsRepository.seedItems(
                    Item(
                        id = 1L,
                        name = "傘",
                        category = ItemCategory.RAINY
                    ),
                    Item(
                        id = 2L,
                        name = "レインコート",
                        category = ItemCategory.RAINY
                    ),
                    Item(
                        id = 3L,
                        name = "入館証",
                        category = ItemCategory.WORKDAY
                    )
                )

                // Act
                val result = useCase(RegisteredItemsQuery.ByCategory(ItemCategory.RAINY)).first()

                // Assert
                result.map { it.id } shouldBe listOf(1L, 2L)
                result.map { it.name } shouldBe listOf("傘", "レインコート")
            }
        }

        test("指定した日付の登録アイテムを取得できること") {
            runTest {
                // Arrange
                specialItemDateRepository.seedItems(
                    Item(
                        id = 1L,
                        name = "締め切り期限の書類",
                        category = ItemCategory.DATE_SPECIFIC
                    ),
                    Item(
                        id = 2L,
                        name = "借りていたCD",
                        category = ItemCategory.DATE_SPECIFIC
                    ),
                    Item(
                        id = 3L,
                        name = "友人の誕生日プレゼント",
                        category = ItemCategory.DATE_SPECIFIC
                    )
                )

                specialItemDateRepository.seedDateItemIds(
                    Pair(
                        LocalDate.of(2026, 2, 1),
                        listOf(1L, 3L)
                    ),
                    Pair(
                        LocalDate.of(2026, 2, 2),
                        listOf(2L)
                    )
                )

                // Act
                val result =
                    useCase(RegisteredItemsQuery.BySpecificDate(LocalDate.of(2026, 2, 1))).first()

                // Assert
                result.map { it.id } shouldBe listOf(1L, 3L)
                result.map { it.name } shouldBe listOf(
                    "締め切り期限の書類",
                    "友人の誕生日プレゼント"
                )
            }
        }

        test("指定したカテゴリーの登録アイテムがない場合、空のリストが返されること") {
            runTest {
                // Arrange
                // NOP

                // Act
                val result = useCase(RegisteredItemsQuery.ByCategory(ItemCategory.WORKDAY)).first()

                // Assert
                result shouldHaveSize 0
            }
        }

        test("指定した日付の登録アイテムがない場合、空のリストが返されること") {
            runTest {
                // Arrange
                // NOP

                // Act
                val result =
                    useCase(RegisteredItemsQuery.BySpecificDate(LocalDate.of(2026, 3, 1))).first()

                // Assert
                result shouldHaveSize 0
            }
        }
    })
