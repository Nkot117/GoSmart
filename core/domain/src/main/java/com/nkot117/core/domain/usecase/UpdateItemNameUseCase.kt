package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.repository.ItemsRepository

class UpdateItemNameUseCase(
    private val itemsRepository: ItemsRepository,
) {
    /**
     * アイテムの名前を更新するユースケース
     *
     * アイテムの ID やカテゴリは変更せず、
     * 名前のみを上書き保存する場合に使用する。
     *
     * `Item` の更新には、ディープなロジックを入れずに、
     * Repository に委譲することで単一責任を保つ。
     *
     * @param item 変更対象のアイテム
     * @param newName 更新後の名前
     */
    suspend operator fun invoke(
        item: Item,
        newName: String
    ) {
        val updatedItem = item.copy(name = newName)
        itemsRepository.saveItem(updatedItem)
    }
}