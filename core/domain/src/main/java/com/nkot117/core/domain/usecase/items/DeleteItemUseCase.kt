package com.nkot117.core.domain.usecase.items

import com.nkot117.core.domain.repository.ItemsRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
) {
    /**
     * アイテムを削除するユースケース
     *
     * アイテムに紐づく特定日のデータ (SPECIAL_ITEM_DATES) も、
     * 外部キー制約 onDelete = CASCADE により自動的に削除される。
     *
     * そのため、本ユースケースでは関連データを明示的に削除する必要はない。
     *
     * @param itemId 削除したいアイテムの ID
     */
    suspend operator fun invoke(
        itemId: Long,
    ) {
        itemsRepository.deleteItem(itemId)
    }
}