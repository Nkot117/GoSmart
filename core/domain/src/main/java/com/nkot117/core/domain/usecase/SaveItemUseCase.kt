package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.repository.ItemsRepository
import javax.inject.Inject

class SaveItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
) {
    /**
     * アイテムを保存するユースケース
     *
     * 新規登録、または既存アイテムの更新に使用する。
     * 保存処理は Room によって行われ、`id` が未設定（null）の場合は
     * 自動的に採番される。
     *
     * 本ユースケースはアイテム本体のみを保存し、特定日との紐付けは行わない。
     * 特定日も同時に保存したい場合は、
     * SaveItemWithSpecialDateUseCase などの複合ユースケースを使用する。
     *
     * @param item 保存対象のアイテム（id が null の場合は新規作成として扱われる）
     * @return 保存後に確定したアイテムの ID（自動採番された値）
     */
    suspend operator fun invoke(
        item: Item,
    ): Long = itemsRepository.saveItem(item)
}