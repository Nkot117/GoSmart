package com.nkot117.core.test.fake

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeItemsRepository : ItemsRepository {
    /**
     * FakeItemsRepository 内部の状態管理
     */
    private val itemsState = MutableStateFlow<List<Item>>(emptyList())

    /**
     * Item ID の自動インクリメント用カウンタ
     */
    var nextId: Long = 1L

    /**
     * 保存されたアイテムの履歴
     */
    val savedItems = mutableListOf<Item>()

    /**
     * 削除されたアイテムのIDの履歴
     */
    val deletedItemIds = mutableListOf<Long>()

    /**
     * カテゴリ別の登録アイテムクエリの履歴
     */
    val categoryQueries = mutableListOf<RegisteredItemsQuery.ByCategory>()

    /**
     * テスト用の初期データ投入ヘルパー
     */
    fun seedItems(vararg items: Item) {
        itemsState.value = items.toList()
    }

    override fun getAllItems(): Flow<List<Item>> = itemsState

    override suspend fun saveItem(item: Item): Long {
        // IDがnullの場合は自動生成
        val id = item.id ?: nextId++
        val saved = item.copy(id = id)

        // 保存履歴に追加
        savedItems += saved

        // 内部状態に追加
        val current = itemsState.value.toMutableList()
        current.add(saved)
        itemsState.value = current

        // 保存したIDを返す
        return id
    }

    override suspend fun deleteItem(id: Long) {
        // 削除履歴に追加
        deletedItemIds += id

        // 内部状態から削除
        itemsState.value = itemsState.value.filterNot { it.id == id }
    }

    override fun getRegisteredItemsByCategory(
        query: RegisteredItemsQuery.ByCategory
    ): Flow<List<Item>> {
        // クエリ履歴に追加
        categoryQueries += query

        // 指定されたカテゴリでフィルタリングして返す
        return itemsState.map { items ->
            items.filter { it.category == query.category }
        }
    }
}
