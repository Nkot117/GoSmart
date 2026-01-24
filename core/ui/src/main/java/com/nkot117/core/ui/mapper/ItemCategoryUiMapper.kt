package com.nkot117.core.ui.mapper

import com.nkot117.core.domain.model.ItemCategory

fun ItemCategory.label(): String = when (this) {
    ItemCategory.ALWAYS -> "いつも"
    ItemCategory.WORKDAY -> "仕事の日"
    ItemCategory.HOLIDAY -> "休みの日"
    ItemCategory.RAINY -> "雨の日"
    ItemCategory.SUNNY -> "晴れの日"
    ItemCategory.DATE_SPECIFIC -> "この日だけ"
}