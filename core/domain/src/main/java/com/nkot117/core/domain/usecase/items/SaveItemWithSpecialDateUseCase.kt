package com.nkot117.core.domain.usecase.items

import com.nkot117.core.domain.model.Item
import java.time.LocalDate
import javax.inject.Inject

class SaveItemWithSpecialDateUseCase @Inject constructor(
    private val saveItem: SaveItemUseCase,
    private val addSpecialItemDate: SaveItemSpecialDateUseCase,
) {
    /**
     * アイテムを保存し、特定日を紐付ける
     *
     * @param item 保存対象
     * @param specialDate 紐付ける特定日
     */
    suspend operator fun invoke(
        item: Item,
        specialDate: LocalDate,
    ) {
        val itemId = saveItem(item)
        addSpecialItemDate(itemId, specialDate)
    }
}