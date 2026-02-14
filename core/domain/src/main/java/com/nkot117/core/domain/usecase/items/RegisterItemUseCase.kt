package com.nkot117.core.domain.usecase.items

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import java.time.LocalDate
import javax.inject.Inject

class RegisterItemUseCase @Inject constructor(
    private val saveItemUseCase: SaveItemUseCase,
    private val saveItemWithSpecialDateUseCase: SaveItemWithSpecialDateUseCase,
) {
    /**
     * アイテムを登録する
     *
     * @param item 登録するアイテム
     * @param date 特定日（DATE_SPECIFICカテゴリの場合に使用）
     * @return 登録されたアイテムのID
     */
    suspend operator fun invoke(
        item: Item,
        date: LocalDate? = null,
    ): Long {
        return when (item.category) {
            ItemCategory.DATE_SPECIFIC -> {
                requireNotNull(date) { "DATE_SPECIFICカテゴリの場合は日付が必要です" }
                saveItemWithSpecialDateUseCase(item, date)
            }

            else -> {
                saveItemUseCase(item)
            }
        }
    }
}
