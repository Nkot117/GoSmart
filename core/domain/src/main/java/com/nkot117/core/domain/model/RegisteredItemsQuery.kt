package com.nkot117.core.domain.model

import java.time.LocalDate

sealed interface RegisteredItemsQuery {
    data class ByCategory(val category: ItemCategory) : RegisteredItemsQuery
    data class BySpecificDate(val date: LocalDate) : RegisteredItemsQuery
}