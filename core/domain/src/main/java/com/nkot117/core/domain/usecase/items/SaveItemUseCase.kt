package com.nkot117.core.domain.usecase.items

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.repository.ItemsRepository
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import java.time.LocalDate
import javax.inject.Inject

class SaveItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val specialItemDateRepository: SpecialItemDateRepository,
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
                val itemId = itemsRepository.saveItem(item)
                specialItemDateRepository.saveDate(itemId, date)
                itemId
            }

            else -> {
                itemsRepository.saveItem(item)
            }
        }
    }
}
