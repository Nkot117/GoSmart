package com.nkot117.feature.checklist

data class ChecklistUiState(
    val checklist: List<ChecklistItem> = emptyList(),
)

data class ChecklistItem(
    val id: Long,
    val title: String,
    val checked: Boolean = false,
)