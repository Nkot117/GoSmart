package com.nkot117.feature.item

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import java.time.LocalDate

data class ItemsUiState(
    val date: LocalDate?,
    val category: ItemCategory?,
    val itemList: List<Item> = emptyList(),
)