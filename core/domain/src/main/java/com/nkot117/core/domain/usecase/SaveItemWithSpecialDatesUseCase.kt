package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Item
import java.time.LocalDate
import javax.inject.Inject

class SaveItemWithSpecialDateUseCase @Inject constructor(
    private val saveItem: SaveItemUseCase,
    private val addSpecialItemDate: AddSpecialItemDateUseCase,
) {
    /**
     * アイテム本体を保存し、必要なら特定日を紐付けて保存する
     *
     * @param item 保存対象
     * @param specialDate 紐付ける特定日
     * @return 保存後のitemId
     */
    suspend operator fun invoke(
        item: Item,
        specialDate: LocalDate?,
    ) {
        val itemId = saveItem(item)

        if (specialDate != null) {
            addSpecialItemDate(itemId, specialDate)
        }
    }
}