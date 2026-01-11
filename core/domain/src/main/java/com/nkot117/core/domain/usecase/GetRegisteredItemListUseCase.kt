package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.repository.ItemsRepository
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetRegisteredItemListUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val specialItemDateRepository: SpecialItemDateRepository,
) {
    /**
     * 登録されているアイテム一覧を取得するユースケース
     *
     * 指定された[RegisteredItemsQuery]に基づいて、
     * データベースに登録されているItemを取得する。
     *
     * クエリの種別によって取得条件が切り替わる：
     *
     * - [RegisteredItemsQuery.ByCategory]
     *   - 指定されたカテゴリに属するアイテムを取得する
     *
     * - [RegisteredItemsQuery.BySpecificDate]
     *   - 指定された日付に紐づけられているアイテムを取得する
     *   - 特定日アイテムは、通常カテゴリとは別テーブルで管理される
     *
     * @param query アイテム取得条件を表すクエリ
     * @return 条件に一致するItemの一覧
     */
    suspend operator fun invoke(
        query: RegisteredItemsQuery,
    ): List<Item> {
        return when (query) {
            is RegisteredItemsQuery.ByCategory ->
                itemsRepository.getRegisteredItemsByCategory(query)
                    .first()

            is RegisteredItemsQuery.BySpecificDate -> specialItemDateRepository.getRegisteredItemsByDate(
                query
            ).first()
        }
    }
}