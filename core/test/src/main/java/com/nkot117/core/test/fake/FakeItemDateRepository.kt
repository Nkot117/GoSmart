package com.nkot117.core.test.fake

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.repository.ItemDateRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeItemDateRepository : ItemDateRepository {
    /**
     * 日付ごとにアイテムIDのリストを管理する内部状態
     */
    private val dateToItemIds = MutableStateFlow(emptyMap<LocalDate, List<Long>>())

    /**
     * アイテムのリストを管理する内部状態
     */
    private val itemsState = mutableListOf<Item>()

    /**
     * 保存された日付とアイテムIDの履歴
     */
    val savedDates = mutableListOf<Pair<Long, LocalDate>>()

    /**
     * テスト用の初期データ投入ヘルパー
     */
    fun seedDateItemIds(vararg dateItemPairs: Pair<LocalDate, List<Long>>) {
        dateToItemIds.value = dateItemPairs.toMap()
    }

    fun seedItems(vararg items: Item) {
        itemsState.clear()
        itemsState.addAll(items)
    }

    override fun getItemIdsOnDate(date: LocalDate): Flow<List<Long>> = dateToItemIds.map {
        it[date] ?: emptyList()
    }

    override suspend fun saveDate(itemId: Long, date: LocalDate) {
        // 保存履歴に追加
        val newDateToItemId = itemId to date
        savedDates.add(newDateToItemId)

        // 内部状態に追加
        dateToItemIds.update { current ->
            val updated = current.toMutableMap()
            val list = updated[date]?.toMutableList() ?: mutableListOf()
            list.add(itemId)
            updated[date] = list
            updated
        }
    }

    override fun getRegisteredItemsByDate(
        query: RegisteredItemsQuery.BySpecificDate
    ): Flow<List<Item>> = dateToItemIds.map {
        val itemIds = it[query.date] ?: emptyList()
        itemsState.filter { item -> item.id in itemIds }
    }
}
