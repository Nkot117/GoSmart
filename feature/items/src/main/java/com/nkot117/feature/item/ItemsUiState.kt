package com.nkot117.feature.item

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import java.time.LocalDate

data class ItemFormState(
    val name: String = "",
    val canSubmit: Boolean = false,
)

data class ItemsUiState(
    val date: LocalDate = LocalDate.now(),
    val category: ItemCategory = ItemCategory.ALWAYS,
    val itemList: List<Item> = emptyList(),
    val form: ItemFormState = ItemFormState(),
)